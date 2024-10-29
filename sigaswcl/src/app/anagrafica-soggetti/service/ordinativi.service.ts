import { LuoghiService } from './../../shared/service/luoghi-service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ConfigService } from '../../core/services/config.service';
import { RicercaOrdinativiRequest } from '../../commons/request/ricerca-ordinativi-request';
import { ConfermaSoggettoRequest } from '../../commons/request/conferma-soggetto-request';
import { RicercaVersamentiRequest } from '../../commons/request/ricerca-versamenti-request';
import { LoggerService } from '../../core/services/logger.service';
import { DownloadReport } from '../../commons/request/download-Report-request';
import { DownloadVersamentiReport } from '../../commons/request/download-Versamenti-Report-request';
import { DownloadAccertamentiReport } from '../../commons/request/download-Accertamenti-Report-request';
import { DownloadSoggettiReport } from '../../commons/request/download-Soggetti-Report-request';
import { DownloadComunicazioneReport } from '../../commons/request/download-Comunicazione-Report-request';
import { AssociaSoggettoRequest } from '../../commons/request/associa-soggetto-request';
import { ConfermaConsumiRequest } from '../../commons/request/conferma-consumi-request';
import { ValidaSoggettoRequest } from '../../commons/request/valida-soggetto-request';
import { GestioneAllarmeRequest } from '../../commons/request/gestione-Allarme-request';
import { ConfermaVersamentoRequest } from '../../commons/request/conferma-versamento-request';
import { SalvaRimborsoRequest } from '../../commons/request/salva-rimborso-request';

import { OrdinativiIncassoVO } from '../../commons/vo/ordinativi-incasso';
import { ScartoVO } from '../../commons/vo/scarto-vo';
import { AnagraficaSoggettoVO } from '../../commons/vo/soggetti-vo';
import { ConsumiPrVO } from '../../commons/vo/consumi-pr-vo';
import { DichiaranteVO } from '../../commons/vo/dichiarante-vo';
import { FusioneSoggettoVO } from '../../commons/vo/fusione-vo';
import { AliquotaVO } from '../../commons/vo/aliquota-vo';
import { StoricoConsumiVO } from '../../commons/vo/storico-consumi-vo';
import { ProvinceVO } from '../../commons/vo/luoghi-vo/province-vo';
import { TipoVersamentiVO } from '../../commons/vo/tipo-versamenti-vo';
import { VersamentiPrVO } from '../../commons/vo/versamenti-pr-vo';
import { AllarmiSoggettoVO } from '../../commons/vo/allarmi-soggetto-vo';
import { Observable } from 'rxjs';
import { AnaComunicazioniVO } from '../../commons/vo/ana-comunicazioni-vo';
import { TipoComunicazioniVO } from '../../commons/vo/tipo-comunicazioni-vo';
import { ConfermaAnaComunicazioniRequest } from '../../commons/request/conferma-anaComunicazioni-request';
import { RimborsoVO } from '../../commons/vo/rimborso-vo';
import { RicercaAnaComunicazioniRequest } from '../../commons/request/ricerca-anaComunicazioni-request';
import { AllarmeDocumentoRequest } from '../../commons/request/allarme-documento-request';
import { RicercaConsumiRequest } from '../../commons/request/ricerca-consumi-request';
import { ConsumiVO } from '../../commons/vo/consumi-vo';
import { ConfermaPagamentoRequest } from '../../commons/request/conferma-pagamento-request';
import { ConfermaVersamentoContabiliaRequest } from '../../commons/request/conferma-versamento-contabilia-request';
import { PagamentiVersamentiVO } from '../../commons/vo/pagamenti-versamenti-vo';

@Injectable({
  providedIn: 'root'
})
export class OrdinativiService {

  private ricercaConsumiRequest: RicercaConsumiRequest;
  private ricercaOrdinativiRequest: RicercaOrdinativiRequest;
  private elencoOrdinativiIncassoSelezionati: Array<OrdinativiIncassoVO>;
  private soggettoDaAssociare: AnagraficaSoggettoVO;
  private ricercaAnaComunicazioniRequest: RicercaAnaComunicazioniRequest;
  private confermaAnaComunicazioniRequest: ConfermaAnaComunicazioniRequest;
  private ricercaVersamentiRequest: RicercaVersamentiRequest;
  private gestioneAllarmeRequest: GestioneAllarmeRequest;

  private ordinativoIncasso: OrdinativiIncassoVO;
  private idConsumi: number;
  private idTipoComunicazione: number;
  private dichiarante: DichiaranteVO;
  private dichiaranteSelezionato: DichiaranteVO;
  private soggetto: AnagraficaSoggettoVO;
  private anno: string;
  private validazione: boolean;
  private codiceAzienda: string;
  private idDocumento: number;
  private idAnag: number;
  private requestAcc: Array<DownloadVersamentiReport>;
  listaAccertamentiRequest: Array<ConfermaVersamentoRequest>;
  private subscribers: any = {};

