import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ElencoDocumentazioneComponent } from './elenco-documentazione.component';

describe('ElencoDocumentazioneComponent', () => {
  let component: ElencoDocumentazioneComponent;
  let fixture: ComponentFixture<ElencoDocumentazioneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ElencoDocumentazioneComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ElencoDocumentazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
