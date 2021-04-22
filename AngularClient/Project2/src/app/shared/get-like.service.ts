import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Like } from '../model/LIke';

@Injectable({
  providedIn: 'root'
})
export class GetLikeService {

  constructor(private myHttpCli:HttpClient) { }

  getAllLikesForPost(postId:number):Observable<Like[]> {
    let url:string=`http://localhost:9001/toph/link/likes/getLikesForPost/${postId}`;
    return this.myHttpCli.get<Like[]>(url,{withCredentials:true});
  }
  getLikeCountForPost(postId:number):Observable<number> {
    let url:string=`http://localhost:9001/toph/link/likes/getLikesForPostSize/${postId}`;
    return this.myHttpCli.get<number>(url,{withCredentials:true});
  }
}
