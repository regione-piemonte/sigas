import { OnInit, OnDestroy, Component, EventEmitter, Output, ViewChild, AfterViewInit, ViewChildren, Renderer2 } from "@angular/core";
import { Router } from "@angular/router";
import { ExceptionVO } from "../../../../core/commons/vo/exceptionVO"; 
import { Observable } from "rxjs/Observable";
import { InserisciAccreditamentoService } from "../../service/inserisci-accreditamento.service";
import { RegioniVO } from "../../../../commons/vo/luoghi-vo/regioni-VO";
import { ProvinceVO } from "../../../../commons/vo/luoghi-vo/province-vo"; 
import { ComuniVO } from "../../../../commons/vo/luoghi-vo/comuni-vo";
import { LuoghiService } from "../../../../shared/service/luoghi-service";
import { MessageRestError, GinevraPiemonte } from "../../../../core/commons/commons-core";
import { RoutingAccreditamentoFO } from "../../../../commons/routing";
import { DichiaranteVO } from "../../../../commons/vo/dichiarante-accreditamento-vo";
import { NazioniVO } from "../../../../commons/vo/luoghi-vo/nazioni-VO";
import { DestroySubscribers } from "../../../../core/commons/decorator/destroy-subscribers";
import { LoggerService } from '../../../../core/services/logger.service';
import { Subject } from 'rxjs';
import { DataTableIt } from '../../../../commons/class/commons-data-table';
import { RicercaDichiaranteRequest } from '../../../../commons/request/ricerca-dichiarante-request';
import { DataTableDirective } from 'angular-datatables';
import { AnagraficaSoggettoVO } from '../../../../commons/vo/soggetti-vo';
import { VerificaDichiaranteRequest } from "../../../../commons/request/verifica-dichiarante-request";


@Component({
    selector: 'app-accreditamento-inserimento-dichiarante',
    templateUrl: './inserimento-dichiarante.component.html',
    styleUrls: ['./inserimento-dichiarante.component.scss']
})
@DestroySubscribers()
export class InserimentoDichiaranteComponent implements OnInit, OnDestroy, AfterViewInit {
    @ViewChild(DataTableDirective) dtElement: DataTableDirective;
    dichiaranteModel: DichiaranteVO;
    dichiaranteModelList: Array<AnagraficaSoggettoVO>;
    dichiaranteCensito: DichiaranteVO;
    regioniModel: Array<RegioniVO>;
    provinciaModel: Array<ProvinceVO>;
    comuniModel: Array<ComuniVO>;
    statiEsteriModel: Array<NazioniVO>;
    ricercaModel: RicercaDichiaranteRequest;


    loaderDichiarante: boolean;
    loaderProvince: boolean;
    loaderComuni: boolean;
    loaderRegioni: boolean;
    loaderStatiEsteri: boolean;
    loaderInsNuovoDichiarante: boolean;
    //DA ESPORRE
    messageInfo: string;
    showMessageInfo: boolean = false;
    loaderDT:boolean;
    primaRicercaSenzaRisultati: boolean;

    public subscribers: any = {};
private dtTrigger: Subject<any> = new Subject();

private dtOptions: any;

    showMessage: boolean;
    message: string;
    tipoMessaggio: string;

    ngOnInit(): void {
        //CASO REFRESH
        this.dichiaranteModel = new DichiaranteVO(null,'','',new RegioniVO(null, ''),new ProvinceVO(null, '',''),new ComuniVO(null, ''),'','','','','',null,false);
        
//        this.loadRegioni();
//        this.loadStatiEsteri();
        let ricercaRequest = this.inserisciAccreditamentoService.RicercaDichiarante;
//        if (ricercaRequest == null)
//            this.router.navigateByUrl(RoutingAccreditamento.RICERCA_DICHIARANTE);
//        else {
            //SE CONFERMA EFFETTUATA RICARICO DAL SERVICE
            let confermaEffettuata = this.inserisciAccreditamentoService.confermaDichiarante;
//            if (confermaEffettuata == null)
//                this.initAccrDichiaranteOSCAR();
//            else {
//                this.initAccrDichiaranteConfermato(confermaEffettuata);
//            }
            this.loaderInsNuovoDichiarante = false;
            this.loaderDichiarante = true;
//            this.loadRegioni();
//            this.loadStatiEsteri();
             
            this.loaderComuni = true;
            this.loaderProvince = true;
//        }
            
            this.dtOptions = {
                    searching: true,
                    pagingType: 'full_numbers',
                    info: false,
                    language: DataTableIt.language,
                    ordering: false,
                    columnDefs: [
                      { className: 'dt-center', 'targets': [0, 1, 2] },
                      { width: '30%', targets: 0 },
                      { width: '30%', targets: 1 },
                      { width: '40%', targets: 2 },
                    ]
                  };
            setTimeout(() => {
                this.dtTrigger.next();
              });
            
            
            this.initRicercaModel();

            this.primaRicercaSenzaRisultati=false;
            
            this.loadProvince();
    }
    
