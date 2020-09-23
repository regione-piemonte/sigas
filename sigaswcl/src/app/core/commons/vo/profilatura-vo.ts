
export class ProfilaturaVO {

    constructor(
        public useCase: Array<String>,
        public codiceFiscaleUtente: string,
        public nome: string,
        public cognome: string,
        //OPERATORE
        public flagPrimoAccesso: boolean,
        public importazioneUTF: boolean,
        public aliquote: boolean,
        public tassiInteresse: boolean
       
    ) {

    }
}