
export class RiepilogoPagamentoRequest {

    constructor(
        public idDichiarazione: number,
        public idImpianto: number,
        public idDichiarazioneImpianto: number
    ) { }
}