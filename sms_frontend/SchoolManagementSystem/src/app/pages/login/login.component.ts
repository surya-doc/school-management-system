import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { ToasterService } from 'src/app/services/toaster.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  reactiveForm : FormGroup;
  ngOnInit(): void {
      this.reactiveForm = new FormGroup(
        {
          email : new FormControl("",[Validators.email,Validators.required]),
          password : new FormControl("",[Validators.required])
        }
      )
  }

  constructor(private loginService: LoginService, private router: Router, private toasterService: ToasterService){}

  onSubmit(): void {
    console.log(this.reactiveForm.value);
    this.loginService.generateToken(this.reactiveForm.value).subscribe(
      (data : any) => {
        this.loginService.loginUser(data.jwtToken);
        this.loginService.setUser(data.username);
        this.toasterService.successSubject.next("Successfully loggedin");
        this.loginService.loginStatusSubject.next(true);
        this.router.navigate([""]);
      },
      (error)=>{this.toasterService.errorSubject.next("inavalid credentials");
      }
    );
  }
}
