import { ImportazioneUTFModule } from './importazione-utf.module';

describe('ImportazioneUTFModule', () => {
  let importazioneUTFModule: ImportazioneUTFModule;

  beforeEach(() => {
    importazioneUTFModule = new ImportazioneUTFModule();
  });

  it('should create an instance', () => {
    expect(importazioneUTFModule).toBeTruthy();
  });
});
