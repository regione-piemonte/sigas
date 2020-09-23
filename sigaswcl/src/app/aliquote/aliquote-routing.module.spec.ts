import { AliquoteRoutingModule } from './aliquote-routing.module';

describe('AliquoteRoutingModule', () => {
  let aliquoteRoutingModule: AliquoteRoutingModule;

  beforeEach(() => {
    aliquoteRoutingModule = new AliquoteRoutingModule();
  });

  it('should create an instance', () => {
    expect(aliquoteRoutingModule).toBeTruthy();
  });
});
