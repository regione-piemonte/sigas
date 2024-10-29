
export class TassiVO {

    public numGiorni: number;
    public totInteresse: number;
    
    constructor(
        public id : number,
        public idTipoTasso : number,
        public valore: number,
        public dataStart: Date,
        public dataEnd: Date,
        public version: number
    ) { }
}