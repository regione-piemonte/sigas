export class TableRiconciliaVO {

    constructor(
        public idCarrello: number,
        public codicePagamento: number,
        public dichiarante: string,
        public totaleAcconto: number,
        public totaleLitri: number,
        public dataVersamento: string,
    ) { }
}