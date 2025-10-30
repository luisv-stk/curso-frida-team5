import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';

@Component({
  selector: 'app-items-list',
  imports: [MatIconModule, CommonModule, MatButtonModule, MatDividerModule],
  templateUrl: './items-list.component.html',
  styleUrl: './items-list.component.css',
})
export class ItemsListComponent implements OnInit {
  uploadResponse = history.state.uploadResponse;
  tagString: string = '';
  posts: any[] = [];
  router = inject(Router)
  ngOnInit(): void {
    console.log('ON INIT');
    console.log(this.uploadResponse);
    this.uploadResponse['etiquetas'].forEach((tag: string) => {
      this.tagString += tag + ', ';
      console.log(tag);
    });
    console.log(this.tagString);
    this.posts = [
      {
        name: this.uploadResponse['titulo'],
        type: 'JPG',
        //TODO-->Cambiar tipo 1,2,3 por correspondiente
        size: this.uploadResponse['tamanio'],
        description: this.tagString,
        imageUrl: `data:image/jpeg;base64,${this.uploadResponse['documento']}`,
      },
    ];
  }

  navigateBack(){
    this.router.navigate(['/new-attachment']);
  }
}
