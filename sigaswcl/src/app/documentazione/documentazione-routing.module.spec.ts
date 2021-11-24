import { DocumentazioneRoutingModule } from './documentazione-routing.module';

describe('DocumentazioneRoutingModule', () => {
  let documentazioneRoutingModule: DocumentazioneRoutingModule;

  beforeEach(() => {
      documentazioneRoutingModule = new DocumentazioneRoutingModule();
  });

  it('should create an instance', () => {
    expect(documentazioneRoutingModule).toBeTruthy();
  });
});
