import { Component, inject } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { NewAttachmentComponent } from '../new-attachment/new-attachment.component';

@Component({
  selector: 'app-user-info',
  imports: [MatIconModule],
  standalone: true,
  templateUrl: './user-info.component.html',
  styleUrl: './user-info.component.css',
})
export class UserInfoComponent {
  private router = inject(Router);
  newAttachmentScreen() {
    this.router.navigate(['/new-attachment']);
  }
}
