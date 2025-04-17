export class ReportUTFSoggettiMacroVO{

    constructor(
        public id_anag: Number,
	    public denominazione: String,	
        public tot_uso_industriale: Number,
        public tot_uso_civile : Number,
        public tot_nuovi_allacciamenti : Number,
        public tot_dichiarazione : Number,
        public azione : String, 
        public selectedImport: Boolean,       
    ) { }

}