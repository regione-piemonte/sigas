import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScartiComponent } from './scarti.component';

describe('ScartiComponent', () => {
  let component: ScartiComponent;
  let fixture: ComponentFixture<ScartiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScartiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScartiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