    initRicercaModel() {
        this.ricercaModel = new RicercaDichiaranteRequest("", "");
      }
    
    ngAfterViewInit() {
        const element = this.renderer.selectRootElement('#idDivToContPrin');
        setTimeout(() => element.focus(), 0);
    }

    initAccrDichiaranteOSCAR() {
        this.loaderDichiarante = false;
        
        this.subscribers.ricercaDichiarante = this.inserisciAccreditamentoService.ricercaDichiarante().subscribe(data => {
            this.dichiaranteModelList = data;
            this.loaderDichiarante = true;
            this.logger.info(this.dichiaranteModel);
            
        }, err => {
            //SE DICHIARANTE NON TROVATO MOSTRO IL FORM DI INSERIMENTO
            if (err instanceof ExceptionVO && (err.status == "200" || err.status == "400")) {
                this.messageInfo = err.message;
            }
            else this.messageInfo = MessageRestError.GENERIC;
            this.dichiaranteModel = new DichiaranteVO();
            this.loaderDichiarante = true;
            this.showMessageInfo = true;
            this.loaderComuni = true;
            this.loaderProvince = true;
//            this.dichiaranteModel.statoEstero = new NazioniVO(null, null);
            this.dichiaranteModel.regione = new RegioniVO(-1, null);
            this.dichiaranteModel.provincia = new ProvinceVO(null, null, null);
            this.dichiaranteModel.comune = new ComuniVO(null, null);
        });
    }    
    
    loadProvince() {
        this.logger.info("carico le province")
        this.loaderProvince = false;
        this.subscribers.province = this.luoghiService.getAllProvince().subscribe(data => {
            this.loaderProvince = true;
            this.provinciaModel = data;
            this.provinciaModel.push(new ProvinceVO(-1, "Nessuna Provincia Selezionata",""));
        }, err => {
            this.logger.error("Errore nel recupero delle province");
        });
    }

    loadComuni(id: number) {
        this.logger.info("carico i comuni  della provincia con id:" + this.dichiaranteModel.provincia.id);
        if (id != null) {
            this.loaderComuni = false;
//            this.dichiaranteModel.statoEstero = new NazioniVO(null, null);
            this.subscribers.comuniByProvince = this.luoghiService.getComuneByIdProvincia(id).subscribe(data => {
                this.comuniModel = data;
                let com = this.comuniModel.find(x => x.id == this.dichiaranteModel.comune.id);
                if (!com) {
                    this.dichiaranteModel.comune = new ComuniVO(null, null);
//                    this.dichiaranteModel.cap = null;
                    this.dichiaranteModel.indirizzo = null;
//                    this.dichiaranteModel.cap = null;
//                    this.dichiaranteModel.indirizzoNonTrovato = null;
                }
                this.loaderComuni = true;
            }, err => {
                this.logger.error(err);
            });

        }
        else {
            this.comuniModel = new Array<ComuniVO>();
            this.dichiaranteModel.comune = new ComuniVO(null, null);
			this.loaderComuni = true;
        }

    }

    loadStatiEsteri() {
        this.loaderStatiEsteri = false;
        this.subscribers.nazioni = this.luoghiService.getAllNazioni().subscribe(data => {
            this.statiEsteriModel = data;
            this.loaderStatiEsteri = true;
        }, err => {
            this.logger.error(err);
        });
    }

    loadRegioni() {
        this.loaderRegioni = false;
        this.subscribers.regioni = this.luoghiService.getAllRegioni().subscribe(data => {
            this.loaderRegioni = true;
            this.regioniModel = data;
            this.regioniModel.push(new RegioniVO(-1, "Nessuna Regione Selezionata"));
        }, err => {
            this.logger.error("Errore nel recupero delle regioni");
        });
    }

    disableIndirizzo() {
        if (this.dichiaranteModel.comune.id == null || this.dichiaranteModel.regione.id != GinevraPiemonte.id)
            return true;
        else
            return false;
    }


    ngOnDestroy(): void {

    }

    inserisciDichiarante() {
        this.subscribers.verificaDichiarante = this.inserisciAccreditamentoService.verificaDichiarante( this.dichiaranteModel.codiceAzienda, this.inserisciAccreditamentoService.confermaOperatoreVO.codiceFiscale).subscribe(data => {
            this.inserisciAccreditamentoService.confermaDichiarante = this.dichiaranteModel;
            this.router.navigateByUrl(RoutingAccreditamentoFO.INSERMENTO_LEGALE_RAPPR);
        }, err => {
            this.loaderDT = false;
            if (err instanceof ExceptionVO && err.status == '200') {
                this.message = err.message;
                this.showMessage = true;
                this.tipoMessaggio = err.errorCode;
            }
        });
    }
     
