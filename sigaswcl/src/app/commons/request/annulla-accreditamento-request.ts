export class AnnullaAccreditamentoRequest {
    constructor(
            public idUtenteProvv: number,
            public stato: string,
            public note: string
    ) {

    }
}
