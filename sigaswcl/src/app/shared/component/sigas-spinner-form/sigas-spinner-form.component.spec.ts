import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SigasSpinnerFormComponent } from './sigas-spinner-form.component';

describe('SigasSpinnerFormComponent', () => {
  let component: SigasSpinnerFormComponent;
  let fixture: ComponentFixture<SigasSpinnerFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SigasSpinnerFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SigasSpinnerFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
