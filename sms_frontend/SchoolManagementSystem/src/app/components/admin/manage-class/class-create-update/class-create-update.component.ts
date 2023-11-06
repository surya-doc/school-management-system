import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { TeacherService } from '../../manage-teachers/service/teacher.service';
import { ClassDataService } from '../service/class-data.service';
import { ToasterService } from 'src/app/services/toaster.service';

@Component({
  selector: 'app-class-create-update',
  templateUrl: './class-create-update.component.html',
  styleUrls: ['./class-create-update.component.css'],
})
export class ClassCreateUpdateComponent implements OnInit {
  @Input() item: any;
  @Output() close: EventEmitter<void> = new EventEmitter<void>();
  @Input() opType: String | undefined;
  teachersList = null;

  classCreateForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private teacherService: TeacherService,
    private classService: ClassDataService,
    private toasterService: ToasterService
  ) {
    this.classCreateForm = this.fb.group({
      class_id: ['', Validators.required],
      class_name: ['', Validators.required],
      min_qualification: ['', Validators.required],
      teacher_id: [null],
    });
  }

  ngOnInit() {
    // this.teacherService.getAllTeachers().subscribe(
    //   (response) => {
    //     this.teachersList = response;
    //     console.log('All teachers', this.teachersList);
    //   },
    //   (error) => {
    //     console.log("Something went wrong", error);
    //   }
    // );
    if (this.item) {
      this.classCreateForm.patchValue(this.item);
    }
  }

  getTeachers(event: any) {
    console.log('This is a list of classes', event.target.value);
    this.classService.getTeachersByQualification(event.target.value).subscribe(
      (response) => {
        console.log('All classes', response);
        this.teachersList = response;
      },
      (error) => {
        console.log('Something went wrong', error);
      }
    );
  }

  createClass() {
    console.log(this.classCreateForm.value);
    if (this.opType === 'Create') {
      this.classService.createClass(this.classCreateForm.value).subscribe(
        (response) => {
          this.toasterService.successSubject.next("Class added successfully!!");
          this.classService.classOperationSuccess.next();
        },
        (error) => {
          console.log('Something went wrong', error);
          this.toasterService.errorSubject.next("Something went wrong.");
        }
      );
    } else {
      this.classService.updateClass(this.classCreateForm.value).subscribe(
        (response) => {
          
          this.toasterService.successSubject.next("Class updated successfully!!");
          this.classService.classOperationSuccess.next();
        },
        (error) => {
          console.log('Something went wrong', error);
          this.toasterService.errorSubject.next("Something went wrong.");
        }
      );
    }
    this.closeEdit();
  }

  closeEdit(): void {
    this.close.emit();
  }
}
