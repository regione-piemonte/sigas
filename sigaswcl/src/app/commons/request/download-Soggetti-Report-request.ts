import { ConsumiVO } from '../../commons/vo/consumi-vo';

export class DownloadSoggettiReport {

    constructor(
        public soggetti : Array<ConsumiVO>,
        public anno : string
    ) { }
    
}