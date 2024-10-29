export class AllarmiSoggettoVO {

    constructor(
        public idAllarme: number,
        public idConsumi: number,
        public idComunicazioni: number,
        public idTipoAllarme: number,
        public codiceAzienda: string,
        public nota: string,
        public status: number,
        public attivazione: string 
       
    ) { }
}