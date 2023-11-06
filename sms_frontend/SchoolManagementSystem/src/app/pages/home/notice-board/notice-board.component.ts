import { Component, OnInit, HostListener } from '@angular/core';
import { NoticeService } from 'src/app/components/admin/manage-notices/service/notice.service';


interface Notice {
  heading: string;
  description: string;
  startDate: Date;
  expiryDate: Date;
  image: any;
}

@Component({
  selector: 'app-notice-board',
  templateUrl: './notice-board.component.html',
  styleUrls: ['./notice-board.component.css']
})
export class NoticeBoardComponent implements OnInit{
  constructor(private notice: NoticeService){}
  notices: Notice[];
  item: any;
  ngOnInit(): void {
    this.notice.getActiveNotices().subscribe(
      (response) => {
        console.log('File uploaded successfully', response);
        this.notices = response
      },
      (error) => {
        console.log("Something went wrong", error);       
      }
    );
  }

  isPopupOpen = false;
  edit = false

  openPopup(notice): void {
    this.isPopupOpen = true;
    this.item = notice;
    let currentDate = new Date();
    if(this.item.expiryDate < currentDate){
      this.item.active = false;
    }
    else{
      this.item.active = true;
    }
  }

  closePopup(): void {
    this.isPopupOpen = false;
  }

}
