import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { WebSocketAPI } from './api/WebSocketAPI';
import { LoginComponent } from './login/login.component';
import { User } from './model/User';
import { GetUserService } from './shared/get-user.service';
import { LoginService } from './shared/login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  user:User;
  appCom: HTMLElement;
  public isShow: boolean = false ;
  down:boolean = false;
  constructor(private loginService:LoginService,private router:Router){
    
  }
  
  title = 'Project2';
  ngOnInit(): void {
    this.loginService.getLoggedInUser().subscribe(
      data=> this.user=data
    );
    this.down = false;
    this.appCom = document.getElementById("home-navbar");
  }


  logout(){
    // this.chatCom.logoutAndDisconnect();
    //this.webSocketAPI._sendDisconnect(this.user.username);
    this.loginService.logoutUser().subscribe(
      data=>{
        this.appCom.setAttribute("style","display: none");
        let currentUrl = this.router.url;
        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
        this.router.navigate([currentUrl]);
    });
      }
    );
    
    
    


  }

  reloadCurrentRoute() {
    let currentUrl = this.router.url;
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
        this.router.navigate([currentUrl]);
    });
}

  notify(){

    let box = document.getElementById("box");
    if(this.down){
      this.down = false;
      }else{
        this.down = true;
      }


  }

  activate(link:string){
    console.log(link);
    let home=document.getElementById("home");
    let profile=document.getElementById("profile");
    let cahtRoom=document.getElementById("chatroom");
    let search=document.getElementById("search");
    home.setAttribute("class","nav-item");
    profile.setAttribute("class","nav-item");
    cahtRoom.setAttribute("class","nav-item");
    search.setAttribute("class","nav-item");
    
    document.getElementById(link).setAttribute("class","nav-item active");

  }
}
