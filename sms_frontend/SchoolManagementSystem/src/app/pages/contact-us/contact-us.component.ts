import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { environment } from 'src/app/api-config';
import { ToasterService } from 'src/app/services/toaster.service';

interface emailBody{
  to:string;
  subject:string;
  body:string;
}


@Component({
  selector: 'app-contact-us',
  templateUrl: './contact-us.component.html',
  styleUrls: ['./contact-us.component.css']
})
export class ContactUsComponent implements OnInit{
  reactiveForm : FormGroup;
  email:emailBody;
  constructor(private http:HttpClient,private toasterService:ToasterService){}

  ngOnInit(): void {
      this.reactiveForm = new FormGroup({
        email : new FormControl("",[Validators.email,Validators.required]),
        subject : new FormControl("",[Validators.required]),
        description : new FormControl("",[Validators.required])
      })
  }

  onSubmit(){
    const form = new FormData();
    form.append("to","");
    form.append("subject",this.reactiveForm.value.subject);
    form.append("body",this.reactiveForm.value.description);
    console.log(form);

    this.email = {"to":"","subject":this.reactiveForm.value.subject,"body":this.reactiveForm.value.description};
    console.log(this.email);
    
    
    this.http.post(`${environment.apiUrl}/sendEmail/`,this.email).subscribe(
      (data:any)=>{
        this.toasterService.successSubject.next("Mail Sent");
      }
    );
    console.log(this.reactiveForm.valid);
  }
}
