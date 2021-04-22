import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Like } from '../model/LIke';

@Injectable({
  providedIn: 'root'
})
export class LikeService {

  constructor(private myHttpCli:HttpClient) { }

  insertNewLike(like:Like):Observable<string>{
    let url:string="http://localhost:9001/toph/link/likes/insertNewLike";
    console.log("The like being sent: "+like);
    return this.myHttpCli.post<string>(url,like,{withCredentials:true});
  }
  deleteLike(like:Like):Observable<string>{
    let url:string = "http://localhost:9001/toph/link/likes/deleteLike";
    return this.myHttpCli.post<string>(url,like,{withCredentials:true});
  }
}
