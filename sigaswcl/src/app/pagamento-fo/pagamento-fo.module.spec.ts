import { PagamentoFoModule } from './pagamento-fo.module';

describe('HomeModule', () => {
  let pagamentoFoModule: PagamentoFoModule;

  beforeEach(() => {
    pagamentoFoModule = new PagamentoFoModule();
  });

  it('should create an instance', () => {
    expect(pagamentoFoModule).toBeTruthy();
  });
});