    private showMessageError: boolean;
    private showSuccess: boolean;
    private messageSuccess: string;
    private messageError: string;

  
  constructor(
    private http: HttpClient,
    private config: ConfigService,
    private logger: LoggerService,
    private luoghiService: LuoghiService
  ) {
    logger.info('ConsultaSoggettiService constructor');
  }

  public set ricercaOrdinativiReq(ricercaOrdinativiRequest: RicercaOrdinativiRequest) {
      this.ricercaOrdinativiRequest = ricercaOrdinativiRequest;
    }

    public get ricercaOrdinativiReq(): RicercaOrdinativiRequest {
      return this.ricercaOrdinativiRequest;
    }
    
  public set ricercaConsumiReq(ricercaConsumiRequest: RicercaConsumiRequest) {
    this.ricercaConsumiRequest = ricercaConsumiRequest;
  }

  public get ricercaConsumiReq(): RicercaConsumiRequest {
    return this.ricercaConsumiRequest;
  }
      
      
  public set listaOrdinativiIncassoSelezionati(elencoOrdinativiIncassoSelezionati: Array<OrdinativiIncassoVO>) {
      this.elencoOrdinativiIncassoSelezionati = elencoOrdinativiIncassoSelezionati;
    }

  public get listaOrdinativiIncassoSelezionati(): Array<OrdinativiIncassoVO> {
      return this.elencoOrdinativiIncassoSelezionati;
    }
        
  public set setShowMessageError(showMessageError: boolean) {
    this.showMessageError = showMessageError;
  }

  public get getShowMessageError(): boolean {
    return this.showMessageError;
  }
  
  public set setShowSuccess(showSuccess: boolean) {
      this.showSuccess = showSuccess;
    }

    public get getShowSuccess(): boolean {
      return this.showSuccess;
    }
    
    public set setMessageSuccess(messageSuccess: string) {
        this.messageSuccess = messageSuccess;
      }

      public get getMessageSuccess(): string {
        return this.messageSuccess;
      }
      
      public set setMessageError(messageError: string) {
          this.messageError = messageError;
        }

        public get getMessageError(): string {
          return this.messageError;
        }
  
  public set soggettoAssociato(soggettoDaAssociare: AnagraficaSoggettoVO) {
      this.soggettoDaAssociare = soggettoDaAssociare;
    }

    public get soggettoAssociato(): AnagraficaSoggettoVO {
      return this.soggettoDaAssociare;
    }
  
  

  public ricercaAnnualitaPagamenti() {
    var url: string = this.config.getBEServer() + '/rest/home/ricercaAnnualitaPagamenti';
    return this.http.get<Array<string>>(url);
  }
  
  public ricercaOrdinativi() {
      var url: string = this.config.getBEServer() + '/rest/home/ricercaOrdinativi';
      return this.http.post<Array<OrdinativiIncassoVO>>(url, this.ricercaOrdinativiRequest);
    }
  
  public ricercaConsumi() {
      var url: string = this.config.getBEServer() + '/rest/home/ricercaConsumi';
      return this.http.post<Array<ConsumiVO>>(url, this.ricercaConsumiRequest);
    }
  
  public conciliaPagamento(pagamento: OrdinativiIncassoVO) {
      var url: string = this.config.getBEServer() + '/rest/home/conciliaPagamento';
      const  body = new ConfermaPagamentoRequest(pagamento);
      return this.http.post<OrdinativiIncassoVO>(url, body);
    }
  
  public confermaModificaPagamento(pagamento: OrdinativiIncassoVO) {
      var url: string = this.config.getBEServer() + '/rest/home/updatePagamento';
      const  body = new ConfermaPagamentoRequest(pagamento);
      return this.http.post<OrdinativiIncassoVO>(url, body);
    }
  
  public confermaInserimentoVersamentoContabilia(versamento: VersamentiPrVO, pagamento: OrdinativiIncassoVO, conciliato: boolean, confermaInserimento: boolean) {
      var url: string = this.config.getBEServer() + '/rest/versamenti/insertVersamentoContabilia';

      const body = new ConfermaVersamentoContabiliaRequest(versamento, pagamento, conciliato, confermaInserimento);
      return this.http.post<Array<PagamentiVersamentiVO>>(url, body);
    }

  public eliminaConciliazione(pagamento: OrdinativiIncassoVO) {
      var url: string = this.config.getBEServer() + '/rest/home/eliminaConciliazione';
      const  body = new ConfermaPagamentoRequest(pagamento);
      return this.http.post<OrdinativiIncassoVO>(url, body);
    }
  
}
