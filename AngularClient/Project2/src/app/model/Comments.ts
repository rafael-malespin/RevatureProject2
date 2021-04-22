import { Post } from "./Post";
import { User } from "./User";


export interface Comments{
    'commentId':number;
    'commentContent':string;
    'commentedAt':string;
    'commentWriter':User;
    'commentPost':Post;
    
}