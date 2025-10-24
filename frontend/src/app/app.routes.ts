import { Routes } from '@angular/router';
import { UserInfoComponent } from './user-info/user-info.component';
import { NewAttachmentComponent } from './new-attachment/new-attachment.component';

export const routes: Routes = [
  {
    path:'',
    component:UserInfoComponent
  },
  {
    path:'new-attachment',
    component:NewAttachmentComponent
  }
];
