import { ImpiantoVO } from "../vo/impianto-vo";


export class ImpiantoDT {
    constructor(
        public checked: boolean,
        public deleted: boolean,
        public impianto: ImpiantoVO
    ) { }
}