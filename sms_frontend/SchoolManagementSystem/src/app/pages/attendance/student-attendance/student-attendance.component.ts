import { Component, OnInit } from '@angular/core';
import { ClassDataService } from 'src/app/components/admin/manage-class/service/class-data.service';
import { StudentService } from 'src/app/components/admin/manage-students/service/student.service';
import { TeacherService } from 'src/app/components/admin/manage-teachers/service/teacher.service';

@Component({
  selector: 'app-student-attendance',
  templateUrl: './student-attendance.component.html',
  styleUrls: ['./student-attendance.component.css']
})
export class StudentAttendanceComponent implements OnInit{

  students:any = [];
  userId:any;
  constructor(private studentService:StudentService, private teacherService: TeacherService, private classService: ClassDataService){
    this.studentService.classOperationSuccess.subscribe(()=>{
      this.getStudents();
    })
  }
  ngOnInit(): void {
    this.userId = localStorage.getItem("user_id");
    this.getStudents();
  }

  getStudents(): any {
    this.classService.getClassByClassTeacherId(this.userId).subscribe(
      (data:any)=>{
        console.log(data);
        
        this.studentService.classAttendance(data).subscribe(
          (data:any)=>{
            this.students = data;
            console.log(data);
          },
          (error)=>{
            console.log("error while fetching students for class");
          }
        )
      },
      (error)=>{
        console.log("error while fetching class from class teacher id");
      }
    )
  }
}
