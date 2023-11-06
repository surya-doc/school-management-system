import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { StudentService } from '../service/student.service';
import { ClassDataService } from '../../manage-class/service/class-data.service';
import { ToasterService } from 'src/app/services/toaster.service';
import { Student } from 'src/app/services/models/student.model';

@Component({
  selector: 'app-student-create-update',
  templateUrl: './student-create-update.component.html',
  styleUrls: ['./student-create-update.component.css']
})
export class StudentCreateUpdateComponent implements OnInit {
  @Input() studentDetail: any;
  @Input() opType: string | undefined;
  @Output() close: EventEmitter<void> = new EventEmitter<void>();

  studentCreateForm: FormGroup;
  file = null;
  availableClasses = null;
  
  constructor(private fb: FormBuilder, private studentService: StudentService, private classDataService: ClassDataService, private toasterService: ToasterService) {
    this.studentCreateForm = this.fb.group({
      id: [null, Validators.required],
      image: [null, Validators.required],
      fullName: ['', [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(50),
        Validators.pattern(/^[a-zA-Z\s]*$/)
      ]],
      dob: [null, Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', [Validators.required, this.phoneNumber]],
      gender: ['', Validators.required],
      classId: [null, Validators.required],
      cur_street: ['', Validators.required],
      cur_city: ['', Validators.required],
      cur_state: ['', Validators.required],
      cur_country: ['', Validators.required],
      cur_postalCode: ['', Validators.required],
      per_street: ['', Validators.required],
      per_city: ['', Validators.required],
      per_state: ['', Validators.required],
      per_country: ['', Validators.required],
      per_postalCode: ['', Validators.required]
    });
  }

  ngOnInit() {
    if (this.studentDetail) {
      console.log(this.studentDetail);
      this.studentCreateForm.patchValue({
        id: this.studentDetail?.id,
        fullName: this.studentDetail?.fullName,
        dob: this.studentDetail?.dob,
        email: this.studentDetail?.email,
        phoneNumber: this.studentDetail?.phoneNumber,
        qualification: this.studentDetail?.qualification,
        gender: this.studentDetail?.gender,
        classId: this.studentDetail?.classDetail,
        cur_street: this.studentDetail?.current_Address.street_address,
        cur_city: this.studentDetail?.current_Address.city,
        cur_state: this.studentDetail?.current_Address.state,
        cur_country: this.studentDetail?.current_Address.country,
        cur_postalCode: this.studentDetail?.current_Address.postal_code,
        per_street: this.studentDetail?.permanent_Address.street_address,
        per_city: this.studentDetail?.permanent_Address.city,
        per_state: this.studentDetail?.permanent_Address.state,
        per_country: this.studentDetail?.permanent_Address.country,
        per_postalCode: this.studentDetail?.permanent_Address.postal_code,
      });
    }
    this.classDataService.getAllclasses().subscribe(
      (response) => {
        this.availableClasses = response;
        console.log('All Classes', this.availableClasses);
      },
      (error) => {
        console.log("Something went wrong", error);
      }
    );
  }

  
  closeEdit(): void {
    this.close.emit();
  }
  
  phoneNumber(control: FormControl){
    let phoneNumber = control.value.replace('-', '');
    if (phoneNumber.length < 10) {
      return { 'phoneNumber': true };
    } 
    return null;
  }

  onFileSelected(event: any) {
    const fileInfo = event.target.files[0];
    this.file = fileInfo;
  }

  createStudent() {
    console.log(this.studentCreateForm.value);
    console.log(this.studentCreateForm.value);
    const studentData: Student = {
      id: this.studentCreateForm.value.id,
      fullName: this.studentCreateForm.value.fullName,
      dob: this.studentCreateForm.value.dob,
      email: this.studentCreateForm.value.email,
      phoneNumber: this.studentCreateForm.value.phoneNumber,
      gender: this.studentCreateForm.value.gender,
      classId: this.studentCreateForm.value.classId,
      current_Address: {
        street_address: this.studentCreateForm.value.cur_street,
        city: this.studentCreateForm.value.cur_city,
        state: this.studentCreateForm.value.cur_state,
        postal_code: this.studentCreateForm.value.cur_postalCode,
        country: this.studentCreateForm.value.cur_country
      },
      permanent_Address: {
        street_address: this.studentCreateForm.value.per_street,
        city: this.studentCreateForm.value.per_city,
        state: this.studentCreateForm.value.per_state,
        postal_code: this.studentCreateForm.value.per_postalCode,
        country: this.studentCreateForm.value.per_country
      },
      profilePic: '',
      hobbies: []
    };
    const formData = new FormData();
    formData.append('studentData', JSON.stringify(studentData));

    if(this.file){
      formData.append('image', this.file);
    }

    if(this.opType === "Create"){
      this.studentService.createStudent(formData).subscribe(
        (response) => {
          console.log('Student created successfully.', response);
          this.toasterService.successSubject.next("Student created successfully.");
          this.studentService.classOperationSuccess.next();
        },
        (error) => {
          console.log("Something went wrong", error);
        }
      );
    }
    else{
      console.log(formData);
      this.studentService.updateStudent(this.studentCreateForm.value.id, formData).subscribe(
        
        (response) => {
          console.log('Student updated successfully.', response);
          this.toasterService.successSubject.next("Student updated successfully.");
          this.studentService.classOperationSuccess.next();
        },
        (error) => {
          console.log("Something went wrong", error);
        }
      );
    }
    this.closeEdit();
  }
}
