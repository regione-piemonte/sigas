import { TestBed, inject } from '@angular/core/testing';

import { ImportazioneUTFService } from './importazione-utf.service';

describe('ImportazioneUTFService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ImportazioneUTFService]
    });
  });

  it('should be created', inject([ImportazioneUTFService], (service: ImportazioneUTFService) => {
    expect(service).toBeTruthy();
  }));
});
