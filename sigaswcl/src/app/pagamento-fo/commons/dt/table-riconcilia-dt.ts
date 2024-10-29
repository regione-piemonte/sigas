import { TableRiconciliaVO } from "../vo/table-riconcilia-vo";

export class TableRiconciliaDT {

    constructor(
        public indice: number,
        public deleted: boolean,
        public checked: boolean,
        public pagamento: TableRiconciliaVO
    ) { }
}