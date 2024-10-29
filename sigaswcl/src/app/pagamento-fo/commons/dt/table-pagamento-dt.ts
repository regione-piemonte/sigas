import { TablePagamentoVO } from "../vo/table-pagamento-vo";

export class TablePagamentoDT {

    constructor(
        public indice: number,
        public deleted: boolean,
        public checked: boolean,
        public pagamento: TablePagamentoVO
    ) { }
}