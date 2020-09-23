import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccertamentiComponent } from './accertamenti.component';

describe('AccertamentiComponent', () => {
  let component: AccertamentiComponent;
  let fixture: ComponentFixture<AccertamentiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccertamentiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccertamentiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
