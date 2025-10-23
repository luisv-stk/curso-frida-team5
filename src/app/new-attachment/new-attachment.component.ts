import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-new-attachment',
  imports: [FormsModule],
  templateUrl: './new-attachment.component.html',
  styleUrl: './new-attachment.component.css'
})
export class NewAttachmentComponent {
  fileType = 'Fotografía';
  title = '';
  price = '';
  file = null;

  onFileTypeChange(event: any) {
    this.fileType = event.target.value;
  }

  onFileChange(event: any) {
    this.file = event.target.files[0];
  }

  clearFile() {
    this.file = null;
  }

  @ViewChild('fileInput') fileInput!: ElementRef<HTMLInputElement>;

openFileInput() {
  this.fileInput.nativeElement.click();
}
}
