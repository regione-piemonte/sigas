import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CalcoloAccertamentoComponent } from './calcolo-accertamento.component';

describe('CalcoloAccertamentoComponent', () => {
  let component: CalcoloAccertamentoComponent;
  let fixture: ComponentFixture<CalcoloAccertamentoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CalcoloAccertamentoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CalcoloAccertamentoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
