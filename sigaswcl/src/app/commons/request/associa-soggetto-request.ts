import { AnagraficaSoggettoVO } from "../vo/soggetti-vo";

export class AssociaSoggettoRequest {

    constructor(public soggettoNew: AnagraficaSoggettoVO, public soggettoSelezionato: AnagraficaSoggettoVO)
    { }
}