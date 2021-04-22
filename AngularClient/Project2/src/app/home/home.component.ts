import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';
import { User } from '../model/User';
import { LoginService } from '../shared/login.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  appCom;
  constructor(private loginService:LoginService,private router:Router,private appComponent:AppComponent) { }

  ngOnInit(): void {

    this.appComponent.isShow=true;
    this.loginService.getLoggedInUser().subscribe(
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
    this.appComponent.isShow=true;
    //this.loginService.logoutUser().subscribe();
  }

}
