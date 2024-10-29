export class RicevutaPagamentoVO {
    constructor(
        public enteBeneficiario:string,
        public cfEnte:string,
        public importoPagato:number,
        public codiceAvviso:string,
        public iuv:string,
        public ragioneSociale:string,
        public cfRivenditore:string
    ) { }    
}