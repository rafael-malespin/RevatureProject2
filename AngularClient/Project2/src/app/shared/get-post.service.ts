import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../model/Post';

@Injectable({
  providedIn: 'root'
})
export class GetPostService {

  constructor(private myHttpCli:HttpClient) { }

  getAllPosts():Observable<Post[]>{
    let url:string ="http://localhost:9001/toph/link/posts/getAllPosts";
    return this.myHttpCli.get<Post[]>(url,{withCredentials:true});
  }
  getPostById(postId:number):Observable<Post>{
    let url:string =`http://localhost:9001/toph/link/posts/getPostById/${postId}`;
    return this.myHttpCli.get<Post>(url,{withCredentials:true});
  }

  getPostsCreatedByUser(userId:number):Observable<Post[]>{
    let url:string =`http://localhost:9001/toph/link/posts/getPostsByUser/${userId}`;
    return this.myHttpCli.get<Post[]>(url,{withCredentials:true});
  }
  getPostsLikedByUser(userId:number):Observable<Post[]>{
    let url:string =`http://localhost:9001/toph/link/posts/getPostsLikedByUser/${userId}`;
    return this.myHttpCli.get<Post[]>(url,{withCredentials:true});
  }
}
