import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private currentUser: User;

  constructor(private httpCli:HttpClient) { }

  loginUser(user:User): Observable<User> {
    let url:string="http://localhost:9001/toph/link/users/login";
    console.log("in loginUser");
    const httpPost ={
      headers : new HttpHeaders({
        'Content-Type':'application/json',
        'withCredentials':'true'
      })
    }
    return this.httpCli.post<User>(url,user,{withCredentials:true});
  }

  logoutUser(): Observable<string>{
    let url: string="http://localhost:9001/toph/link/users/logout";
    return this.httpCli.get<string>(url, {withCredentials:true});
  }

  getLoggedInUser(): Observable<User>{
    let url:string ="http://localhost:9001/toph/link/users/getLoggedInUser";
    let content:any=this.httpCli.get<User>(url,{withCredentials:true})
    let content2=content.subscribe(
      data=>{
        this.currentUser=data;
      }
    )
    return content;
  }

  getCurrent():User{
    if(this.currentUser==null || this.currentUser==undefined){
      this.getLoggedInUser().subscribe(
        data =>{
          this.currentUser=data;
        }
      )
    }
    
    return this.currentUser;
  }
  setCurrent(user:User):void{
    console.log("Current User is being set.")
    console.log(user);
    this.currentUser=null;
    this.currentUser=user;
  }

  triggerRetrieveCurrent():void{
    this.getLoggedInUser().subscribe(
      data=>{
        console.log("Retrieve Triggered")
        this.setCurrent(data);
      }
    )
  }

  resetPassword(userName:string):Observable<string>{
    let url:string = "http://localhost:9001/toph/link/users/resetPassword";
    return this.httpCli.post<string>(url,userName,{withCredentials:true});
  }
}
