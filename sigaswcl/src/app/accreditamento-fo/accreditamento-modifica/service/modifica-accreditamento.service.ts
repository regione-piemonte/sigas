import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../../../core/services/config.service';
import {LoggerService} from '../../../core/services/logger.service';
import {DichiaranteVO} from '../../../commons/vo/dichiarante-accreditamento-vo';
import {LegaleRappresentanteVO} from '../../accreditamento-shared/commons/vo/legale-rappresentante-vo';
import {OperatoreVO} from '../../accreditamento-shared/commons/vo/operatore-vo';
import {ConfermaAccreditamentoRequest} from '../../accreditamento-shared/commons/request/conferma-accreditamento-request';
import {UtenteProvvisorioVO} from '../../../commons/vo/utente-provvisorio-vo';
import {RicercaDichiaranteRequest} from '../../../commons/request/ricerca-dichiarante-request';
import {RicercaAccreditamentoRequest} from '../../../commons/request/ricerca-accreditamento-request';
import {AccreditamentoVO} from '../../../commons/vo/accreditamento-vo';
import {AnnullaAccreditamentoRequest} from '../../../commons/request/annulla-accreditamento-request';

@Injectable()
export class ModificaAccreditamentoService {

    private dichiaranteVO: DichiaranteVO;
    private legaleRappresentanteVO: LegaleRappresentanteVO;
    private operatoreVO: OperatoreVO;
    private ricercaDichiaranteRequest: RicercaDichiaranteRequest;
    private ricercaAccreditamentoRequest: RicercaAccreditamentoRequest;
    private confermaAnnulamentoRequest: AnnullaAccreditamentoRequest;

    constructor(private http: HttpClient, private config: ConfigService, private logger: LoggerService) {
        logger.info('ModificaAccreditamentoService constructor');
    }

    public get confermaDichiarante(): DichiaranteVO {
        return this.dichiaranteVO;
    }

    public set confermaDichiarante(dichiaranteVO: DichiaranteVO) {
        this.dichiaranteVO = dichiaranteVO;
    }

    public get confermaLegaleRappresentante(): LegaleRappresentanteVO {
        return this.legaleRappresentanteVO;
    }

    public set confermaLegaleRappresentante(dichiaranteVO: LegaleRappresentanteVO) {
        this.legaleRappresentanteVO = dichiaranteVO;
    }

    public get confermaOperatore(): OperatoreVO {
        return this.operatoreVO;
    }

    public set confermaOperatore(operatoreVO: OperatoreVO) {
        this.operatoreVO = operatoreVO;
    }

    public get RicercaDichiarante(): RicercaDichiaranteRequest {
        return this.ricercaDichiaranteRequest;
    }

    public set RicercaDichiarante(ricercaDichiaranteRequest: RicercaDichiaranteRequest) {
        this.ricercaDichiaranteRequest = ricercaDichiaranteRequest;
    }

    public get ricercaAccreditamento(): RicercaAccreditamentoRequest {
        return this.ricercaAccreditamentoRequest;
    }

    public set ricercaAccreditamento(ricercaAccreditamentoRequest: RicercaAccreditamentoRequest) {
        this.ricercaAccreditamentoRequest = ricercaAccreditamentoRequest;
    }

    public get confermaAnnulamento(): AnnullaAccreditamentoRequest {
        return this.confermaAnnulamentoRequest;
    }

    public set confermaAnnulamento(confermaAnnulamentoRequest: AnnullaAccreditamentoRequest) {
        this.confermaAnnulamentoRequest = confermaAnnulamentoRequest;
    }

    recuperaDichiarante() {
        const url: string = this.config.getBEServer() + '/rest/accreditamento/recuperaDichiarante';
        return this.http.get<DichiaranteVO>(url);
    }

    recuperaLegaleRappresentante() {
        const url: string = this.config.getBEServer() + '/rest/accreditamento/recuperaLegaleRappresentante';
        return this.http.get<LegaleRappresentanteVO>(url);
    }

    recuperaOperatoreServizi() {
        const url: string = this.config.getBEServer() + '/rest/accreditamento/recuperaOperatore';
        return this.http.get<OperatoreVO>(url);
    }

    cambiaStatoNoteAccreditamento() {
        const url: string = this.config.getBEServer() + '/rest/accreditamento/cambiaStatoNoteAccreditamento';
        return this.http.post<any>(url, this.confermaAnnulamento);
    }

    ricercaPratiche() {
        const url: string = this.config.getBEServer() + '/rest/accreditamento/ricercaPraticheFO';
        return this.http.get<Array<UtenteProvvisorioVO>>(url);
    }

    clean() {
        this.dichiaranteVO = null;
        this.legaleRappresentanteVO = null;
    }

    ricercaPraticaAccreditamento() {
        const url: string = this.config.getBEServer() + '/rest/accreditamento/ricercaPraticaAccreditamento';
        return this.http.post<AccreditamentoVO>(url, this.ricercaAccreditamentoRequest);
    }

    confermaModificaAccreditamento(idUtenteProvvisorio: number) {
        const url: string = this.config.getBEServer() + '/rest/accreditamento/confermaAggiornamentoAccreditamento';
        const confermaAccreditamentoRequest = new ConfermaAccreditamentoRequest(
            this.dichiaranteVO, this.legaleRappresentanteVO, this.operatoreVO);
        confermaAccreditamentoRequest.idOperatoreProvvisorio = idUtenteProvvisorio;
        return this.http.post<any>(url, confermaAccreditamentoRequest);
    }
}

