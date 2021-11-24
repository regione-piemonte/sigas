export class ScartoVO {

    constructor(
        public idScarti: number,
        public idConsumi: number,
        public provincia: string,
        public consumi: number,
        public aliquota: number,
        public imposta: number,
        public conciliato: boolean,
        public descUsoScarto: string
    ) { }
}
