import { AccreditamentoModule } from './accreditamento.module';

describe('AccreditamentoModule', () => {
  let aliquote: AccreditamentoModule;

  beforeEach(() => {
    aliquote = new AccreditamentoModule();
  });

  it('should create an instance', () => {
    expect(aliquote).toBeTruthy();
  });
});
