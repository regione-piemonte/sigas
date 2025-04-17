import { AnagraficaSoggettoVO } from "../../commons/vo/soggetti-vo";

export class ConfermaSoggettoRequest {

    constructor(
        public soggetto: AnagraficaSoggettoVO,
        public annualita: String,
        public ownerOperazione: String
    )
    { }
}