import { Component, OnInit } from '@angular/core';

interface Images{
  imageSrc: string;
  imageAlt: string;
}

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.css']
})
export class GalleryComponent implements OnInit{

  images: Images[] = [
    {
      imageSrc:
        'https://images.unsplash.com/photo-1546410531-bb4caa6b424d?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTZ8fHNjaG9vbHxlbnwwfHwwfHx8MA%3D%3D',
      imageAlt: 'nature1',
    },
    {
      imageSrc:
        'https://images.unsplash.com/photo-1562774053-701939374585?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8Y29sbGVnZXxlbnwwfHwwfHx8MA%3D%3D',
      imageAlt: 'nature2',
    },
    {
      imageSrc:
        'https://images.pexels.com/photos/3992949/pexels-photo-3992949.jpeg?auto=compress&cs=tinysrgb&w=1600',
      imageAlt: 'person1',
    },
    {
      imageSrc:
        'https://images.unsplash.com/photo-1535982330050-f1c2fb79ff78?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NHx8Y29sbGVnZXxlbnwwfHwwfHx8MA%3D%3D',
      imageAlt: 'person2',
    },
  ];
  currentIdx = 0;
  ngOnInit(): void {
      
  }

  selectedImage(index: number): void{
    this.currentIdx=index;
  }

}
