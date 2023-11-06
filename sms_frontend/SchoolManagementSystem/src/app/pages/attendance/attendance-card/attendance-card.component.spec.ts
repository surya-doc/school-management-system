import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AttendanceCardComponent } from './attendance-card.component';

describe('AttendanceCardComponent', () => {
  let component: AttendanceCardComponent;
  let fixture: ComponentFixture<AttendanceCardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AttendanceCardComponent]
    });
    fixture = TestBed.createComponent(AttendanceCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
