import { ImpiantoVO } from "../vo/impianto-vo";

export class SalvaImpiantoRequest {
    constructor(public id: number, public impianto: ImpiantoVO) {

    }
}