import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { StudentService } from 'src/app/components/admin/manage-students/service/student.service';
import { TeacherService } from 'src/app/components/admin/manage-teachers/service/teacher.service';
import { LoginService } from 'src/app/services/login.service';
import { PasswordReset } from 'src/app/services/passwordReset.service';
import { ToasterService } from 'src/app/services/toaster.service';
import { UserServiceService } from 'src/app/services/user-service.service';
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  hobbies = ["Singing", "Dancing"]
  userId = null;
  role = null;
  profileDetails;
  inputValue: string = '';
  file = null;
  open: boolean = false;
  passwordUpdate: boolean = false;
  updatePasswordForm: FormGroup;
  addImage: boolean = false;


  constructor(private teacherService: TeacherService, private studentervice: StudentService, private loginService: LoginService, private toasterService: ToasterService, private formBuilder: FormBuilder, private updatePasswordreqService: PasswordReset, private userService: UserServiceService){
    this.updatePasswordForm = this.formBuilder.group(
      {
        oldPassword: [null, Validators.required],
        newPassword: [null, [Validators.required, Validators.minLength(8)]],
        reEntereedPassword: [null, Validators.required]
      }
    )
  }
  ngOnInit(): void {
    this.role = this.loginService.getRoleOfUser();
    this.userId = localStorage.getItem("user_id");
    this.getProfileDetails();
  }
  getProfileDetails(){
    if(this.role === "teacher"){
      this.getTeacherProfileDetails();
    }
    else{
      this.getStudentProfileDetails();
    }
  }
  getTeacherProfileDetails(){
    this.teacherService.getTeacher(this.userId).subscribe(
      (response) => {
        this.profileDetails = response;
        console.log('All teachers', this.profileDetails);
      },
      (error) => {
        console.log("Something went wrong", error);
      }
    );
  }
  getStudentProfileDetails(){
    this.studentervice.getStudent(this.userId).subscribe(
      (response) => {
        response = this.studentervice.mapToNewDetails(response);
        this.profileDetails = response;
        console.log('Student', response);
        // this.item = response;
      },
      (error) => {
        console.log("Something went wrong", error);
      }
    );
  }
  onInput(event: Event) {
    this.inputValue = (event.target as HTMLInputElement).value;
  }
  addHobby() {
    if (this.inputValue) {
      console.log('Input value:', this.inputValue);
      this.studentervice.addHobby(this.userId, this.inputValue).subscribe(
        (response) => {
          console.log('Student', response);
          this.toasterService.successSubject.next("Hobby added!!");
          this.getProfileDetails();
          this.inputValue = '';
        },
        (error) => {
          console.log("Something went wrong", error);
          this.toasterService.errorSubject.next("Something went wrong.");
        }
      );
    }
    return false
  }
  onFileSelected(event: any) {
    const fileInfo = event.target.files[0];
    this.file = fileInfo;
  }
  addDobCertificate(){
    if (this.file) {
      const formData = new FormData();
      formData.append('certificateImage', this.file);
      this.studentervice.addDobCertificate(this.userId, formData).subscribe(
        (response) => {
          console.log('Student', response);
          this.toasterService.successSubject.next("Certificate uploaded successfully!!");
          this.getProfileDetails();
          this.file = null;
        },
        (error) => {
          console.log("Something went wrong", error);
          this.toasterService.errorSubject.next("Something went wrong.")
        }
      );
    }
    return false;
  }
  openDiv(){
    this.open = true;
  }
  closeDiv(){
    this.open = false;
  }
  openImageEdit(){
    this.addImage = true;
  }
  closeImageEdit(){
    this.addImage = false;
  }
  openPasswordUpdateDiv(){
    this.passwordUpdate = true;
  }
  closePasswordUpdateDiv(){
    this.passwordUpdate = false;
  }
  updatePassword(){
    if(this.updatePasswordForm.value.newPassword === this.updatePasswordForm.value.reEntereedPassword){
      const formData = new FormData();
      const passwordUpdateData = {
        oldPassword: this.updatePasswordForm.value.oldPassword,
        newPassword: this.updatePasswordForm.value.newPassword,
      }
      formData.append('passwordUpdateData', JSON.stringify(passwordUpdateData));
      this.updatePasswordreqService.checkValidation(this.profileDetails.email, this.updatePasswordForm.value.oldPassword).subscribe(
        (response) => {
          console.log('Teacher created successfully', response);
          if(response){
            this.updatePasswordreqService.updatePasswordRequest(this.userId, formData).subscribe(
              (response) => {
                this.toasterService.successSubject.next("Password updated successfully!!");
                this.closePasswordUpdateDiv();
              },
              (error) => {
                this.toasterService.errorSubject.next("Something went wrong.");
              }
            );
          }
        },
        (error) => {
          this.toasterService.errorSubject.next("Something went wrong.");
        }
      );
    }
    else{
      this.toasterService.errorSubject.next("Password doesn't match.");
    }
  }

  updateProfilePic(){
    if (this.file) {
      const formData = new FormData();
      formData.append('profileimage', this.file);
      this.userService.updateProfileImage(this.userId, formData).subscribe(
        (response) => {
          console.log('Student', response);
          this.toasterService.successSubject.next("Profile pic updated successfully!!");
          this.closeImageEdit();
          this.getProfileDetails();
          this.file = null;
        },
        (error) => {
          console.log("Something went wrong", error);
          this.toasterService.errorSubject.next("Something went wrong.")
        }
      );
    }
    return false
  }
}