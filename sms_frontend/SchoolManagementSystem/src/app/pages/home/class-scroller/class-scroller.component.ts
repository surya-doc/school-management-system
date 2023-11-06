import { Component, OnInit, HostListener } from '@angular/core';
import { ViewportScroller } from '@angular/common';
import { ClassDataService } from 'src/app/components/admin/manage-class/service/class-data.service';

interface Class {
  name:string;
  ct_name:string;
  total_students:number;
}

@Component({
  selector: 'app-class-scroller',
  templateUrl: './class-scroller.component.html',
  styleUrls: ['./class-scroller.component.css']
})
export class ClassScrollerComponent implements OnInit{
  productList;

  images: string[] = ["../../assets/class_1.jpeg","../../assets/class_2.jpeg","../../assets/classs_3.jpeg"];

  isNavCollapse = false;
  @HostListener('window:scroll', []) onScroll() {
    if (this.scroll.getScrollPosition()[1] > 70) {
      this.isNavCollapse = true;
    } else {
      this.isNavCollapse = false;
    }
  }
  element: HTMLElement|null = document.getElementById('scroll-1');
  constructor(private scroll: ViewportScroller, private classDataService: ClassDataService) { }

  ngOnInit(){
    this.classDataService.getAllclasses().subscribe(
      (response) => {
        // this.classes = response;
        console.log('All Classes', response);
        this.productList = response;
      },
      (error) => {
        console.log("Something went wrong", error);
      }
    );
  }

  onWheel(event: WheelEvent): void {
    if (event.deltaY > 0) this.scrollToRight();
    else this.scrollToLeft();
  }

  scrollToLeft(): void {
    if(this.element){
      this.element.scrollLeft -= 400;
    }
  }

  scrollToRight(): void {
    document.getElementById('scroll-1')!.scrollLeft += 400;
  }

}
