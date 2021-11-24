import { RicercaDichiaranteRequest } from "./ricerca-dichiarante-request";


export class InviaDichiarazioniRequest {
    constructor(
        public anno?: number,
        public ricercaDichiaranteRequest?: RicercaDichiaranteRequest
    ) { }
}