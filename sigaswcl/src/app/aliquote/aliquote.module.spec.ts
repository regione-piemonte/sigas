import { AliquoteModule } from './aliquote.module';

describe('AliquoteModule', () => {
  let aliquote: AliquoteModule;

  beforeEach(() => {
    aliquote = new AliquoteModule();
  });

  it('should create an instance', () => {
    expect(aliquote).toBeTruthy();
  });
});
