export class SalvaCarrelloResponse {
    constructor(
        public redirect: boolean,
        public urlRedirect: string,
        public codiceIuv: string
    ) { }
}