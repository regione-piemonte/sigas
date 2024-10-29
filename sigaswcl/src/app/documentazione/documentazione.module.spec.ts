import { DocumentazioneModule } from './documentazione.module';

describe('DocumentazioneModule', () => {
  let aliquote: DocumentazioneModule;

  beforeEach(() => {
    aliquote = new DocumentazioneModule();
  });

  it('should create an instance', () => {
    expect(aliquote).toBeTruthy();
  });
});
