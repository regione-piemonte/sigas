import { TestBed, inject } from '@angular/core/testing';

import { AnagraficaSoggettiService } from './anagrafica-soggetti.service';

describe('AnagraficaSoggettiService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AnagraficaSoggettiService]
    });
  });

  it('should be created', inject([AnagraficaSoggettiService], (service: AnagraficaSoggettiService) => {
    expect(service).toBeTruthy();
  }));
});
