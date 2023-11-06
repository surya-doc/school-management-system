import { Component, Input, OnInit } from '@angular/core';
import { StudentService } from '../service/student.service';
import { ToasterService } from 'src/app/services/toaster.service';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-student-card',
  templateUrl: './student-card.component.html',
  styleUrls: ['./student-card.component.css']
})
export class StudentCardComponent implements OnInit{

  constructor(private studentService: StudentService,private loginService: LoginService, private toasterService: ToasterService){}

  @Input() item: any;
  studentDetail = null;
  isPopupOpen = false;
  edit = false
  opType = "Update";

  role = null;

  ngOnInit(): void {
      this.role = this.loginService.getUserRole();
  }
  
  openPopup(): void {
    this.getStudent();
    this.isPopupOpen = true;
  }

  closePopup(): void {
    this.isPopupOpen = false;
  }

  openEdit(): void {
    this.getStudent();
    this.edit = true;
  }

  closeEdit(): void {
    this.edit = false;
  }

  getStudent(){
    this.studentService.getStudent(this.item.user_id).subscribe(
      (response) => {
        this.studentDetail = response;
        console.log('All teachers', this.studentDetail);
      },
      (error) => {
        console.log("Something went wrong", error);
      }
    );
  }

  deleteStudent(){
   let response = window.confirm("Delete student?");
   if(response){
    console.log("Deleted.");
    this.studentService.deleteStudent(this.item.user_id).subscribe(
      (response) => {
        console.log(response);
        this.toasterService.successSubject.next("Student deleted successfully!!");
        this.studentService.classOperationSuccess.next();
      },
      (error) => {
        this.toasterService.errorSubject.next("Something went wrong.");
        console.log("Something went wrong", error);
      }
    );
   }
   else{
    console.log("User staies.");
    
   }
  }
}
