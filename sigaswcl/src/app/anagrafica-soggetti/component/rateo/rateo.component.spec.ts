import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RateoComponent } from './rateo.component';

describe('RateoComponent', () => {
  let component: RateoComponent;
  let fixture: ComponentFixture<RateoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RateoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RateoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
