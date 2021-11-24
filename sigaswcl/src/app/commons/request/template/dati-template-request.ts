import { DatiTemplateCompilatiVO } from "../../vo/template/dati-template-compilati-vo";

export class DatiTemplateRequest {
    public codiceTemplate: number;

    public idDocumentazione: number;

    public nProtocollo: string;

    public datiTemplateCompilatiVO: DatiTemplateCompilatiVO;
}