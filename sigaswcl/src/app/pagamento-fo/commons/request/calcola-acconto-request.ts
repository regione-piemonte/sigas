import { AccontoVO } from "../vo/acconto-vo";
import { AccontoCommonsRequest } from "./acconto-commons-request";

export class CalcolaAccontoRequest extends AccontoCommonsRequest {

    public acconto: AccontoVO;

    constructor(
        idDichiarazione: number,
        idImpianto: number,
        idDichiarazioneImpianto: number,
        acconto: AccontoVO
    ) {
        super(idDichiarazione, idImpianto, idDichiarazioneImpianto);
        this.acconto = acconto;
    }
}