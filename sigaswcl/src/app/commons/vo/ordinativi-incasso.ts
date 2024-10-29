import { PagamentiVersamentiVO } from './pagamenti-versamenti-vo';

export class OrdinativiIncassoVO {

    constructor(
        public idPagamento:number,
        public annoAttoAmministrativo: string,
        public annoOrdInc: string,
        public cfSoggetto: string,
        public codCapitolo: number,
        public codSoggetto:number,
        public conciliato: boolean,
        public dataElaborazione: Date,
        public dataEmissione:Date,
        public dataFirma: Date,
        public descCapitolo: string,
        public descDistinta: string,
        public descOrdInc: string,
        public descPdcFinanziarioIv: string,
        public descPdcFinanziarioV: string,
        public descSoggetto: string,
        public descStatoOrdInc: string,
        public descTipoAttoAmministrativo: string,
        public dichiarante: string,
        public firma: string,
        public importoAttuale: number,
        public importoIniziale: number,
        public noteAttoAmministrativo: string,
        public numAttoAmministrativo: number,
        public numOrdInc: number,
        public oggettoAttoAmministrativo: string,
        public pIvaSoggetto: string,
        public soggettoId:number,
        public sigasPagamentiVersamentis: Array<PagamentiVersamentiVO>
    ) { }
}