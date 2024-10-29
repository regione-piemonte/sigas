import { TassiVO } from "../vo/tassi-vo";

export class ValidaSoggettoRequest {

    constructor(
            public idAnag : number,
            public anno: string,
            public validato: boolean
    ) { }
}