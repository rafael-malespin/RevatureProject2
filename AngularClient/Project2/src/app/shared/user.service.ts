import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ChatMessage } from '../model/ChatMessage';
import { ResponseMessage } from '../model/ResponseMessage';
import { User } from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private myHttpCli:HttpClient) { }

  insertNewUser(user:User): Observable<ResponseMessage> {
    let url:string="http://localhost:9001/toph/link/users/insertNewUser";
    return this.myHttpCli.post<ResponseMessage>(url,user,{withCredentials:true});
  }
  updateUser(user:User): Observable<HttpResponse<string>>{
    let url: string = "http://localhost:9001/toph/link/users/updateUser";
    console.log(" inside the user update  >>> "+user);
    return this.myHttpCli.put<HttpResponse<string>>(url,user,{withCredentials:true});

  }
  checkOldPass(user:User): Observable<HttpResponse<string>>{
    let url: string = "http://localhost:9001/toph/link/users/checkOldPassword";
    
    return this.myHttpCli.post<HttpResponse<string>>(url,user,{withCredentials:true});
  }

  deleteUser(user:User): Observable<HttpResponse<string>>{
    let url: string = "http://localhost:9001/toph/link/users/deleteUser";
    //TODO: change in Java from deletemapping to postmapping
    return this.myHttpCli.post<HttpResponse<string>>(url,user,{withCredentials:true});
  }
}
