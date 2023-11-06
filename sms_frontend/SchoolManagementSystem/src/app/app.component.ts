import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { ToasterService } from './services/toaster.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: []
})
export class AppComponent {
  title = 'SchoolManagementSystem';
  constructor(private toastr: ToastrService, private toasterServics: ToasterService){
    this.toasterServics.successSubject.subscribe((message) => {
      this.showSuccess(message);
    })

    this.toasterServics.errorSubject.subscribe((message) => {
      this.showError(message);
    })
  }
  

  showSuccess(message) {
    this.toastr.success(message, "Success", {closeButton: true, progressBar: true, positionClass: 'toast-bottom-right'});
  }

  showError(message) {
    this.toastr.error(message, "Error", {closeButton: true, progressBar: true, positionClass: 'toast-bottom-right'});
  }
}