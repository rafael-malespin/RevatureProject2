import { Component, OnInit } from '@angular/core';
import { ImageUploadService } from '../shared/image-upload.service';

@Component({
  selector: 'app-image-upload',
  templateUrl: './image-upload.component.html',
  styleUrls: ['./image-upload.component.css']
})
export class ImageUploadComponent implements OnInit {

  imageObj: File;
  imageUrl: string;

  constructor(private imgService: ImageUploadService) { }

  ngOnInit(): void {
  }

  onImagedPicked(event: Event): void {
    const imgFile = (event.target as HTMLInputElement).files[0];
    this.imageObj = imgFile;
  }

  onImageUpload() {
    const imageForm = new FormData();
    imageForm.append('image', this.imageObj);
    this.imgService.imageUpload(imageForm).subscribe(
      res => {
        this.imageUrl = res['image'];
      }
    )
  }

  onProfileUpload(){

  }

  onPostImageUpload(imageForm:FormData):any{
    return this.imgService.postImageUpload(imageForm);
  }




}
