import { Component, OnInit } from '@angular/core';
import { ManageTeacherData } from './service/teacherDataService.service';
import { TeacherService } from './service/teacher.service';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-manage-teachers',
  templateUrl: './manage-teachers.component.html',
  styleUrls: ['./manage-teachers.component.css'],
  providers: [ManageTeacherData]
})
export class ManageTeachersComponent implements OnInit {
  constructor(private teachersDataService: ManageTeacherData, private teacherService: TeacherService, private loginService: LoginService){
    this.teacherService.classOperationSuccess.subscribe(() => {
      this.getAllTeachers();
    })
  }
  teachersMainData = [];
  allTeachers = this.teachersMainData;
  opType: string = 'Create';
  edit = false
  sortOptions = ['All', 'Date', 'Active', 'Inactive'];
  pageNo: number = 0;

  role = "";

  ngOnInit(){
    this.getAllTeachers();
    this.role = this.loginService.getRoleOfUser();
  }

  getAllTeachers(){
    let teachers = this.teacherService.getAllTeachersByPage(this.pageNo).subscribe(
      (response) => {
        this.teachersMainData = response;
        this.allTeachers = this.teachersMainData;
        console.log('All teachers', this.teachersMainData);
      },
      (error) => {
        console.log("Something went wrong", error);
      }
    );
  }

  openEdit(): void {
    this.edit = true;
  }

  closeEdit(): void {
    this.edit = false;
  }

  nextClick(){
    this.pageNo = this.pageNo+1;
    this.getAllTeachers();
  }

  prevClick(){
    if(this.pageNo > 0){
      this.pageNo = this.pageNo-1;
      this.getAllTeachers();
    }
  }
}
