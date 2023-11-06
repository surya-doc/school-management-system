import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageNoticesComponent } from './manage-notices.component';

describe('ManageNoticesComponent', () => {
  let component: ManageNoticesComponent;
  let fixture: ComponentFixture<ManageNoticesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManageNoticesComponent]
    });
    fixture = TestBed.createComponent(ManageNoticesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
