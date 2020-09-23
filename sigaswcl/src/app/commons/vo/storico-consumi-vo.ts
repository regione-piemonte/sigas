export class StoricoConsumiVO {

    constructor(
        public modIdConsumi: number,
        public idConsumi: number,
        public modUser: string,
	    public modDate: Date,
	    public note: string
        
    ) { }
}