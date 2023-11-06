import { Component, Input } from '@angular/core';
import { ClassDataService } from '../service/class-data.service';

@Component({
  selector: 'app-class-card',
  templateUrl: './class-card.component.html',
  styleUrls: ['./class-card.component.css']
})
export class ClassCardComponent {
  @Input() item: any;
  edit: boolean = false;
  opType = "Update"
  teachers = null;
  open: boolean = false;

  constructor(private classService: ClassDataService){}

  openEdit(): void {
    this.edit = true;
  }

  closeEdit(): void {
    this.edit = false;
  }

  openDiv(qualification: string){
    this.open = true;
    this.classService.getTeachersByQualification(qualification).subscribe(
      (response) => {
        this.teachers = response;
      },
      (error) => {
        console.log('Something went wrong', error);
      }
    );
  }

  closeDiv(){
    this.open = false;
  }

  assignClassTeacher(class_id: number, event: any){
    let teacher_id = parseInt(event.target.value, 10)
    this.classService.assignClassTeacher(class_id, teacher_id).subscribe(
      (response) => {
        this.classService.classOperationSuccess.next();
      },
      (error) => {
        console.log('Something went wrong', error);
      }
    );
  }
}
