import {RicercaDichiarazioniConsultaRequest} from "./ricerca-dichiarazoini-consulta-request" ;
import { StatoDichiarazioneVO } from "../vo/stato-dichiarazione-vo";


export class DownloadDichiarazionePARequest extends RicercaDichiarazioniConsultaRequest{

    public idDichiarazione: number;

    constructor(
        idDichiarazione: number,
        anno: number,
        stato: StatoDichiarazioneVO,
        flagRettifica: boolean,
        cfDichiarante: string,
        piva: string
    ) {
        super(anno, stato, flagRettifica, cfDichiarante, piva, null, null);
        this.idDichiarazione = idDichiarazione;
    }

 
}