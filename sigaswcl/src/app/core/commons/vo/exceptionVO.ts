export class ExceptionVO {
    constructor(
        public status: string,
        public message: string,
        public errorCode: string
    ) { }
}