import { AnagraficaSoggettoVO } from '../vo/soggetti-vo';
import { TipoDocumentoVO } from '../../commons/vo/tipo-documento-vo'; 

export class RicercaDocumentazioneRequest {
    constructor(
            public anagraficaSoggettoVO: AnagraficaSoggettoVO,
            public nprotocollo: string,
            public tipoDocumentoVO: TipoDocumentoVO,
            public annualita:Number
    ) {

    }
}
