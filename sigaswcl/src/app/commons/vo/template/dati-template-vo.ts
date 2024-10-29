import { AnagraficaSoggettoVO } from '../soggetti-vo';

export class DatiTemplateVO {

    //intestazione
    public numeroProtocollo: string;
    public classificazione: string;

    public mailSettoreTributi: string;

    public nome: string;


    public anagraficaSoggettoVO: AnagraficaSoggettoVO;

    public nomeDirigente: string;
    public cognomeDirigente: string;

    public dataProtocollazione: Date;
}