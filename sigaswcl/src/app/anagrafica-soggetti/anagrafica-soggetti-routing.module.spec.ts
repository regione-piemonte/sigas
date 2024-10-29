import { AnagraficaSoggettiRoutingModule } from './anagrafica-soggetti-routing.module';

describe('AnagraficaSoggettiRoutingModule', () => {
  let anagraficaSoggettiRoutingModule: AnagraficaSoggettiRoutingModule;

  beforeEach(() => {
    anagraficaSoggettiRoutingModule = new AnagraficaSoggettiRoutingModule();
  });

  it('should create an instance', () => {
    expect(anagraficaSoggettiRoutingModule).toBeTruthy();
  });
});
