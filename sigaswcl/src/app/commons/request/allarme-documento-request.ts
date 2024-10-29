export class AllarmeDocumentoRequest {

    constructor(
        public idAnag: number,
        public idComunicazione: number,
        public tipoAllarme: string,
        public allarmeOn: boolean,
        public nota: string
    ) { }
}
