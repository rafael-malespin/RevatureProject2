import { Like } from "./LIke";
import { User } from "./User";


export interface Post{
    'postId':number;
    'creator':User;
    'postTitle':string;
    'postContent':string;
    'postImageUrl':string;
    'youtubeUrl':string;
    'usersWhoLiked':Like[];
    'postedAt':string;
    'comments':Comment[];
}