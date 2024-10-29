import { DocumentazioneFoRoutingModule } from './documentazione-fo-routing.module';

describe('DocumentazioneFoRoutingModule', () => {
  let documentazioneFoRoutingModule: DocumentazioneFoRoutingModule;

  beforeEach(() => {
      documentazioneFoRoutingModule = new DocumentazioneFoRoutingModule();
  });

  it('should create an instance', () => {
    expect(documentazioneFoRoutingModule).toBeTruthy();
  });
});
