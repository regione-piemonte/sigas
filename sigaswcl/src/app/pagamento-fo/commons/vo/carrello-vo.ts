import { ModalitaPagamentoVO } from "./modalita-pagamento-vo";
import { TipoPagamentoVO } from "./tipo-pagamento-vo";
import { StatoCarrelloVO } from "./stato-carrello-vo";
import { TipoVersanteVO } from "./tipo-versante-vo";
import { SituazionePagamentoVO } from "./situazione-pagamento-vo";
import { MeseVO } from "./mese-vo";

export class CarrelloVO {

    constructor(

        public id: number,

        public codicePagamento: string,
        public emailOperatore: string,

        public modalitaPagamento: ModalitaPagamentoVO,

        public dataPagamento: string,
        public dataVersamento: string,

        public tipoPagamento: TipoPagamentoVO,

        public statoCarrello: StatoCarrelloVO,

        public imposta: number,
        public sanzioni: number,
        public interessi: number,
        public totale: number,

        public tipoVersante: TipoVersanteVO,
        public identificativoVersante: String,
        public denominazioneVersante: String,
        public pagamentoRiconciliato: Boolean,
        public situazionePagamento: SituazionePagamentoVO,
        public importoPagatoRiconciliato: number,
        public dataValutaRiconciliata: string,
        public dataQuietanza: string,
        public impostaRiconciliata: number,
        public sanzioneRiconciliata: number,
        public interesseRiconciliato: number,
        public totaleRiconciliato: number,

        public acconti: number,
        //solo per il riepilogo pagamento
        public mese: MeseVO
    ) { }
}