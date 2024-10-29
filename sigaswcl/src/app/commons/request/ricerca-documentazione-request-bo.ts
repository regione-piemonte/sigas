import { AnagraficaSoggettoVO } from '../vo/soggetti-vo';
import { TipoDocumentoVO } from '../../commons/vo/tipo-documento-vo'; 

export class RicercaDocumentazioneRequestBo {
    constructor(
            public anagraficaSoggettoVO: AnagraficaSoggettoVO,
            public idStatoDocumento: Number,
            public dataProtocolloDal:Date,
            public dataProtocolloAl:Date
    ) {

    }
}
