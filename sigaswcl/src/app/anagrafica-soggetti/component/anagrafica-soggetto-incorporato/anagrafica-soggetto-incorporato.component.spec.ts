import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { AnagraficaSoggettoIncorporatoComponent } from './anagrafica-soggetto-incorporato.component';

describe('AnagraficaSoggettoComponent', () => {
  let component: AnagraficaSoggettoIncorporatoComponent;
  let fixture: ComponentFixture<AnagraficaSoggettoIncorporatoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnagraficaSoggettoIncorporatoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnagraficaSoggettoIncorporatoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
