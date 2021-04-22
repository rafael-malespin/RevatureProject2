import { Component, OnInit } from '@angular/core';
import { User } from '../model/User';
import { LoginService } from '../shared/login.service';

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.css']
})
export class UserInfoComponent implements OnInit {

  loggedInUser:User={
    userId: 0,
    username: '',
    password: '',
    emailAddress: '',
    profilePic: '',
    description: '',
    posts: null,
    likes: null,
    firstName:'',
    lastName:''
  };;
  constructor(private loginServ:LoginService) { }

  ngOnInit(): void {
    let info:any=null;
    info = this.loginServ.getLoggedInUser().subscribe(
      data => {
        
        this.loggedInUser=data;
        this.loginServ.setCurrent(this.loggedInUser);
        return data;
      }
    )
  }

}
