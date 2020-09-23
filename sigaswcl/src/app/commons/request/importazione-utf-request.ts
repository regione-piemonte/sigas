import { AllarmiVO } from "../vo/allarmi-vo";
import { DichiaranteVO } from "../vo/dichiarante-vo";

export class ImportazioneUTFRequest {
    constructor(
        public utfFile: File,
        public annualita: string
    ) {

    }
}