import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginComponent } from '../login/login.component';
import { Like } from '../model/LIke';
import { Post } from '../model/Post';

import { User } from '../model/User';
import { GetPostService } from '../shared/get-post.service';
import { GetUserService } from '../shared/get-user.service';
import { LikeService } from '../shared/like.service';
import { LoginService } from '../shared/login.service';



@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  appCom;
  users:User[];
  selectedUser:User;
  userPosts:Post[];
  searchForm = this.formBuilder.group({
    userName: ''
    });
  
  constructor(private formBuilder: FormBuilder, private getUserService:GetUserService,
    private getPostService:GetPostService,
    private loginServ:LoginService,
    private likeServ:LikeService,
    private router:Router) { }

  ngOnInit(): void {

    this.loginServ.getLoggedInUser().subscribe(
      data =>{
        // info=data;
        
        if(data==null){
          this.router.navigate(['/login']);
        }
        else {
             
        }

      }
    )
    
    this.appCom = document.getElementById("home-navbar");
    this.appCom.setAttribute("style","");

    this.getUserService.getAllUsers().subscribe(
      data => {
        this.users = data ;
        
      }
    );


  }

  selectUser(){
    for (const user of this.users) {
      if(user.username==this.searchForm.value.userName){
        this.selectedUser=user;
      }
    }
    this.getAllPosts();

  }

  getAllPosts():void{
    this.getPostService.getPostsCreatedByUser(this.selectedUser.userId).subscribe(
      data =>{
        
        let newPosts:Post[];
        newPosts=data;
        newPosts.sort((a,b) => (a.postedAt > b.postedAt) ? -1 : ((b.postedAt > a.postedAt) ? 1 : 0))
        this.userPosts= newPosts;
      }
    );
  }

  selectUserByKey(event:any){
    for (const user of this.users) {
      if(user.username==this.searchForm.value.userName){
        this.selectedUser=user;
      }
    }
    this.getPostService.getPostsCreatedByUser(this.selectedUser.userId).subscribe(

      data =>{
        this.userPosts= data;
      }
    );
  }

  toggleLike(valueOfPost:Post,isLiked:boolean){
    console.log("/////////////IN TOGGLE LIKE: POST IS LIKED:"+isLiked);
    if(isLiked){//if the Post is liked by the User it will call delete
      //first get the loggedInUser
      console.log("/////////////DELETING LIKE");
      let loggedIn:User = this.loginServ.getCurrent();
      let valueOfLike:Like|null = null;
      let found:boolean=false;
      for(var like of valueOfPost.usersWhoLiked){//will search the post for the Like that connects the user and post
        for(var likeOfUser of loggedIn.likes){
          console.log("Like id:"+like.likeId+" likeOfUser:"+likeOfUser.likeId);
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
          console.log(data);
          this.getAllPosts();
          this.loginServ.triggerRetrieveCurrent();
          
        }
      );
    }
    else{
      let newLike:Like = {"likeId":0,"user":this.loginServ.getCurrent(),"post":valueOfPost}
      console.log("////////////NEWLIKE: POST:"+newLike.post.postId +" USER:"+newLike.user.userId +" "+newLike.user.username+" "+JSON.stringify(newLike.user.likes));

      this.likeServ.insertNewLike(newLike).subscribe(
        data=>{
          console.log(data);
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

  
}


