import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ImportazioneUTFComponent } from './importazione-utf.component';

describe('ImportazioneUTFComponent', () => {
  let component: ImportazioneUTFComponent;
  let fixture: ComponentFixture<ImportazioneUTFComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ImportazioneUTFComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ImportazioneUTFComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
