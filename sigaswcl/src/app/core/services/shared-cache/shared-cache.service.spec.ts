import { TestBed, inject } from '@angular/core/testing';

import { SharedCacheService } from './shared-cache.service';

describe('SharedCacheService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SharedCacheService]
    });
  });

  it('should be created', inject([SharedCacheService], (service: SharedCacheService) => {
    expect(service).toBeTruthy();
  }));
});
