export class PaymentRedirectVO {

    constructor(
        public url:  string = null,
        public codiceChiamante:  string = null,
        public digest:  string = null,
        public iuv:  string = null,
        public codiceFiscale:  string = null,
        public identificativoPagamento:  string = null,
        public waitingUserMessage:  string = null
    ) { }
}