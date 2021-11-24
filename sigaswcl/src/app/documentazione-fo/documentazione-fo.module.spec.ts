import { DocumentazioneFoModule } from './documentazione-fo.module';

describe('DocumentazioneFoModule', () => {
  let aliquote: DocumentazioneFoModule;

  beforeEach(() => {
    aliquote = new DocumentazioneFoModule();
  });

  it('should create an instance', () => {
    expect(aliquote).toBeTruthy();
  });
});
