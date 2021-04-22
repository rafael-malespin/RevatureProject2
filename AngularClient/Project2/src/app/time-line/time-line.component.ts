import { Component, OnDestroy, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import Swal from 'sweetalert2';
import { Comments } from '../model/Comments';
import { Like } from '../model/LIke';
import { Post } from '../model/Post';
import { User } from '../model/User';
import { CommentService } from '../shared/comment.service';
import { GetPostService } from '../shared/get-post.service';
import { GetUserService } from '../shared/get-user.service';
import { ImageUploadService } from '../shared/image-upload.service';
import { LikeService } from '../shared/like.service';
import { LoginService } from '../shared/login.service';
import { PostService } from '../shared/post.service';

@Component({
  selector: 'app-time-line',
  templateUrl: './time-line.component.html',
  styleUrls: ['./time-line.component.css']
})
export class TimeLineComponent implements OnInit,OnDestroy {

  
  currentUser:User;
  postContrnt: string = null;
  youtubeUrl: string = null;
  postimg: string = null;
  postImage:File=null;
  posts:Post[]=[];

  constructor(private postservice:PostService,
    private getPostService:GetPostService,
    private loginServ:LoginService,
    private likeServ:LikeService,
    private imageServ:ImageUploadService,
    private getUserService:GetUserService,
    private commentService:CommentService) { }
  ngOnDestroy(): void {
    window.removeEventListener('scroll', this.scroll, true);
  }

  ngOnInit(): void {

    
    this.getUserService.getCurrentUser().subscribe(
      data=>{
        this.currentUser=data;
      }
    );
    window.addEventListener('scroll', this.scroll, true);
    this.getAllPosts();
    
  }
  scroll = (event): void => {
    const topButton = document.getElementById("topButton");
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
      topButton.style.display = "block";
    } else {
      topButton.style.display = "none";
    }
  }

  goToTop(){
    document.body.scrollTop = 0; // For Safari
    document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
    this.getAllPosts();
  }

  getAllPosts():void{
    this.getPostService.getAllPosts().subscribe(
      data =>{
        let newPosts:Post[];
        newPosts=data;
        newPosts.forEach(element => {
          console.log(element.comments);
          console.log(element.usersWhoLiked);
          element.comments.forEach(element1=>{
            console.log(element1)
          })
          
        });
        newPosts.sort((a,b) => (a.postedAt > b.postedAt) ? -1 : ((b.postedAt > a.postedAt) ? 1 : 0))
        this.posts= newPosts;
      }
    )
  }


  handleFileInput(files:FileList){
    this.postImage=files.item(0);
  }


  addPost(){
    
    if (this.postContrnt.length<5){
      Swal.fire({ 
        icon: 'warning',
        title: 'you need to add more than one or two words',
        timer: 8000,
        showConfirmButton: true
      });
      return;
    }
    if(this.youtubeUrl!=null){
      if((this.youtubeUrl.startsWith("https:")) || (this.youtubeUrl.startsWith("www."))||(this.youtubeUrl.length!=11)){
        Swal.fire({
          icon: 'warning',
          title: 'please enter a valid youtube vidoe ID ',
          timer: 8000,
          showConfirmButton: true
        });
        return;
      }
    }
    Swal.fire({
      title: 'Creating the post',
      allowEscapeKey: false,
      allowOutsideClick: false,
      showConfirmButton: false,
      timer: 4000,
      onOpen: () => {
        Swal.showLoading();
      }
      
    });
    //todo add current user to the post object and image url to
    let post:Post = {
      'postId':0,
      'creator':this.loginServ.getCurrent(),
      'postTitle':"New Post",
      'postContent':this.postContrnt,
      'postImageUrl':null,
      'youtubeUrl':(this.youtubeUrl!=null)?"https://www.youtube.com/embed/"+this.youtubeUrl:null,
      'usersWhoLiked':[],
      'comments':[],
      'postedAt':<string>(<unknown>new Date().getTime())
    }
    if(this.postImage!=null){
      
      let file:FormData=new FormData;
      file.append("file",this.postImage)
      this.imageServ.postImageUpload(file).subscribe(
        data=>{
          
          post.postImageUrl="https://rev-training-p2-bucket.s3.us-east-2.amazonaws.com/"+data.message;
          
          let response =  this.postservice.insertNewPost(post).subscribe(
            data =>{
              
              Swal.fire({ 
                icon: 'success',
                title: 'Created',
                timer: 4000,
                showConfirmButton: true
              });
              this.getAllPosts();
              this.postImage=null;
              this.postContrnt=null;
              this.youtubeUrl=null;
              this.postimg=null;
            }
          )
        }
      )
    }
    else{
      let response =  this.postservice.insertNewPost(post).subscribe(
        data =>{
          
          Swal.fire({ 
            icon: 'success',
            title: 'Created',
            timer: 4000,
            showConfirmButton: true
          });
          this.getAllPosts();
          this.postImage=null;
          this.postContrnt=null;
          this.youtubeUrl=null;
          this.postimg=null;
        }
      )
    }
  }

  toggleLike(valueOfPost:Post,isLiked:boolean){
    
    if(isLiked){//if the Post is liked by the User it will call delete
      //first get the loggedInUser
      
      let loggedIn:User = this.loginServ.getCurrent();
      let valueOfLike:Like|null = null;
      let found:boolean=false;
      for(var like of valueOfPost.usersWhoLiked){//will search the post for the Like that connects the user and post
        for(var likeOfUser of loggedIn.likes){
          
          if(like.likeId===likeOfUser.likeId){
            valueOfLike=like;
            found=true;
            break;
          }
        }
        if(found){
          break;
        }
      }
      this.likeServ.deleteLike(valueOfLike).subscribe(
        data=>{
          
          this.getAllPosts();
          this.loginServ.triggerRetrieveCurrent();
          
        }
      );
    }
    else{
      let newLike:Like = {"likeId":0,"user":this.loginServ.getCurrent(),"post":valueOfPost}
      

      this.likeServ.insertNewLike(newLike).subscribe(
        data=>{
          
          this.getAllPosts();
          this.loginServ.triggerRetrieveCurrent();
        }
      );
    }
  }

  checkIfPostIsLiked(post:Post):boolean{
    let loggedInUser:User = this.loginServ.getCurrent();
    for(var like of post.usersWhoLiked){//will search the post for the Like that connects the user and post

      for(var likeOfUser of loggedInUser.likes){
        if(like.likeId===likeOfUser.likeId){
          return true;
        }
      }
    }
    return false;
  }

  addNewComment(valueOfPost:Post){
    let commentText = (<HTMLInputElement>document.getElementById(<string><unknown>valueOfPost.postId)).value;

    console.log("commentText= "+ commentText)
    if(commentText.length==0){
      Swal.fire({
        icon: 'warning',
        title: 'please Write a comment first',
        timer: 8000,
        showConfirmButton: true
      });
      return;
    }

    let newComment:Comments = {
      "commentId":0,
      "commentContent":commentText,
      "commentedAt":<string>(<unknown>new Date().getTime()),
      "commentWriter":this.loginServ.getCurrent(),
      "commentPost":valueOfPost
    }
    
      this.commentService.insertNewComment(newComment).subscribe(
        data=>{
          
          this.getAllPosts();
          this.loginServ.triggerRetrieveCurrent();
        }
      );
  }

  filterPosts(event: any){
    let matcher = new RegExp(event.target.value, "gi");

    for (var i=0;i<document.getElementsByClassName("postHolder").length;i++) {
      if (
        matcher.test(document.getElementsByClassName("posttext")[i].innerHTML)
      ) {
        (<HTMLElement>document.getElementsByClassName("postHolder")[i]).style.display="";
      } else {
        (<HTMLElement>document.getElementsByClassName("postHolder")[i]).style.display="none";
      }
    }
    
  }

  

  

}
