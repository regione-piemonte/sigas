import { MeseVO } from "./mese-vo";

export class PagamentoImpiantoVO {
    constructor(
        public index: number,
        public id: number,
        public mese: MeseVO,
        public lErogati: number,
        public aliquota: number,
        public impostaDovuta: number,
        public accontiVersati: number,
        public dataVersamento: string,
        public conguaglio: number,
        public flgIsAcconto?: boolean) { }
}