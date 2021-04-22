import { Post } from "./Post";
import { User } from "./User";

export interface Like{
    'likeId':number;
    'user':User;
    'post':Post;
}