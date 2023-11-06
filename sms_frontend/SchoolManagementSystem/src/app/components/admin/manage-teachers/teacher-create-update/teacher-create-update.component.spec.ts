import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeacherCreateUpdateComponent } from './teacher-create-update.component';

describe('TeacherCreateUpdateComponent', () => {
  let component: TeacherCreateUpdateComponent;
  let fixture: ComponentFixture<TeacherCreateUpdateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TeacherCreateUpdateComponent]
    });
    fixture = TestBed.createComponent(TeacherCreateUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
