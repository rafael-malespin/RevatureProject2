import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChatComponent } from './chat/chat.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { SearchComponent } from './search/search.component';


const routes: Routes = [
{path: 'login', component:LoginComponent},
{path: 'home', component:HomeComponent},
{path: 'profile', component:ProfileComponent},
{path: 'chatroom', component: ChatComponent},
{path: 'search', component: SearchComponent},
{path:'',redirectTo:'login',pathMatch:'full'},
{path:'**',redirectTo:'login'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
