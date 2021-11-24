import { TestBed, inject } from '@angular/core/testing';

import { DocumentazioneService } from './documentazione.service';

describe('DocumentazioneService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DocumentazioneService]
    });
  });

  it('should be created', inject([DocumentazioneService], (service: DocumentazioneService) => {
    expect(service).toBeTruthy();
  }));
});
