import { Component, OnInit } from '@angular/core';
import { FormBuilder} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { AppComponent } from '../app.component';
import { User } from '../model/User';
import { GetUserService } from '../shared/get-user.service';


import { LoginService } from '../shared/login.service';
import { UserService } from '../shared/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  
  container: any ;
  registrationForm = this.formBuilder.group({
    userName: '',
    password: '',
    email:''
    });
  loginForm = this.formBuilder.group({
  userName: '',
  password: ''
  });
  appCom: HTMLElement;

  constructor(private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private loginService: LoginService,
    private getUserService:GetUserService,
    private userComponent:UserService) { 
    
  }

  ngOnInit(): void {

    this.loginService.getLoggedInUser().subscribe(
      data =>{
        // info=data;
        
        if(data==null){

        }
        else {
          this.router.navigate(['/home']);   
        }

        this.appCom = document.getElementById("home-navbar");
        this.appCom.setAttribute("style","display:none;");
        
        this.container = document.getElementById('container');
        this.loginService.setCurrent(data);
        console.log("LOGIN COMPONENT LOGIN: "+data);
        return data;
      }
    )
    
  }

  

  openLogin(){
    this.container.classList.remove("right-panel-active");
    
  }

  openRegistration(){
    this.container.classList.add("right-panel-active");
    
  }

  onLoginSubmit(){

    Swal.fire({
      title: 'LOGGING IN ...',
      allowEscapeKey: false,
      allowOutsideClick: false,
      showConfirmButton: false,
      timer: 4000,
      onOpen: () => {
        Swal.showLoading();
      }
      
    });
    console.log(this.loginForm.value.password);
    console.log(this.loginForm.value.userName);
    let user: User = {
      userId: null,
      username: this.loginForm.value.userName,
      password: this.loginForm.value.password,
      emailAddress: null,
      profilePic: null,
      description: null,
      posts: null,
      likes: null,
      firstName:"",
      lastName:""
    };
    let response =  this.loginService.loginUser(user).subscribe(
      (RCurrentUser: User)=> {
        console.log("RCurrentUser"+RCurrentUser);
        if (RCurrentUser){
          Swal.fire({ 
            icon: 'success',
            title: 'Logged IN',
            timer: 4000,
            showConfirmButton: true
          });
          window.location.reload();
          this.appCom.setAttribute("style","");
          
        }else{
          
          Swal.fire({ 
            icon: 'warning',
            title: 'Wrong User Name OR Password',
            timer: 4000,
            showConfirmButton: true
          });
        }
        return RCurrentUser;
      }
    );
  }

  onRegistrationSubmit(){

    Swal.fire({
      title: 'Creating New Account ...',
      allowEscapeKey: false,
      allowOutsideClick: false,
      showConfirmButton: false,
      timer: 4000,
      onOpen: () => {
        Swal.showLoading();
      }
      
    });
    
    console.log(this.registrationForm.value.password);
    console.log(this.registrationForm.value.userName);
    console.log(this.registrationForm.value.email);
    let user: User = {
      userId: null,
      username: this.registrationForm.value.userName,
      password: this.registrationForm.value.password,
      emailAddress: this.registrationForm.value.email,
      profilePic: null,
      description: "",
      posts: null,
      likes: null,
      firstName:"",
      lastName:""
    };
  let response =  this.userComponent.insertNewUser(user).subscribe(
    data=> {
      if(data.message=="User was created"){
        Swal.fire({ 
          icon: 'success',
          title: 'Account Created',
          timer: 4000,
          showConfirmButton: true
        });
        this.container.classList.remove("right-panel-active");
        (<HTMLInputElement>document.getElementById("loginusername")).value=this.registrationForm.value.userName;
        this.loginForm.value.userName=this.registrationForm.value.userName;
        (<HTMLInputElement>document.getElementById("loginpassword")).value=this.registrationForm.value.password;
        this.loginForm.value.password=this.registrationForm.value.password;
      }else if(data.message=="Username already taken"){
        Swal.fire({ 
          icon: 'warning',
          title: '"Username already taken try again',
          timer: 4000,
          showConfirmButton: true
        });
      }
      
    }
  );
  
  }

  onPasswordReset():void{
    Swal.fire({
      title: 'Enter Your Username',
      input: 'text',
      showClass: {
          popup: 'animate__animated animate__fadeInDown'
        },
        hideClass: {
          popup: 'animate__animated animate__fadeOutUp'
        },
      inputAttributes: {
        autocapitalize: 'off'
      },
      showCancelButton: true,
      confirmButtonText: 'Send Email',
      showLoaderOnConfirm: true,
      preConfirm: (userToReset) => {
        console.log(userToReset);
        this.loginService.resetPassword(userToReset).subscribe(
          data=>{
            console.log("Password reset result: "+data);
          }
        )
      },
      allowOutsideClick: () => !Swal.isLoading()
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire({ 
          icon: 'success',
          title: 'Done',
          timer: 4000,
          showConfirmButton: true
        });
      }   
        
    });

    // Swal.fire({
    //   title: 'Do you want to save the changes?',
    //   showDenyButton: true,
    //   showCancelButton: true,
    //   confirmButtonText: `Save`,
    //   denyButtonText: `Don't save`,
      // showClass: {
      //   popup: 'animate__animated animate__fadeInDown'
      // },
      // hideClass: {
      //   popup: 'animate__animated animate__fadeOutUp'
      // },
    // }).then((result) => {
    //   /* Read more about isConfirmed, isDenied below */
    //   if (result.isConfirmed) {
    //     Swal.fire('Saved!', '', 'success')
    //   } else if (result.isDenied) {
    //     Swal.fire('Changes are not saved', '', 'info')
    //   }
    // })

  }

}
