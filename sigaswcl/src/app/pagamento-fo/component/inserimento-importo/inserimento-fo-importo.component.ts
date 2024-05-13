import { AfterViewInit, Component, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Router } from "@angular/router";
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { LoggerService } from "../../../core/services/logger.service";
import { TipoVersamentiVO } from "../../../commons/vo/tipo-versamenti-vo";
import { PaymentFoService } from '../../service/pagamento-fo.service';
import { RicercaVersamentiRequest } from '../../../commons/request/ricerca-versamenti-request';
import { AnagraficaSoggettiService } from '../../../anagrafica-soggetti/service/anagrafica-soggetti.service';
import { LuoghiService } from './../../../shared/service/luoghi-service';
import { ProvinceVO } from "../../../commons/vo/luoghi-vo/province-vo";
import { VersamentiPrVO } from '../../../commons/vo/versamenti-pr-vo';

declare var $: any;

@Component({
  selector: 'app-inserimento-fo-importo',
  templateUrl: './inserimento-fo-importo.component.html',
  styleUrls: ['./inserimento-fo-importo.component.scss']
})

@DestroySubscribers()
export class PagamentoFoImporto implements OnInit, AfterViewInit {
  
  private showErrorMessage: string = null;
  private paymentTypes: Array<TipoVersamentiVO>;
  private pageLoadingInProgress: boolean;  
  private userAmountForPrevYear: string = null;
  private ricercaVersamentiRequest: RicercaVersamentiRequest;
  private provinciaSelezionata: ProvinceVO;
  private tipoVersamentoSelezionato: TipoVersamentiVO;

  private message: string;
  private showMsg: boolean=false;
  private levelMessage: string;
  private loadingCrtVersamentoEsistente:boolean=false;
  private mesiAnnuali: Array<any>;
  private meseSelDescrizione: string;
  private elencoVersamenti:  Array<VersamentiPrVO>;
    
  constructor(
    private router: Router,
    private logger: LoggerService,
    private foPayService: PaymentFoService,
    private anagraficaSoggettiService: AnagraficaSoggettiService,
    private luoghiService: LuoghiService,
    private renderer: Renderer2
  ) { }

  ngAfterViewInit(): void {
    const element = this.renderer.selectRootElement('#idDivToContPrin');
    setTimeout(() => element.focus(), 0);
  }

  ngOnInit() {
    window.scrollTo(0, 0);

    this.foPayService.cartReq.amount = null;
    this.foPayService.cartReq.month = null;
    this.foPayService.cartReq.type = null;

/*if(true){
this.foPayService.cartReq.year = '2020';
this.foPayService.cartReq.idAnag='5251';
this.foPayService.cartReq.month = '1';
this.foPayService.cartReq.type = '1';
}*/

    if(!this.foPayService.cartReq.idAnag)
      this.router.navigateByUrl("home/selezione-soggetto-pagamento");

    this.pageLoadingInProgress = true;

    this.foPayService.retrievePaymentTypes().subscribe(resp => { 
          this.paymentTypes = resp; 
          this.pageLoadingInProgress = false;
        },
        err => { this.logger.error(err); });

    /*
    this.foPayService.getUserAmountsForPrevYear().subscribe(res => {
          res.forEach((item) => {
            this.userAmountForPrevYear = item?item.replace('.',','):null;
          });
        },
        err => { this.logger.error(err); });
    */
    
    
    this.luoghiService.provinciaBySigla(this.foPayService.cartReq.area)
    .subscribe(resp => {
      this.provinciaSelezionata = resp;
    }, err => {
      this.logger.error('error');
    });
    
    this.mesiAnnuali = [
                        {
                            'valore': '1',
                            'descrizione': 'Gennaio'
                        },
                        {
                            'valore': '2',
                            'descrizione': 'Febbraio'
                        },
                        {
                            'valore': '3',
                            'descrizione': 'Marzo'
                        },
                        {
                            'valore': '4',
                            'descrizione': 'Aprile'
                        },
                        {
                            'valore': '5',
                            'descrizione': 'Maggio'
                        },
                        {
                            'valore': '6',
                            'descrizione': 'Giugno'
                        },
                        {
                            'valore': '7',
                            'descrizione': 'Luglio'
                        },
                        {
                            'valore': '8',
                            'descrizione': 'Agosto'
                        },
                        {
                            'valore': '9',
                            'descrizione': 'Settembre'
                        },
                        {
                            'valore': '10',
                            'descrizione': 'Ottobre'
                        },
                        {
                            'valore': '11',
                            'descrizione': 'Novembre'
                        },
                        {
                            'valore': '12',
                            'descrizione': 'Dicembre'
                        }
                    ];
    
    this.loadingCrtVersamentoEsistente = false;
  }

