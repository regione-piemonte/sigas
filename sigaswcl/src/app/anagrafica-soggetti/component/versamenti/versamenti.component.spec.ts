import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VersamentiComponent } from './versamenti.component';

describe('VersamentiComponent', () => {
  let component: VersamentiComponent;
  let fixture: ComponentFixture<VersamentiComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VersamentiComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VersamentiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
