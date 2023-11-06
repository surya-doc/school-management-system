import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NoticeService } from '../service/notice.service';

@Component({
  selector: 'app-notice-detail',
  templateUrl: './notice-detail.component.html',
  styleUrls: ['./notice-detail.component.css']
})
export class NoticeDetailComponent implements OnInit {
  @Input() item: any;
  @Output() close: EventEmitter<void> = new EventEmitter<void>();

  constructor(private notice: NoticeService){}

  closePopup(): void {
    this.close.emit();
  }

  noticeImage: any;
  imageBase64: string | undefined;
  ngOnInit() {
    
    this.notice.getNotice(this.item.notice_id).subscribe(
      (response) => {
        this.imageBase64 = response.image;
      },
      (error) => {
      }
    );
  }

  checkStatus(expiryDate: Date){
    
    if(new Date(expiryDate) <= new Date()){
      
      return false;
    }
    return true;
  }
}
