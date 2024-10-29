import { TestBed, inject } from '@angular/core/testing';

import { AccreditamentoService } from './accreditamento.service';

describe('AccreditamentoService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AccreditamentoService]
    });
  });

  it('should be created', inject([AccreditamentoService], (service: AccreditamentoService) => {
    expect(service).toBeTruthy();
  }));
});
