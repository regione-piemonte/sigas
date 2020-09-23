import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SigasSpinnerComponent } from './sigas-spinner.component';

describe('SigasSpinnerComponent', () => {
  let component: SigasSpinnerComponent;
  let fixture: ComponentFixture<SigasSpinnerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SigasSpinnerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SigasSpinnerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
