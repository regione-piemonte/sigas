export class TipoDocumentoVO {

    constructor(
        public idTipoDocumento: number,
        public codiceTipoDocumento: string,
        public descrizione: string,
        public idTipoDocumentoPadre: number
        ) { }
}
