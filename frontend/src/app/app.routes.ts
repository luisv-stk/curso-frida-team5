import { Routes } from '@angular/router';
import { NewAttachmentComponent } from './components/new-attachment/new-attachment.component';
import { UserInfoComponent } from './components/user-info/user-info.component';
import { ItemsListComponent } from './components/items-list/items-list.component';


export const routes: Routes = [
  {
    path:'',
    component:UserInfoComponent
  },
  {
    path:'new-attachment',
    component:NewAttachmentComponent
  },
  {
    path:'items-list',
    component:ItemsListComponent
  }
];
