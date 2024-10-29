import { TestBed, inject } from '@angular/core/testing';

import { TassiService } from './tassi.service';

describe('TassiService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TassiService]
    });
  });

  it('should be created', inject([TassiService], (service: TassiService) => {
    expect(service).toBeTruthy();
  }));
});
