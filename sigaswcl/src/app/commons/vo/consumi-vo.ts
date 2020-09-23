import { AllarmiVO } from "../vo/allarmi-vo";

export class ConsumiVO {

    constructor(
        public idAnag: number,
        public codiceAzienda: string,
        public denominazione: string,
        public nProvince: number,
        public totVersato: number,
        public totCalcolato: number,
        public validato: string,
        public allarmi: AllarmiVO
    ) { }
}