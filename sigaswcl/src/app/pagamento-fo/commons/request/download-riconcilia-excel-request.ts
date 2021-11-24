import { RicercaPagamentiDaRiconciliareRequest } from "./ricerca-pagamenti-da-riconciliare-request";
import { MeseVO } from "../vo/mese-vo";


export class DownloadPagamentiDaRiconciliareRequest extends RicercaPagamentiDaRiconciliareRequest {

    public idCarrello: number;


    constructor(
        idCarrello: number,
        codicePagamento?: string,
        anno?: number,
        mese?: MeseVO,
        cfDichiarante?: string,
        piva?: string,
        flagRiconciliato?: boolean
    ) {
        super(codicePagamento, anno, mese, cfDichiarante, piva, flagRiconciliato);
        this.idCarrello = idCarrello;
    }



}
