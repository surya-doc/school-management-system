import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-teacher-details',
  templateUrl: './teacher-details.component.html',
  styleUrls: ['./teacher-details.component.css']
})
export class TeacherDetailsComponent {
  @Input() item: any;
  @Output() close: EventEmitter<void> = new EventEmitter<void>();

  closePopup(): void {
    this.close.emit();
  }
}
