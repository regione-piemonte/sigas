import { TassiModule } from './tassi.module';

describe('TassiModule', () => {
  let tassiModule: TassiModule;

  beforeEach(() => {
    tassiModule = new TassiModule();
  });

  it('should create an instance', () => {
    expect(tassiModule).toBeTruthy();
  });
});
