export class RigeneraBollettinoPagamentoRequestVO {
    constructor(
        public idDocumento ?: number,	
	    public idDepositoCauzionale ?: number,	
	    public codicePagamento ?: String,
    ) {
    }
}