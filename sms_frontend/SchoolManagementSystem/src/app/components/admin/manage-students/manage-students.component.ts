import { Component, OnInit } from '@angular/core';
import { ManageStudentData } from './service/studentData.service';
import { StudentService } from './service/student.service';
import { ClassDataService } from '../manage-class/service/class-data.service';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-manage-students',
  templateUrl: './manage-students.component.html',
  styleUrls: ['./manage-students.component.css'],
  providers: [ManageStudentData]
})
export class ManageStudentsComponent implements OnInit {
  constructor(private classDataService: ClassDataService, private studentsDataService: ManageStudentData, private studentService: StudentService,private loginService: LoginService){
    this.studentService.classOperationSuccess.subscribe(() => {
      this.getAllStudents();
    })
  }
  studentsMainData = [];
  allStudents;
  edit = false
  opType: string = 'Create';
  availableClasses = null;
  pageNo: number = 0;
  classId: number = null;
  role = null;
  openEdit(): void {
    this.edit = true;
  }

  closeEdit(): void {
    this.edit = false;
  }

  getAllClasses(){
    this.classDataService.getAllclasses().subscribe(
      (response) => {
        this.availableClasses = response;
        let array = [...this.availableClasses];
        
      },
      (error) => {
        console.log("Something went wrong", error);
      }
    );
  }

  selectClass(event: any){
    this.classId = event.target.value;
    console.log(this.classId);
    this.pageNo = 0;
    this.getSTudentsByClassId();
  }

  getSTudentsByClassId(){
    this.studentService.getStudentsByClassId(this.classId, this.pageNo).subscribe(
      (response) => {
        console.log('Students', response);
        this.allStudents = response;
      },
      (error) => {
        console.log("Something went wrong", error);
      }
    );
    console.log(this.classId);
  }

  ngOnInit(){
    this.pageNo = 0;
    this.getAllStudents();
    console.log(this.classId);
    this.getAllClasses();
    this.role = this.loginService.getUserRole();
  }

  getAllStudents(){
    this.studentService.getStudentsByPage(this.pageNo).subscribe(
      (response) => {
        this.studentsMainData = response;
        this.allStudents = this.studentsMainData;
        console.log('All teachers', this.studentsMainData);
      },
      (error) => {
        console.log("Something went wrong", error);
      }
    );
  }

  nextClick(){
    this.pageNo = this.pageNo+1;
    if(this.classId === null){
      this.getAllStudents();
    }
    else{
      this.getSTudentsByClassId();
    }
  }

  prevClick(){
    if(this.pageNo > 0){
      this.pageNo = this.pageNo-1;
      if(this.classId === null){
        this.getAllStudents();
      }
      else{
        this.getSTudentsByClassId();
      }
    }
  }
}
