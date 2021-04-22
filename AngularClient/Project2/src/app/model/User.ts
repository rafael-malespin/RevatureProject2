
import { Like } from "./LIke";
import { Post } from "./Post";


export interface User{
    'userId': number;
    'username':string;
    'password':string;
    'emailAddress':string;
    'profilePic':string;
    'description':string;
    'firstName':string;
    'lastName':string;
    'posts':Post[];
    'likes':Like[];
    
    
}