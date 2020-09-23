import { TassiRoutingModule } from './tassi-routing.module';

describe('TassiRoutingModule', () => {
  let tassiRoutingModule: TassiRoutingModule;

  beforeEach(() => {
    tassiRoutingModule = new TassiRoutingModule();
  });

  it('should create an instance', () => {
    expect(tassiRoutingModule).toBeTruthy();
  });
});
