export class OperatoreVO {

    constructor(
        public cognome: string,
        public nome: string,
        public codiceFiscale: string,
        public telefono: string,
        public email: string,
        public servizi: Array<number>
    ) { }
}