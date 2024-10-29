import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ElencoAccreditamentoComponent } from './elenco-accreditamento.component';

describe('ElencoAccreditamentoComponent', () => {
  let component: ElencoAccreditamentoComponent;
  let fixture: ComponentFixture<ElencoAccreditamentoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ElencoAccreditamentoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ElencoAccreditamentoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
