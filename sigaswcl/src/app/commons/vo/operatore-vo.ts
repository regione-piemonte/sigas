export class OperatoreVO {

    constructor(
        public cognome: string,
        public nome: string,
        public codiceFiscale: string,
        public telefono: string,
        public email: string,
        public dataDiNascita: string,
        public idProvincia: number,
        public idComune: number,
        public idStatoEstero: number,
        public cittaEstera: string,
        public servizi: Array<number>
    ) { }
}