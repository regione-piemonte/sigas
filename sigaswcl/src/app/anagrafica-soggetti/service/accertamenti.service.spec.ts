import { TestBed, inject } from '@angular/core/testing';

import { AccertamentiService } from './accertamenti.service';

describe('AccertamentiService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AccertamentiService]
    });
  });

  it('should be created', inject([AccertamentiService], (service: AccertamentiService) => {
    expect(service).toBeTruthy();
  }));
});
