import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TeacherService } from '../service/teacher.service';
import { ToasterService } from 'src/app/services/toaster.service';
import { Teacher } from 'src/app/services/models/teacher.model';

@Component({
  selector: 'app-teacher-create-update',
  templateUrl: './teacher-create-update.component.html',
  styleUrls: ['./teacher-create-update.component.css']
})
export class TeacherCreateUpdateComponent implements OnInit {
  @Input() teacherDetail: any;
  @Input() opType: string | undefined;
  @Output() close: EventEmitter<void> = new EventEmitter<void>();

  teacherCreateForm: FormGroup;
  file = null;

  classes = [];
  subjects = [];

  constructor(private fb: FormBuilder, private teacherService: TeacherService, private toasterService: ToasterService) {
    this.teacherCreateForm = this.fb.group({
      id: [null, Validators.required],
      image: [null, Validators.required],
      fullName: ['', [
        Validators.required,
        Validators.minLength(2),
        Validators.maxLength(50),
        Validators.pattern(/^[a-zA-Z\s]*$/)
      ]],
      dob: [null, Validators.required],
      email: ['abcd@gmail.com', [Validators.required, Validators.email]],
      phoneNumber: ['', [Validators.required, this.phoneNumber]],
      qualification: ['', Validators.required],
      gender: ['', Validators.required],
      subjects: [],
      classes: [],
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
    if(this.teacherDetail?.qualification){
      this.getClassByQualification(this.teacherDetail?.qualification);
    }

    if (this.teacherDetail) {
      console.log(this.teacherDetail);
      
      this.teacherCreateForm.patchValue({
        id: this.teacherDetail?.id,
        fullName: this.teacherDetail?.fullName,
        dob: this.teacherDetail?.dob,
        email: this.teacherDetail?.email,
        phoneNumber: this.teacherDetail?.phoneNumber,
        qualification: this.teacherDetail?.qualification,
        gender: this.teacherDetail?.gender,
        cur_street: this.teacherDetail?.current_address.street_address,
        cur_city: this.teacherDetail?.current_address.city,
        cur_state: this.teacherDetail?.current_address.state,
        cur_country: this.teacherDetail?.current_address.country,
        cur_postalCode: this.teacherDetail?.current_address.postal_code,
        per_street: this.teacherDetail?.permanent_address.street_address,
        per_city: this.teacherDetail?.permanent_address.city,
        per_state: this.teacherDetail?.permanent_address.state,
        per_country: this.teacherDetail?.permanent_address.country,
        per_postalCode: this.teacherDetail?.permanent_address.postal_code,
      });
    }
    this.getSubjects();
  }
  
  getSubjects(){
    this.teacherService.getAllSubjects().subscribe(
      (response) => {
        if(this.teacherDetail){
          response = response.filter((res) => !this.teacherDetail.subjects.some(item2 => item2.subject_id === res.subject_id));
        }
        console.log('All subjects', response);
        this.subjects = response;
      },
      (error) => {
        console.log("Something went wrong", error);
      }
    );
  }

  getClasses(event: any){
    console.log("This is a list of classes", event.target.value);
    this.getClassByQualification(event.target.value);
  }
  
  getClassByQualification(qualification){
    this.teacherService.getClassesByQualification(qualification).subscribe(
      (response) => {
        if(this.teacherDetail){
          response = response.filter((res) => !this.teacherDetail.classes.some(item2 => item2.class_id === res.class_id));
          console.log('All classes', response, this.teacherDetail.classes);
        }
        this.classes = response;
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

  createTeacher() {
    console.log(this.teacherCreateForm.value);
    const teacherData: Teacher = {
      id: this.teacherCreateForm.value.id,
      fullName: this.teacherCreateForm.value.fullName,
      dob: this.teacherCreateForm.value.dob,
      email: this.teacherCreateForm.value.email,
      phoneNumber: this.teacherCreateForm.value.phoneNumber,
      qualification: this.teacherCreateForm.value.qualification,
      subjects: this.teacherCreateForm.value.subjects,
      classes: this.teacherCreateForm.value.classes,
      current_Address: {
        street_address: this.teacherCreateForm.value.cur_street,
        city: this.teacherCreateForm.value.cur_city,
        state: this.teacherCreateForm.value.cur_state,
        postal_code: this.teacherCreateForm.value.cur_postalCode,
        country: this.teacherCreateForm.value.cur_country
      },
      permanent_Address: {
        street_address: this.teacherCreateForm.value.per_street,
        city: this.teacherCreateForm.value.per_city,
        state: this.teacherCreateForm.value.per_state,
        postal_code: this.teacherCreateForm.value.per_postalCode,
        country: this.teacherCreateForm.value.per_country
      },
      profilePic: '',
      gender: this.teacherCreateForm.value.gender
    };
    const formData = new FormData();
    formData.append('teacherData', JSON.stringify(teacherData));

    if(this.file){
      formData.append('image', this.file);
    }

    if(this.opType === "Create"){
      this.teacherService.createTeacher(formData).subscribe(
        (response) => {
          console.log('Teacher created successfully', response);
          this.toasterService.successSubject.next("Teacher created successfully!!");
          this.teacherService.classOperationSuccess.next();
        },
        (error) => {
          console.log("Something went wrong", error);
        }
        );
      }
      else{
        this.teacherService.updateTeacher(this.teacherCreateForm.value.id, formData).subscribe(
          (response) => {
            console.log('Teacher updated successfully', response);
            this.toasterService.successSubject.next("Teacher updated successfully!!");
            this.teacherService.classOperationSuccess.next();
          },
          (error) => {
            console.log("Something went wrong", error);
        }
        );
      }
    this.closeEdit();
  }
}
