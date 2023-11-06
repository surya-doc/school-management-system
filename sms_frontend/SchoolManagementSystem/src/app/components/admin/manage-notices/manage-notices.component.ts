import { Component, OnInit } from '@angular/core';
import { ManageNoticeData } from './service/manageNoticeData.service';
import { NoticeCRUD } from './service/noticeCRUD.service';
import { HttpClient } from '@angular/common/http';
import { NoticeService } from './service/notice.service';

@Component({
  selector: 'app-manage-notices',
  templateUrl: './manage-notices.component.html',
  styleUrls: ['./manage-notices.component.css'],
  providers: [ManageNoticeData, NoticeCRUD]
})
export class ManageNoticesComponent implements OnInit {
  dummyNoticeMain = [];
  allNotices = [];
  sortOptions = ['All', 'Date', 'Active', 'Inactive'];
  opType = "Create";
  pageNo: number = 0;
  constructor(private noticeDataService: ManageNoticeData, private notice: NoticeService, private http: HttpClient){
    this.notice.classOperationSuccess.subscribe(() => {
      this.getAllNotices();
    })
  }
  edit = false
  
  openEdit(): void {
    this.edit = true;
  }

  closeEdit(): void {
    this.edit = false;
  }

  getAllNotices(){
    this.notice.getAllNotices(this.pageNo).subscribe(
      (response) => {
        console.log('File uploaded successfully', response);
        this.dummyNoticeMain = response
        this.allNotices = response;
      },
      (error) => {
        console.log("Something went wrong", error);       
      }
    );
  }

  ngOnInit(): void {
    this.pageNo = 0;
    this.getAllNotices();
    this.notice.selectedOptionSubject.subscribe((value) => {
      console.log(value);
      this.allNotices = this.dummyNoticeMain;
      if(value === "Date"){
        this.allNotices = this.allNotices.sort((a, b) => {
          const date_1 = new Date(a.startDate);
          const date_2 = new Date(b.startDate);
          if(date_1 > date_2) {
            return 1;
          }
          if(date_1 < date_2) {
            return -1;
          }
          return 0;
        });
      }
      else if(value === "Active"){
        this.allNotices = this.allNotices.filter((notice) => {
          return notice.status === true;
        })
      }
      else if(value === "Inactive"){
        this.allNotices = this.allNotices.filter((notice) => {
          return notice.status === false;
        })
      }
      else if(value === "All"){
        this.allNotices = this.dummyNoticeMain;
      }
    })
  }
  imageSrc: string = '';
  imageToShow: any;
  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener(
      'load',
      () => {
        this.imageToShow = reader.result;
      },
      false
    );

    if (image) {
      reader.readAsDataURL(image);
    }
  }

  nextClick(){
    this.pageNo = this.pageNo+1;
    this.getAllNotices();
  }

  prevClick(){
    if(this.pageNo > 0){
      this.pageNo = this.pageNo-1;
      this.getAllNotices();
    }
  }
}
