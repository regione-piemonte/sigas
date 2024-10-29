import { AccreditamentoRoutingModule } from './accreditamento-routing.module';

describe('AccreditamentoRoutingModule', () => {
  let accreditamentoRoutingModule: AccreditamentoRoutingModule;

  beforeEach(() => {
    accreditamentoRoutingModule = new AccreditamentoRoutingModule();
  });

  it('should create an instance', () => {
    expect(accreditamentoRoutingModule).toBeTruthy();
  });
});
