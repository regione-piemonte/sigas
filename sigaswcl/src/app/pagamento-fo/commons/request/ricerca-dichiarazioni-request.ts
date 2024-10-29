export class RicercaDichiarazioniRequest {
    constructor(
        public piva?: string,
        public anno?: number,
        public cfDichiarante?: string
    ) { }
}