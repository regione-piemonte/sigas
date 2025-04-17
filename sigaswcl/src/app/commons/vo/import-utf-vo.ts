
export class ImportUTFVO {

    constructor(
        public importId: number,
        public filename: string,
        public anno: string,
        public numeroFile: number,
        public selectedImport: boolean,
        public dataRiferimento: Date,
        public esito: number
    ) {  }
}