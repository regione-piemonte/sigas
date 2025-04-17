import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {ConfigService} from '../../core/services/config.service';
import {LoggerService} from '../../core/services/logger.service';
import {ConfermaAliquotaRequest} from '../../commons/request/conferma-aliquota-request';
import {RicercaDocumentazioneRequestBo} from '../../commons/request/ricerca-documentazione-request-bo';
import {AnagraficaSoggettoVO} from '../../commons/vo/soggetti-vo';
import {TipoDocumentoVO} from '../../commons/vo/tipo-documento-vo';
import {DocumentiVO} from '../../commons/vo/documenti-vo';
import {ConfermaDocumentazioneRequest} from '../../commons/request/conferma-documentazione-request';
import {StatoDocumentoVO} from '../../commons/vo/stato-documento-vo';
import {IsCreatedVO} from '../../commons/vo/isCreated-vo';
import {Observable} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class DocumentazioneService {

    private msgProtocollazioneLetteraRisp: string;
    private msgErrProtocollazioneLetteraRisp: string;
///RICERCA DOCUMENTAZIONE
    private ricercaDocumentazioneRequestBo: RicercaDocumentazioneRequestBo;
    private confermaDocumentazioneRequest: ConfermaDocumentazioneRequest;
    private documentazioneSelect: DocumentiVO;

    //evenutuale nome file lettera risposta
    private nomeFileLetteraRisposta: string;

    constructor(
        private http: HttpClient,
        private config: ConfigService,
        private logger: LoggerService,
    ) {
        logger.info('DocumentazioneService constructor');
    }

    public get messaggioProtocollazioneLetteraRisp(): string {
        return this.msgProtocollazioneLetteraRisp;
    }

    public set messaggioProtocollazioneLetteraRisp(msgProtocollazioneLetteraRisp: string) {
        this.msgProtocollazioneLetteraRisp = msgProtocollazioneLetteraRisp;
    }

    public get messaggioErrProtocollazioneLetteraRisp(): string {
        return this.msgErrProtocollazioneLetteraRisp;
    }

    public set messaggioErrProtocollazioneLetteraRisp(msgErrProtocollazioneLetteraRisp: string) {
        this.msgErrProtocollazioneLetteraRisp = msgErrProtocollazioneLetteraRisp;
    }

    public get ricercaDocumentazione(): RicercaDocumentazioneRequestBo {
        return this.ricercaDocumentazioneRequestBo;
    }

    public set ricercaDocumentazione(ricercaDocumentazioneRequest: RicercaDocumentazioneRequestBo) {
        this.ricercaDocumentazioneRequestBo = ricercaDocumentazioneRequest;
    }

    public get confermaDocumentazione(): ConfermaDocumentazioneRequest {
        return this.confermaDocumentazioneRequest;
    }

    public set confermaDocumentazione(confermaDocumentazioneRequest: ConfermaDocumentazioneRequest) {
        this.confermaDocumentazioneRequest = confermaDocumentazioneRequest;
    }

    public get documentazioneSel(): DocumentiVO {
        return this.documentazioneSelect;
    }

    public set documentazioneSel(documentazioneSelect: DocumentiVO) {
        this.documentazioneSelect = documentazioneSelect;
    }

    public set nomeFileLetteraDiRisposta(nomeFileLetteraRisposta: string) {
        this.nomeFileLetteraRisposta = nomeFileLetteraRisposta;
    }

    public get nomeFileLetteraDiRisposta(): string {
        return this.nomeFileLetteraRisposta;
    }
    public ricercaAziendeDocumentiInoltrati() {
        var url: string = this.config.getBEServer() + '/rest/documentazione/ricercaAziendeDocumentiInoltrati';
        return this.http.get<Array<AnagraficaSoggettoVO>>(url);
    }


    public listaStatoDocumenti() {
        var url: string = this.config.getBEServer() + '/rest/documentazione/listaStatoDocumenti';
        return this.http.get<Array<StatoDocumentoVO>>(url);
    }

    ricercaDocumentiBO() {
        var url: string = this.config.getBEServer() + '/rest/documentazione/ricercaDocumentiBO';
        return this.http.post<Array<DocumentiVO>>(url, this.ricercaDocumentazioneRequestBo);
    }

    salvaDocumentazioneBO(notaDataInvioPec: string, 
                          annoAccertamento: number, numeroAccertamento: string, 
                          depCausionaleNumeroDetermina: string,
                          importoDepositoCausionale: number) 
    {
        var url: string = this.config.getBEServer() + '/rest/documentazione/salvaDocumentazioneBO';

        console.log(url);
        let formData: FormData = new FormData();

        formData.append('idDocumento', this.confermaDocumentazioneRequest.documentiVO.idDocumento.toString());
        formData.append('idStatoSelezionato', this.confermaDocumentazioneRequest.documentiVO.statoDocumentoVO.idStatoDocumento.toString());
        if (this.confermaDocumentazioneRequest.documentiVO.noteBo != null) {
            formData.append('noteBo', this.confermaDocumentazioneRequest.documentiVO.noteBo.toString());
        }

        if (notaDataInvioPec != null) {
            formData.append('notaDataInvioPec', notaDataInvioPec);
        }

        if(annoAccertamento != null && annoAccertamento != undefined) {
            formData.append('annoAccertamento', annoAccertamento.toString());
        }
        
        if(numeroAccertamento != null && numeroAccertamento != undefined) {
            formData.append('numeroAccertamento', numeroAccertamento);
        }

        if(depCausionaleNumeroDetermina != null && depCausionaleNumeroDetermina != undefined) {
            formData.append('numeroDetermina', depCausionaleNumeroDetermina);
        }

        if(importoDepositoCausionale != null && importoDepositoCausionale != undefined) {            
            formData.append('importo', importoDepositoCausionale.toString());
        }        

        return this.http.post<string>(url, formData);
    }


    protocollaLetteraRisp(fileLetteraRisp: File) {
        var url: string = this.config.getBEServer() + '/rest/documentazione/protocollaLetteraRisp';

        console.log(url);
        let formData: FormData = new FormData();
        if (null != fileLetteraRisp) {
            formData.append('dataFileRisposta', fileLetteraRisp, fileLetteraRisp.name);
            console.log('dataFileRisposta', fileLetteraRisp.name);
            formData.append('fileName', fileLetteraRisp.name);
        }


        if (null != fileLetteraRisp) {
            formData.append('nomeFileLetteraRisposta', fileLetteraRisp.name);
        }


        formData.append('idDocumento', this.documentazioneSelect.idDocumento.toString());


        return this.http.post<DocumentiVO>(url, formData);
    }


    public getUrlScaricaDocumentoPrincipale(): String {
        return this.config.getBEServer() + '/rest/documentazione/stampaDocumentoPrincipale/';
    }

    //Servizio da creare lato BE
    isLetteraSaved(request: IsCreatedVO): Observable<IsCreatedVO> {
        var url: string = this.config.getBEServer() + '/rest/ordinanza/isLetteraSaved';
        return this.http.post<IsCreatedVO>(url, request);
    }

    public getUrlScaricaDocumentoAllegato(idDocumento: string, fileName: string): String {
        return this.config.getBEServer() + '/rest/documentazione/getAllegato/' + idDocumento + '/' + fileName;
    }

    public getUrlScaricaPacchetto(): String {
        return this.config.getBEServer() + '/rest/documentazione/getPacchettoDocumenti/';
    }


    public getUrlScaricaDocumentoMaster(): String {
        return this.config.getBEServer() + '/rest/documentazione/getDocumentoMaster/';
    }


    ricercaLetteraRisposta(rifArchivio: string) {
        var url: string = this.config.getBEServer() + '/rest/documentazione/ricercaLetteraRisposta';
        //const header = new HttpHeaders({ 'Content-Type': 'application/json; charset=utf-8' });
        let params = new HttpParams().set('rifArchivio', rifArchivio);
        return this.http.get<Array<DocumentiVO>>(url, {params: params});
    }

    public generaProtocollaBollettinoPagamentoDepCausionale(determina: File, 
                                                            idDocumentoRichiestaDepCausionale: number, importo: number, 
                                                            annoAccertamento: number, numeroAccertamento: string, 
                                                            depCausionaleNumeroDetermina: string,
                                                            codiceTipoDocumento: string) 
    {
        var url: string = this.config.getBEServer() + '/rest/deposito-causionale/bollettino-pagamento';

        console.log(url);
        let formData: FormData = new FormData();
        if (null != determina) {
            formData.append('dataFileRisposta', determina, determina.name);            
            formData.append('fileName', determina.name);
        }

        if (null != determina) {
            formData.append('nomeFileLetteraRisposta', determina.name);
        }

        formData.append('idDocumento', idDocumentoRichiestaDepCausionale.toString());
        formData.append('importo', importo.toString());
        formData.append('annoAccertamento', annoAccertamento.toString());
        formData.append('numeroAccertamento', numeroAccertamento);
        formData.append('numeroDetermina', depCausionaleNumeroDetermina);
        formData.append('codiceTipoDocumento', codiceTipoDocumento);

        return this.http.post<String>(url, formData);
    }


}
