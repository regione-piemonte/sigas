import { ConsumiPrVO } from '../vo/consumi-pr-vo';
import { ScartoVO } from '../vo/scarto-vo';

export class ConfermaConsumiRequest {

    constructor(public consumi: ConsumiPrVO, public scarti:Array<ScartoVO>)
    { }
}
