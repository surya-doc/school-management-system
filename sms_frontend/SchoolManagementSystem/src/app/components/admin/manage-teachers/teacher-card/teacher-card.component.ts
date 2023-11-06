import { Component, Input, OnInit } from '@angular/core';
import { TeacherService } from '../service/teacher.service';
import { ToasterService } from 'src/app/services/toaster.service';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-teacher-card',
  templateUrl: './teacher-card.component.html',
  styleUrls: ['./teacher-card.component.css']
})
export class TeacherCardComponent implements OnInit {

  constructor(private teacherService: TeacherService, private toasterService: ToasterService, private loginService: LoginService){}

  @Input() item: any;
  teacherDetail = null;
  opType: string = 'Update';

  isPopupOpen = false;
  edit = false;

  role = "";

  closePopup(): void {
    this.isPopupOpen = false;
  }

  openEdit(): void {
    this.teacherService.getTeacher(this.item.user_id).subscribe(
      (response) => {
        this.teacherDetail = response;
        console.log('All teachers', this.teacherDetail);
      },
      (error) => {
        console.log("Something went wrong", error);
      }
    );
    
    this.edit = true;
  }

  ngOnInit(){
    this.role = this.loginService.getRoleOfUser();
  }

  closeEdit(): void {
    this.edit = false;
  }

  getTeacher(){
    this.teacherService.getTeacher(this.item.user_id).subscribe(
      (response) => {
        this.teacherDetail = response;
        console.log('All teachers', this.teacherDetail);
      },
      (error) => {
        console.log("Something went wrong", error);
      }
    );
    this.isPopupOpen = true;
  }

  deleteTeacher(){
   let response = window.confirm("Delete teacher?");
   if(response){
    console.log("Deleted.");
    this.teacherService.deleteTeacher(this.item.user_id).subscribe(
      (response) => {
        console.log(response);
        this.toasterService.successSubject.next("Teacher deleted successfully!!");
        this.teacherService.classOperationSuccess.next();
      },
      (error) => {
        console.log("Something went wrong", error);
        this.toasterService.errorSubject.next("Something went wrong.")
      }
    );
   }
   else{
    console.log("User staies.");
    
   }
  }
}