  /*
  amountPlaceholder():string {
    if(this.foPayService.cartReq.month && this.userAmountsForPrevYear[this.foPayService.cartReq.month])
      return this.userAmountsForPrevYear[this.foPayService.cartReq.month];
    return this.userAmountForPrevYear;
  }
  */

  goToStep(stepname: string) {
    this.foPayService.savePaymentCart(() => {
      this.foPayService.navigateTo = stepname;
      this.router.navigateByUrl("home/"+stepname);
    }, 
    (error) => {
      if(error.errorCode == 'BUSSINESS EXCEPTION')
        this.showErrorMessage = error.message;
      else
        this.showErrorMessage = 'Errore generico durante la registrazione del carrello. Riprovare in la registrazione in seguito. ';
    });

  }

  cleanAllSelection(){
    this.foPayService.cartReq.amount = null;
    this.foPayService.cartReq.type = null;
    this.foPayService.cartReq.month = null;
  }
  
  goHome() {
    this.router.navigateByUrl("home");
  }

  goBack() {
    this.router.navigateByUrl("home/selezione-soggetto-pagamento");
  }
  
  controlloVersamentoEsistente(){
      this.loadingCrtVersamentoEsistente = true;
      this.anagraficaSoggettiService.ricercaTipoVersamenti().subscribe(data => {
          for (let tipoVersamento of data) {
              console.log('tipoVersamento.idTipoVersamento '+ tipoVersamento.idTipoVersamento + 'this.foPayService.cartReq.type '+ this.foPayService.cartReq.type)
              if(tipoVersamento.idTipoVersamento == +this.foPayService.cartReq.type){
                  this.tipoVersamentoSelezionato = tipoVersamento;
                  this.ricercaVersamentiRequest = new RicercaVersamentiRequest( +this.foPayService.cartReq.idAnag, this.foPayService.cartReq.year, this.meseSelDescrizione, this.provinciaSelezionata.id, this.tipoVersamentoSelezionato.idTipoVersamento );
                  this.anagraficaSoggettiService.ricercaVersamentiReq = this.ricercaVersamentiRequest;
                  this.anagraficaSoggettiService.ricercaVersamenti()
                      .subscribe( resp => {
                          this.elencoVersamenti = resp;
                          if (this.meseSelDescrizione != null && this.meseSelDescrizione.toUpperCase() !== 'TUTTI') {
                              this.elencoVersamenti = this.elencoVersamenti.filter(versamento => 
                                  versamento.mese.toUpperCase() === this.meseSelDescrizione.toUpperCase()
                              )
                             }
                          
                           this.elencoVersamenti = this.elencoVersamenti.filter(versamento => 
                               versamento.tipo.denominazione.toUpperCase() === tipoVersamento.denominazione.toUpperCase()
                           )
                           
                          if(this.elencoVersamenti!=null && this.elencoVersamenti.length>0){
                              this.foPayService.retriveMessageAndParameters("", "msgVersamentoEsistente")
                              .subscribe(res => {
                                  this.message = res.message;
                                  this.showMsg = true;
                                  this.levelMessage = res.levelMessagge;
                              }, err => {
                                this.logger.error("errore ");
                            });
                          }else{
                              this.message = "";
                              this.showMsg = false;
                              this.levelMessage = "";
                              this.apriInfoPopup();
                          }
                          this.loadingCrtVersamentoEsistente = false;
                      });
              }
          }
          
          
      }, err => {
          this.logger.error("Errore nel recupero tipo versamento");
      });
      
      
      
          
  }

  changeMounth(event: any) {
      this.message = "";
      this.showMsg = false;
      this.levelMessage = "";
      
      for(let meseAnn of this.mesiAnnuali){
          if(meseAnn.valore == this.foPayService.cartReq.month){
              this.meseSelDescrizione = meseAnn.descrizione;
              console.log(this.meseSelDescrizione);
          }
      }
      
  }
  
  changeTipoVersamento(event: any){
      this.message = "";
      this.showMsg = false;
      this.levelMessage = "";
  }
  
  public apriInfoPopup(){
      $('#dialogBox').modal('show');
  }
  
  public chiudiInfoPopUp() {
      $('#dialogBox').modal('hide');
  }

}
