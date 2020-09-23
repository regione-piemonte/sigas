import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnagraficaSoggettoComponent } from './dettaglio-anagrafica-soggetto.component';

describe('AnagraficaSoggettoComponent', () => {
  let component: AnagraficaSoggettoComponent;
  let fixture: ComponentFixture<AnagraficaSoggettoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnagraficaSoggettoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnagraficaSoggettoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
