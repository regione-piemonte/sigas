import { DocumentiVO } from '../vo/documenti-vo';

export class ConfermaDocumentazioneRequest {
    constructor(
            public documentiVO: DocumentiVO,
            public importo: Number,
            public codiceProvincia: String,
            public depCausionaleIndirizzo: String,
    ) {  }   
}