    associaDichiarante(dich: AnagraficaSoggettoVO) {     
            
            this.dichiaranteCensito = new DichiaranteVO(null,dich.denominazione, dich.codiceAzienda, new RegioniVO(null, ''), new ProvinceVO(dich.idProvincia, '',''),new ComuniVO(dich.idComune, ''), dich.indirizzo, '', dich.iban, dich.note, '', null,true);
        
            this.inserisciAccreditamentoService.confermaDichiarante = this.dichiaranteCensito;
        
    }
    

    cancellaInsDichiarante() {
        this.dichiaranteModel = new DichiaranteVO(null,'','',new RegioniVO(null, ''),new ProvinceVO(null, '',''),new ComuniVO(null, ''),'','','','','',null,false);
    }
    
    confermaInsDichiarante(){
//        this.inserisciAccreditamentoService.VerificaDichiarante = new VerificaDichiaranteRequest()
        this.loaderDT = true;
        this.subscribers.verificaDichiarante = this.inserisciAccreditamentoService.verificaDichiarante( this.inserisciAccreditamentoService.confermaDichiarante.codiceAzienda, this.inserisciAccreditamentoService.confermaOperatoreVO.codiceFiscale).subscribe(data => {
            this.loaderDT = false;
            this.router.navigateByUrl(RoutingAccreditamentoFO.INSERMENTO_LEGALE_RAPPR);
        }, err => {
            this.loaderDT = false;
            if (err instanceof ExceptionVO && err.status == '200') {
                this.message = err.message;
                this.showMessage = true;
                this.tipoMessaggio = err.errorCode;
            }
        });
    }
    
    resetMsg(){
        this.message = "";
        this.showMessage = false;
        this.tipoMessaggio = "";
    }

    //INDIRIZZO
    elencoIndirizzi: Array<String>;
    loaderIndirizzo: boolean = true;
    indirizzoChange(event: string) {
        if (event.length == 2) {
            this.loaderIndirizzo = false;
            this.subscribers.ricercaIndirizzo = this.luoghiService.ricercaIndirizzo(event, this.dichiaranteModel.comune.id).subscribe(data => {
                this.elencoIndirizzi = data;
                this.loaderIndirizzo = true;
            }, err => {
                this.logger.error(err);
            });
        }
    }

    goBack() {
        this.router.navigateByUrl(RoutingAccreditamentoFO.INSERIMENTO_OPERATRE_SERVIZI);
    }
    
    cancellaCampiRicerca(){
        this.ricercaModel.codiceAzienda = null;
        this.ricercaModel.denominazione = null;
        this.resetMsg(); 
    }
    
    annullaInsDichiarante(){
        this.loaderInsNuovoDichiarante = false;
    }
    
    nuovoDichiarante(){
        this.loaderInsNuovoDichiarante = true;
        this.cancellaInsDichiarante();
    }
    cercaDichiarante(){
        this.loaderDT = true;
        this.inserisciAccreditamentoService.RicercaDichiarante = this.ricercaModel;
        this.resetMsg();
        console.log(this.ricercaModel);
        console.log(this.inserisciAccreditamentoService.RicercaDichiarante);
        this.inserisciAccreditamentoService.confermaDichiarante = null;
        this.subscribers.ricercaDichiarante = this.inserisciAccreditamentoService.ricercaDichiarante().subscribe(data => {
            this.dichiaranteModelList = data;
            if(data==null || data.length == 0){
                this.primaRicercaSenzaRisultati=true;
            }else{
                this.primaRicercaSenzaRisultati=false;
            }
            this.loaderDT = false;
            this.logger.info(this.dichiaranteModel);
            this.rerender();
            
        }, err => {
            //SE DICHIARANTE NON TROVATO MOSTRO IL FORM DI INSERIMENTO
            if (err instanceof ExceptionVO && (err.status == "200" || err.status == "400")) {
                this.messageInfo = err.message;
            }
            else this.messageInfo = MessageRestError.GENERIC;
            this.dichiaranteModel = new DichiaranteVO();
            this.loaderDichiarante = true;
            this.showMessageInfo = true;
            this.loaderComuni = true;
            this.loaderProvince = true;
//            this.dichiaranteModel.statoEstero = new NazioniVO(null, null);
            this.dichiaranteModel.regione = new RegioniVO(-1, null);
            this.dichiaranteModel.provincia = new ProvinceVO(null, null, null);
            this.dichiaranteModel.comune = new ComuniVO(null, null);
            this.loaderDT = true;
        });
    }
    
    rerender(): void {
        this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
           dtInstance.destroy();
           this.dtTrigger.next();
       });
      }



    constructor(
        private logger: LoggerService,
        private inserisciAccreditamentoService: InserisciAccreditamentoService,
        private luoghiService: LuoghiService,
        private router: Router,
        private renderer: Renderer2
    ) { }
}