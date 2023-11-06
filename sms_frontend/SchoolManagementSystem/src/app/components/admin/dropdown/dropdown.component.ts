import { Component, Input } from '@angular/core';
import { NoticeService } from '../manage-notices/service/notice.service';

@Component({
  selector: 'app-dropdown',
  templateUrl: './dropdown.component.html',
  styleUrls: ['./dropdown.component.css']
})
export class DropdownComponent {
  @Input() options: string[] = [];
  isDropdownOpen = false;
  constructor(private noticeService: NoticeService){
  }
  toggleDropdown(): void {
    this.isDropdownOpen = !this.isDropdownOpen;
  }
  onSelect(event: any): void {
    const option = event.target.value;
    this.noticeService.raiseSelectOption(option);
  }
}
