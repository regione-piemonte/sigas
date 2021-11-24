export class RicevutaTelematicaVO {
    constructor(
        public prestatoreServizio:string,
        public identificativoPagamento:string,
        public identificativoTransazione:string,
        public iuv:string,
        public dataPagamento:string,
        public esitoPagamento:string
    ) { }
}