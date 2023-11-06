import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-student-details',
  templateUrl: './student-details.component.html',
  styleUrls: ['./student-details.component.css']
})
export class StudentDetailsComponent {
  @Input() item: any;
  @Output() close: EventEmitter<void> = new EventEmitter<void>();

  closePopup(): void {
    this.close.emit();
  }
}
