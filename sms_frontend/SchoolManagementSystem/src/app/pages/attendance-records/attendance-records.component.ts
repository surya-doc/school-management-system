import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ClassDataService } from 'src/app/components/admin/manage-class/service/class-data.service';
import { environment } from 'src/app/api-config';


@Component({
  selector: 'app-attendance-records',
  templateUrl: './attendance-records.component.html',
  styleUrls: ['./attendance-records.component.css']
})
export class AttendanceRecordsComponent {

  displayedColumns = ["UserId", "Roll No","Student Name","Present Days","Total Days","Attendance Percent"];
  attendanceData = null;
  classOptions = null;
  monthOptions = ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"];

  className = "";
  month = "";

  constructor(private classService:ClassDataService,private http:HttpClient){}

  ngOnInit(): void {
    this.classService.getAllclasses().subscribe(
      (data:any)=>{
        console.log(data);
        this.classOptions=data;
      }
    )
  }

  onSelectClass(event: any){
    this.className = event.target.value;
  }

  onSelectMonth(event: any){
    this.month = event.target.value;
  }

  OnSubmit(){
    console.log(this.className);
    console.log(this.monthOptions.indexOf(this.month)+1);
    const monthIndex = this.monthOptions.indexOf(this.month)+1;
    this.http.get(`${environment.apiUrl}/attendance/allRecords/${monthIndex}/${this.className}`).subscribe(
      (data:any)=>{
        console.log(data);
        this.attendanceData = data;
      }
    )
  }
}
