import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AliquoteComponent } from './aliquote.component';

describe('ImportazioneUTFComponent', () => {
  let component: AliquoteComponent;
  let fixture: ComponentFixture<AliquoteComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AliquoteComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AliquoteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
