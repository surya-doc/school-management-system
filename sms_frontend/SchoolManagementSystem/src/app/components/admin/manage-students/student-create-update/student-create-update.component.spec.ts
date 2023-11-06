import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentCreateUpdateComponent } from './student-create-update.component';

describe('StudentCreateUpdateComponent', () => {
  let component: StudentCreateUpdateComponent;
  let fixture: ComponentFixture<StudentCreateUpdateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StudentCreateUpdateComponent]
    });
    fixture = TestBed.createComponent(StudentCreateUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
