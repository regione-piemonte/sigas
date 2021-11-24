export class PaymentRTInfoVO {

    constructor(
        public idAnag: number,
        public paymentCode: string,
        public transactionCode: string,
        public iuv: string,
        public idOriginPay: string,
        public months: string,
        public year: string,
        public payDate: string,
        public subjectName: string,
        public receivingEntity: string,
        public vatCode: string,
        public taxCode: string,
        public totalAmount: string,
        public entityCode: string = '80087670016'
    ) { }
}