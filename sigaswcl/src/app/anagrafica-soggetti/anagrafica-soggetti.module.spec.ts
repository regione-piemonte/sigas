import { AnagraficaSoggettiModule } from './anagrafica-soggetti.module';

describe('HomeModule', () => {
  let anagraficaSoggettiModule: AnagraficaSoggettiModule;

  beforeEach(() => {
    anagraficaSoggettiModule = new AnagraficaSoggettiModule();
  });

  it('should create an instance', () => {
    expect(anagraficaSoggettiModule).toBeTruthy();
  });
});
