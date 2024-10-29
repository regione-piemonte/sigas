import { Injectable } from "@angular/core";
import { ReplaySubject, Subject } from "rxjs";
import { Observable } from "rxjs/Observable";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { ConfigService } from "../../../core/services/config.service";
import { LoggerService } from '../../../core/services/logger.service';
import { ServizioVO } from "../../../core/commons/vo/servizio-VO";
import { DichiaranteVO } from "../../../commons/vo/dichiarante-accreditamento-vo";
import { UtenteProvvisorioVO } from "../../../commons/vo/utente-provvisorio-vo";
import { RicercaDichiaranteRequest } from "../../../commons/request/ricerca-dichiarante-request";
import { LegaleRappresentanteVO } from "../../accreditamento-shared/commons/vo/legale-rappresentante-vo";
import { OperatoreVO } from "../../accreditamento-shared/commons/vo/operatore-vo";
import { ConfermaAccreditamentoRequest } from "../../accreditamento-shared/commons/request/conferma-accreditamento-request";
import { AnagraficaSoggettoVO } from '../../../commons/vo/soggetti-vo';
import { VerificaDichiaranteRequest } from "../../../commons/request/verifica-dichiarante-request";
import { AccreditamentoVO } from '../../../commons/vo/accreditamento-vo';
import { RicercaAccreditamentoRequest } from '../../../commons/request/ricerca-accreditamento-request';

@Injectable()
export class InserisciAccreditamentoService {

    ///RICERCA DICHIARANTE
    private ricercaDichiaranteRequest: RicercaDichiaranteRequest;

    public set RicercaDichiarante(ricercaDichiaranteRequest: RicercaDichiaranteRequest) {
        this.ricercaDichiaranteRequest = ricercaDichiaranteRequest;
    }
    public get RicercaDichiarante(): RicercaDichiaranteRequest {
        return this.ricercaDichiaranteRequest;
    }
    
  ///VERIFICA DICHIARANTE
    private verificaDichiaranteRequest: VerificaDichiaranteRequest;

    public set VerificaDichiarante(verificaDichiaranteRequest: VerificaDichiaranteRequest) {
        this.verificaDichiaranteRequest = verificaDichiaranteRequest;
    }
    public get VerificaDichiarante(): VerificaDichiaranteRequest {
        return this.verificaDichiaranteRequest;
    }
    
    ///RICERCA ACCREDITAMENTO
    private ricercaAccreditamentoRequest: RicercaAccreditamentoRequest;

    public set ricercaAccreditamento(ricercaAccreditamentoRequest: RicercaAccreditamentoRequest) {
        this.ricercaAccreditamentoRequest = ricercaAccreditamentoRequest;
    }
    
    public get ricercaAccreditamento(): RicercaAccreditamentoRequest {
        return this.ricercaAccreditamentoRequest;
    }

    ricercaDichiarante() {
        var url: string = this.config.getBEServer() + '/rest/accreditamento/ricercaDichiarante';
        //const header = new HttpHeaders({ 'Content-Type': 'application/json; charset=utf-8' });
        return this.http.post<Array<AnagraficaSoggettoVO>>(url, this.ricercaDichiaranteRequest);
    }
    
    verificaDichiarante( codiceAzienda: string, codiceFiscaleOperatore: string) {
        var url: string = this.config.getBEServer() + '/rest/accreditamento/verificaDichiarante';
        //const header = new HttpHeaders({ 'Content-Type': 'application/json; charset=utf-8' });
    let params = new HttpParams().set('codiceAzienda', codiceAzienda).set('codiceFiscaleOperatore', codiceFiscaleOperatore);
        return this.http.get<any>(url,  { params: params });
    }
    
    ricercaPraticaAccreditamento() {
        var url: string = this.config.getBEServer() + '/rest/accreditamento/ricercaPraticaAccreditamento';
        return this.http.post<AccreditamentoVO>(url, this.ricercaAccreditamentoRequest);
    }
    
    ricercaPratiche() {
        var url: string = this.config.getBEServer() + '/rest/accreditamento/ricercaPraticheFO';
        //const header = new HttpHeaders({ 'Content-Type': 'application/json; charset=utf-8' });
        return this.http.get<Array<UtenteProvvisorioVO>>(url);
    }

    //DICHIARANTE COMPILATO
    private dichiaranteVO: DichiaranteVO;

    public set confermaDichiarante(dichiaranteVO: DichiaranteVO) {
        this.dichiaranteVO = dichiaranteVO;
    }
    public get confermaDichiarante(): DichiaranteVO {
        return this.dichiaranteVO;
    }

    recuperaLegaleRappresentanteByCfdichiaranteAndPi(cfDichiarante: string, piva: string) {
        var url: string = this.config.getBEServer() + '/rest/accreditamento/recuperaLegaleRappresentanteByCfdichiaranteAndPi';
        let params = new HttpParams().set('cfDichiarante', cfDichiarante).set('piva', piva);
        return this.http.get<LegaleRappresentanteVO>(url, { params: params });
    }

    //LEGALE RAPPRESENTATE COMPILATO
    private legaleRappresentanteVO: LegaleRappresentanteVO;

    public set confermaLegaleRappresentante(legale: LegaleRappresentanteVO) {
        this.legaleRappresentanteVO = legale;
    }
    public get confermaLegaleRappresentante(): LegaleRappresentanteVO {
        return this.legaleRappresentanteVO;
    }
    
    
  //OPERATORE COMPILATO
    private operatoreVO: OperatoreVO;

    public set confermaOperatoreVO(operatoreVO: OperatoreVO) {
        this.operatoreVO = operatoreVO;
    }
    public get confermaOperatoreVO(): OperatoreVO {
        return this.operatoreVO;
    }


    confermaInserimentoAccreditamento() {
        var url: string = this.config.getBEServer() + '/rest/accreditamento/confermaInserimentoAccreditamento';

        const body = new ConfermaAccreditamentoRequest(this.dichiaranteVO, this.legaleRappresentanteVO, this.operatoreVO);
        return this.http.post<any>(url, body);
    }

    verificaEsistenzaLegaleRappresentante(cfLegale: string) {
        var url: string = this.config.getBEServer() + '/rest/accreditamento/verificaEsistenzaLegaleRappresentante';
        const body = { "cflegaleRappr": cfLegale, "dichiarante": this.dichiaranteVO };
        return this.http.post<any>(url, body);
    }

    clean() {
        this.legaleRappresentanteVO = null;
        this.dichiaranteVO = null;
        this.ricercaDichiaranteRequest = null;
    }


    constructor(private http: HttpClient, private config: ConfigService, private logger: LoggerService) {
        logger.info("InserisciAccreditamentoService constructor");
    }
}