import { ThrowStmt } from '@angular/compiler';
import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { WebSocketAPI } from '../api/WebSocketAPI';
import { ChatMessage } from '../model/ChatMessage';
import { GetUserService } from '../shared/get-user.service';
import { LoginService } from '../shared/login.service';


@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit,OnDestroy{
  appCom;
  title = 'Chat'

  webSocketAPI: WebSocketAPI;
  message: ChatMessage = {
    'sender': "null",
    'text' : "",
    'time': ""
  };

  allMessages:ChatMessage[];
  msglocation: string;
  lastMsgSender: string;
  userInput = new FormControl('');
  onlineUsers:string[];

  constructor(private userService:GetUserService,private loginService:LoginService,private router:Router){
    this.allMessages=[];
    window.addEventListener("unload", this.sendDisconnect.bind(this));
  }

  ngOnInit(): void {
    this.loginService.getLoggedInUser().subscribe(
      data =>{
        // info=data;
        
        if(data==null){
          this.router.navigate(['/login']);
        }
        else {
           this.lastMsgSender = data.username;  
           this.router.events.subscribe(
             event =>{
               this.sendDisconnect();
             }
           )
        }


      }
    )
    
    this.appCom = document.getElementById("home-navbar");
    this.appCom.setAttribute("style","");
    this.webSocketAPI = new WebSocketAPI(this,this.loginService);
    this.connect();
    this.sendStatus();
    let container = document.getElementById("msgContainer");
    container.scrollTop = container.scrollHeight;
    
  }

  @HostListener('window:beforeunload')
  ngOnDestroy(): void {
    
    this.sendDisconnect();
    console.log("ng destroy");
    window.removeEventListener("unload", this.sendDisconnect.bind(this));
    
  }

  logoutAndDisconnect(): void {
    
    this.sendDisconnect();
    console.log("logout and disconnect");
    
    
  }


  get messagefield() {
    if(this.message.text == undefined){
      this.message.text = "";
    }
    return this.message.text
  }

  set messagefield(text: string) {
    this.message.text = text;
  }

  connect() {
    this.webSocketAPI._connect();
  }

  disconnect() {
    this.webSocketAPI._disconnect();
  }




  sendMessage() {
    this.userService.getCurrentUser().subscribe(
      userName => {
        this.message.sender = userName.username;
        this.lastMsgSender = userName.username;
        this.webSocketAPI._send(this.message);
      }
    );
    
  }

  handleMessage(message) {
    //console.log(this.allMessages);
    this.messagefield = "";
    this.allMessages.push(message);
    console.log(this.allMessages);
    console.log(message.sender);
    console.log(message.text);
    let container = document.getElementById("msgContainer");
    container.scrollTop = container.scrollHeight;
  }

  sendStatus() {
    this.userService.getCurrentUser().subscribe(
      userName => {
        if(userName!=null){
          this.webSocketAPI._sendStatus(userName.username);
          this.sendForOldMessages();
        }
      }
    );
  }
  
  sendDisconnect() {
    let userName=this.loginService.getCurrent();
    if(userName !=null){
      this.webSocketAPI._sendDisconnect(userName.username);
      this.disconnect();
    }
    // this.userService.getCurrentUser().subscribe(
    //   userName => {
    //     console.log("send disconnect");
    //     this.webSocketAPI._sendDisconnect(userName.username);
    //     this.disconnect();
    //   }
    // );
  }

  handleStatus(newList) {
    this.onlineUsers = newList;
  }

  sendForOldMessages() {
    this.webSocketAPI._sendForOldMessages("getting old messages");
  }

  handleOldMessages(messageList) {
    this.allMessages = messageList;
  }


  checkUser(inputtedMessage:ChatMessage):boolean{
    if(inputtedMessage.sender == this.lastMsgSender){
      return false;
    }
    return true;
  }
}
