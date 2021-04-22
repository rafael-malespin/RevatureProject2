import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import { ChatComponent } from '../chat/chat.component';

export class WebSocketAPI {
    webSocketEndPoint: string = 'http://localhost:9001/toph/link/ws';
    topic: string = "/topic/messages";
    stompClient: any;
    chatComponent: ChatComponent;
    constructor(chatComponent: ChatComponent) {
        this.chatComponent = chatComponent;
    }
    _connect() {
        console.log("Start websocket connection");
        let ws = new SockJS(this.webSocketEndPoint);
        this.stompClient = Stomp.over(ws);
        const _this = this;
        _this.stompClient.connect({}, function (frame) {
            _this.stompClient.subscribe("/topic/messages", function (sdkEvent) {
                _this.onMessageReceived(sdkEvent);
            });
            _this.stompClient.subscribe("/topic/status", function (sdkEvent){
                _this.onStatusReceived(sdkEvent);
            });
            _this.stompClient.subscribe("/topic/loadMessages", function (sdkEvent){
                _this.onOldMessagesReceived(sdkEvent);
            });
        }, this.errorCallBack);
    };

    _disconnect() {
        if (this.stompClient !== null) {
            this.stompClient.disconnect();
        }
        console.log("Disconnected");
    }

    // on error, schedule a reconnection attempt
    errorCallBack(error) {
        console.log("errorCallBack -> " + error)
        setTimeout(() => {
            this._connect();
        }, 5000);
    }

    _send(message) {
        this.stompClient.send("/app/chat", {}, JSON.stringify(message));
    }

    onMessageReceived(message) {
        console.log("Message Recieved from Server :: " + message);
        this.chatComponent.handleMessage(JSON.parse(message.body));
    }

    _sendStatus(message) {
        this.stompClient.send("/app/onlineUsers", {}, JSON.stringify(message));
    }

    _sendDisconnect(message) {
        this.stompClient.send("/app/disconnect", {}, JSON.stringify(message));
    }

    onStatusReceived(message) {
        console.log("Message Recieved from Server :: " + JSON.parse(message.body));
        this.chatComponent.handleStatus(JSON.parse(message.body));
    }

    _sendForOldMessages(message) {
        this.stompClient.send("/app/loadMessages", {}, JSON.stringify(message));
    }

    onOldMessagesReceived(message) {
        console.log("Message Recieved from Server :: " + message.body);
        this.chatComponent.handleOldMessages(JSON.parse(message.body));
    }
}
