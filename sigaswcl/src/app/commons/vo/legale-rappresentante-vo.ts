export class LegaleRappresentanteVO {

    constructor(
        public cognome: string,
        public nome: string,
        public codiceFiscale: string,
        public telefono: string,
        public email: string,
        public dataDiNascita: string,
        public idProvincia: number,
        public idStatoEstero: number,
        public idComune: number,
        public cittaEstera: string,
    ) { }
}