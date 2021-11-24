
export class SearchSubjectPaymentFoRequest {
    constructor(
        public year: string = null,
        public subjectName: string = null,
        public area: string = null,
        public subjectCode: string = null,
        public subjectId: string = null,
        public dateFrom: any = null,
        public dateTo: any = null,
        public monthFrom: any = null,
        public monthTo: any = null,
        public operatorFO: string = null,
        public vatCode: string = null,
        public payType: string = null,
        public iuv: string = null,
        public id: string = null,
        public codiceFiscalePIva: string = null
    ) {

    }
}