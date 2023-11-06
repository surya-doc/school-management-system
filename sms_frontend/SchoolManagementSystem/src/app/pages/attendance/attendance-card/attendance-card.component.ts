import { Component, Input, OnInit } from '@angular/core';
import { StudentService } from 'src/app/components/admin/manage-students/service/student.service';

interface attendance{
  status: boolean;
}

@Component({
  selector: 'app-attendance-card',
  templateUrl: './attendance-card.component.html',
  styleUrls: ['./attendance-card.component.css']
})
export class AttendanceCardComponent implements OnInit{
  @Input() item: any;
  user_id:any;
  date:Date = new Date();
  constructor(private studentService: StudentService){

  }

  ngOnInit(): void {
    this.user_id = this.item.user_id;
  }

  attendance:attendance;
  
  
  addPresentAttendance(): void{
    this.attendance = {"status": true};
    this.studentService.addStudentAttendance(this.user_id,this.attendance).subscribe(
      (data:any)=>{
        this.studentService.classOperationSuccess.next();
        console.log("attendance added");
        
      },
      (error)=>{
        console.log("error while adding attendance");
        
      }
    )
  }

  addAbsentAttendance(): void{
    this.attendance = {"status": false};
    this.studentService.addStudentAttendance(this.user_id,this.attendance).subscribe(
      (data:any)=>{
        this.studentService.classOperationSuccess.next();
        console.log("attendance added");
        
      },
      (error)=>{
        console.log("error while adding attendance");
        
      }
    )
  }
}
