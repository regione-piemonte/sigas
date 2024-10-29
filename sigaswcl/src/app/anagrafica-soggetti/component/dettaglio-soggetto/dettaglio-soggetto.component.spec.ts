import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DettaglioSoggettoComponent } from './dettaglio-soggetto.component';

describe('DettaglioSoggettoComponent', () => {
  let component: DettaglioSoggettoComponent;
  let fixture: ComponentFixture<DettaglioSoggettoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DettaglioSoggettoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DettaglioSoggettoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
