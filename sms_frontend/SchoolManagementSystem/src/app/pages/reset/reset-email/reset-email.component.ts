import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ResetPasswordService } from 'src/app/services/reset-password.service';

@Component({
  selector: 'app-reset-email',
  templateUrl: './reset-email.component.html',
  styleUrls: ['./reset-email.component.css']
})
export class ResetEmailComponent implements OnInit{
  reactiveForm : FormGroup;

  constructor(private resetPasswordService:ResetPasswordService,private router:Router){}

  ngOnInit(): void {
    this.reactiveForm = new FormGroup(
      {
        email : new FormControl("email",[Validators.email,Validators.required]),
      }
    )
  }
  onSubmit(): void {
    // console.log(this.reactiveForm.value);
    // console.log(this.reactiveForm.value.email);
    const formData = new FormData();
    formData.append('email',JSON.stringify(this.reactiveForm.value.email));
    this.resetPasswordService.validateEmail(formData).subscribe(
      (data:any)=>{
        this.resetPasswordService.setUserId(data);
        const formData = new FormData();
        formData.append('userId',data);
        this.resetPasswordService.generateOtp(formData).subscribe(
          (data:any)=>{
            this.router.navigate(["/reset-password"]);
          },
          (error)=>{
            console.log("error while otp generation");
          }
        )
      },
      (error)=>{
        console.log("username not found");
      }
    )
  }
}
