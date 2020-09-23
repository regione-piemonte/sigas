import { AllarmiVO } from "../vo/allarmi-vo";
import { DichiaranteVO } from "../vo/dichiarante-vo";

export class RicercaConsumiRequest {
    constructor(
        public anno: string,
        public validato: string
    ) {

    }
}