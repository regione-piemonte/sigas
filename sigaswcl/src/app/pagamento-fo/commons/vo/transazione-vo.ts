import { CarrelloVO } from "./carrello-vo";

export class TransazioneVO {

    constructor(
        public codiceIuv?: string,
        public denominazione?: string,
        public carrello?: CarrelloVO,
        public tipoVersamento?: string,
        public statoPagamento?: string
    ) { }
}