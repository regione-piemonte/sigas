import { DichiarazioneVO } from "../vo/dichiarazione-vo";

export class DichiarazioneDT {

    constructor(
        public checked: boolean,
        public alreadyValidated: boolean,        
        public dichiarazione: DichiarazioneVO,
        public toValidate?: boolean,
    ) { }
}