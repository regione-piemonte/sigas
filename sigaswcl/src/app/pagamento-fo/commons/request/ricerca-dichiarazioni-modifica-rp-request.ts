import { RicercaDichiaranteRequest } from "./ricerca-dichiarante-request";


export class RicercaDichiarazioniModificaRpRequest {
    constructor(
        public anno?: number,
        public idProvincia?: number,
        public ricercaDichiaranteRequest?: RicercaDichiaranteRequest
    ) { }
}