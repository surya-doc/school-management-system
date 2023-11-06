import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ResetPasswordService } from 'src/app/services/reset-password.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit{

  constructor(private resetPasswordService:ResetPasswordService,private router:Router){}

  reactiveForm : FormGroup;
  ngOnInit(): void {
      this.reactiveForm = new FormGroup(
        {
          password : new FormControl("",[Validators.required]),
          repassword : new FormControl("",[Validators.required]),
          otp : new FormControl("",[Validators.required]),
        }
      )
  }
  onSubmit(): void {
    const formdata = new FormData;
    formdata.append('userId',this.resetPasswordService.getUserId());
    formdata.append('newPassword',this.reactiveForm.value.password);
    formdata.append('otp',this.reactiveForm.value.otp);
    if(this.reactiveForm.value.password!=this.reactiveForm.value.repassword){
      console.log("passwords not match");
    }
    else{
      this.resetPasswordService.resetPaswword(formdata).subscribe(
        (data:any)=>{
          console.log("password reset successful");
          this.router.navigate(["/login"]);
        },
        (error)=>{
          console.log("password change request failed");
          
        }
      )
    }
  }
}
