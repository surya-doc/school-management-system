import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClassCreateUpdateComponent } from './class-create-update.component';

describe('ClassCreateUpdateComponent', () => {
  let component: ClassCreateUpdateComponent;
  let fixture: ComponentFixture<ClassCreateUpdateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ClassCreateUpdateComponent]
    });
    fixture = TestBed.createComponent(ClassCreateUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
