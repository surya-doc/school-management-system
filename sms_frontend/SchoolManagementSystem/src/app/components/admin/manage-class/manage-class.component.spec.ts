import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageClassComponent } from './manage-class.component';

describe('ManageClassComponent', () => {
  let component: ManageClassComponent;
  let fixture: ComponentFixture<ManageClassComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManageClassComponent]
    });
    fixture = TestBed.createComponent(ManageClassComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
