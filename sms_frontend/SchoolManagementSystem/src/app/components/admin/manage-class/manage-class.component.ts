import { Component, OnDestroy, OnInit } from '@angular/core';
import { ClassDataService } from './service/class-data.service';
import { Subscription } from 'rxjs';
import { ToasterService } from 'src/app/services/toaster.service';

@Component({
  selector: 'app-manage-class',
  templateUrl: './manage-class.component.html',
  styleUrls: ['./manage-class.component.css'],
  providers: [ClassDataService]
})
export class ManageClassComponent implements OnInit {
  private classOperationSuccessSubscription: Subscription;
  constructor(private classDataService: ClassDataService, private toasterService: ToasterService){
    this.classOperationSuccessSubscription = this.classDataService.classOperationSuccess.subscribe(() => {
      this.getAllClasses();
    })
  }
  edit: boolean = false;
  classes: any = [];
  opType = "Create";
  pageNo: number = 0;

  openEdit(): void {
    this.edit = true;
  }

  closeEdit(): void {
    this.edit = false;
  }

  getAllClasses(){
    this.classDataService.getClassesPageWise(this.pageNo).subscribe(
      (response) => {
        this.classes = response;
      },
      (error) => {
        console.log("Something went wrong", error);
      }
    );
  }

  ngOnInit(){
    this.getAllClasses();
  }

  nextClick(){
    this.pageNo = this.pageNo+1;
    this.getAllClasses();
  }

  prevClick(){
    if(this.pageNo > 0){
      this.pageNo = this.pageNo-1;
      this.getAllClasses();
    }
  }
}
