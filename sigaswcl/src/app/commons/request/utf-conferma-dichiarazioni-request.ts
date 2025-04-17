export class UTFConfermaDichiarazioniRequest {

    constructor(
        public idImportA?: number,
        public idImportB?: number,
        public annualita?: string,        
        public azioneRichiesta?:string,
    ) { }
}