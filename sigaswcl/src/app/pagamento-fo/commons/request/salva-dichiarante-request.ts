import { DichiaranteVO } from "../vo/dichiarante-vo";


export class SalvaDichiaranteRequest {

    constructor(

        public anno?: number,
        public dataCartaceo?: string,
        public dichiaranteVO?: DichiaranteVO,
        public confermaUtente?: string

    ) { }
}