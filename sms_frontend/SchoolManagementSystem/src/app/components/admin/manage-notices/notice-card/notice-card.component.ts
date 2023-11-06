import { Component, Input } from '@angular/core';
import { NoticeService } from '../service/notice.service';
import { ToasterService } from 'src/app/services/toaster.service';

@Component({
  selector: 'app-notice-card',
  templateUrl: './notice-card.component.html',
  styleUrls: ['./notice-card.component.css'],
})
export class NoticeCardComponent {
  @Input() item: any;
  isPopupOpen = false;
  edit = false
  opType = "Update"

  constructor(private noticeService: NoticeService, private toasterService: ToasterService){}

  openPopup(): void {
    this.isPopupOpen = true;
  }

  closePopup(): void {
    this.isPopupOpen = false;
  }

  openEdit(): void {
    this.edit = true;
  }

  closeEdit(): void {
    this.edit = false;
  }

  deleteNotice(){
    let response = window.confirm("Delete notice?");
    if(response){
     console.log("Deleted.");
     this.noticeService.deleteNotice(this.item.notice_id).subscribe(
       (response) => {
         console.log(response);
         this.toasterService.successSubject.next("Notice deleted.");
         this.noticeService.classOperationSuccess.next();
       },
       (error) => {
         console.log("Something went wrong", error);
         this.toasterService.errorSubject.next("Something went wrong.");
       }
     );
    }
    else{
     console.log("User staies.");
     
    }
   }
}