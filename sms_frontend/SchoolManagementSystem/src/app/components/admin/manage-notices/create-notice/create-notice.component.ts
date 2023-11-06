import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import{ FormGroup, FormControl, FormBuilder, Validators, ValidatorFn, AbstractControl } from '@angular/forms'
import { HttpClient } from '@angular/common/http';
import { NoticeService } from '../service/notice.service';
import { ToasterService } from 'src/app/services/toaster.service';

@Component({
  selector: 'app-create-notice',
  templateUrl: './create-notice.component.html',
  styleUrls: ['./create-notice.component.css']
})
export class CreateNoticeComponent implements OnInit{
  @Input() item: any;
  @Output() close: EventEmitter<void> = new EventEmitter<void>();
  @Input() opType: string | undefined;
  
  noticeCreateForm: FormGroup;

  file = null;

  constructor(private fb: FormBuilder, private noticeService: NoticeService, private http: HttpClient, 
    private toasterService: ToasterService) {
    this.noticeCreateForm = this.fb.group({
      heading: ['', Validators.required],
      description: ['', Validators.required],
      startDate: ['', [Validators.required, this.dateValidation]],
      expiryDate: ['', [Validators.required, this.dateValidation]],
      image: [null]
    });
  }

  ngOnInit() {
    if (this.item) {
      this.noticeCreateForm.patchValue(this.item);
    }
  }

  closeEdit(): void {
    this.close.emit();
  }

  dateValidation(control: FormControl){
    const currentDate = new Date();
    const selectedDate = new Date(control.value);
    if (selectedDate < currentDate) {
      return { 'pastDate': true };
    }
    
    return null;
  }


  onFileSelected(event: any) {
    const fileInfo = event.target.files[0];
    this.file = fileInfo;
  }

  createNotice() {
    const formData = new FormData();

    formData.append('heading', this.noticeCreateForm.value.heading);
    formData.append('description', this.noticeCreateForm.value.description);
    formData.append('startDate', this.noticeCreateForm.value.startDate);
    formData.append('expiryDate', this.noticeCreateForm.value.expiryDate);

    if (this.file) {
      formData.append('file', this.file);
    }

    if(this.opType === "Create"){
      this.noticeService.createNotice(formData).subscribe(
        (response) => {
          console.log('File uploaded successfully', response);
          this.toasterService.successSubject.next("Notice created successfully!!");
          this.noticeService.classOperationSuccess.next();
        },
        (error) => {
          console.log("Something went wrong", error);       
        }
      );
    }
    else{
      console.log(this.item);
      
      this.noticeService.updateNotice(this.item?.notice_id, formData).subscribe(
        (response) => {
          console.log('File uploaded successfully', response);
          this.toasterService.successSubject.next("Notice updated successfully!!");
          this.noticeService.classOperationSuccess.next();
        },
        (error) => {
          console.log("Something went wrong", error);       
        }
      );
    }

    this.closeEdit();
  }
}