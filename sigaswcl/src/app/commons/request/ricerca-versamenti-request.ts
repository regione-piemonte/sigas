
export class RicercaVersamentiRequest {
    constructor(
        public idAnag: number,    
        public anno: string,
        public mese: string,
        public provincia: number,
        public tipo: number
    ) {

    }
}