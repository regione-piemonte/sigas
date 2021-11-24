import { ReportVO } from "./report-vo";

export class ReportAnnoVO {

    constructor(
        public anno: number,
        public report: Array<ReportVO>
    ) { }
}