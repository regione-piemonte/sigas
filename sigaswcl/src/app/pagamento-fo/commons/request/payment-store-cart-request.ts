
export class PaymentStoreCartRequest {
    constructor(
        public id: number = 0,
        public amount: string = '',
        public year: string = '',
        public area: string = '',
        public subjectName: string = '',
        public idAnag: string = '',
        public subjectCode: string = '',
        public status: string = null,
        public paymentCode: string = "" + Date.now(),
        public paymentType: string = '',
        public currentDate: string = new Date().getDate() + "/" + new Date().getMonth() + '/' + new Date().getFullYear(),
        public payDate: string = '',
        public email: string = '',
        public month: string = '',
        public type: string = '',
        public cartOption: number = 0,
        public codiceFiscalePIva: string = '',
        public iuv: string = ''
    ) { }

    public get months(): { [key: number]: string } {
        return {
            1: 'Gennaio', 
            2: 'Febbraio', 
            3: 'Marzo', 
            4: 'Aprile', 
            5: 'Maggio', 
            6: 'Giugno', 
            7: 'Luglio', 
            8: 'Agosto', 
            9: 'Settembre', 
            10: 'Ottobre', 
            11: 'Novembre', 
            12: 'Dicembre'
          };
    }

    public monthString(): string {
        if(!this.month) return '';

        return this.months[this.month];
    }

    public get types(): { [key: number]: string } {
        return {
            1: 'Rateo', 
            2: 'Conguaglio', 
            3: 'Ravvedimento'
          };
    }

    public typeString(): string {
        if(!this.type) return '';

        return this.types[this.type];
    }

    public get cartKey(): string {
        //return this.year+this.area+this.subjectName+this.month+this.paymentCode+this.id+this.paymentType;
        return this.year+this.area+this.subjectName+this.month+this.paymentCode+this.id+this.paymentType;
    }
    
 
}