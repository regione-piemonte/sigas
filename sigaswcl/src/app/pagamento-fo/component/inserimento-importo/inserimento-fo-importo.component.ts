import { AfterViewInit, Component, OnInit, Renderer2, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { LoggerService } from "../../../core/services/logger.service";
import { TipoVersamentiVO } from "../../../commons/vo/tipo-versamenti-vo";
import { PaymentFoService } from '../../service/pagamento-fo.service';
import { RicercaVersamentiRequest } from '../../../commons/request/ricerca-versamenti-request';
import { AnagraficaSoggettiService } from '../../../anagrafica-soggetti/service/anagrafica-soggetti.service';
import { LuoghiService } from './../../../shared/service/luoghi-service';
import { ProvinceVO } from "../../../commons/vo/luoghi-vo/province-vo";
import { VersamentiPrVO } from '../../../commons/vo/versamenti-pr-vo';
import { PaymentStoreCartRequest } from '../../commons/request/payment-store-cart-request';

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
  private viewQueryParams: String;
  private opzioniControlloVersamentoSpinnerFlag: boolean = false;
  public areaPagamentoSelezionata: String;
  private countiesList: Array<string>;
  private flagLoadingProvinciaSelezionata: boolean = false;
  private idCartModificata: Number;
    
  constructor(
    private router: Router,
    private logger: LoggerService,
    private foPayService: PaymentFoService,
    private anagraficaSoggettiService: AnagraficaSoggettiService,
    private luoghiService: LuoghiService,
    private renderer: Renderer2,
    private activatedRoute: ActivatedRoute,
  ) { }

  ngAfterViewInit(): void {
    const element = this.renderer.selectRootElement('#idDivToContPrin');
    setTimeout(() => element.focus(), 0);
  } 

  ngOnInit() {
    window.scrollTo(0, 0);  

    //Controllo angrafica selezionata
    if(!this.foPayService.cartReq.idAnag){
      this.router.navigateByUrl("home/selezione-soggetto-pagamento");
    }
    
    this.pageLoadingInProgress = true;    

    this._inizializzaVariabiliClasse();

    this.foPayService.reloadCart();
    
    this._caricaComboTipoPagamento();
    
    this._gestioneInformazioniProvincia();        
  }
  
  changeArea($event){
    this.flagLoadingProvinciaSelezionata = true;
    this.luoghiService.provinciaBySigla(this.foPayService.cartReq.area)
      .subscribe(resp => {
        this.provinciaSelezionata = resp;
        this.areaPagamentoSelezionata = this.foPayService.cartReq.area;
        this.flagLoadingProvinciaSelezionata = false;
      }, err => {
        this.logger.error('error');
        this.flagLoadingProvinciaSelezionata = false;
    });
    this.message = "";            
    this.showMsg = false;
    if(this.viewQueryParams != 'ins') {
      this.checkCodMeseTipo();
    }    
  }

  goToStep(stepname: string) {
    if(stepname == 'popup-si'){
      // this.foPayService.navigateTo = "selezione-soggetto-pagamento";      
      // return this.router.navigate([`home/selezione-soggetto-pagamento`], {queryParams: {caller: 'add'}});
      this.ngOnInit();
      this.viewQueryParams = 'add'; 
      return
    };
    if(stepname == 'popup-no'){            
      return this.router.navigate([`home/carrello-fo`], {queryParams: {caller: this.viewQueryParams}});
    };    
    this.pageLoadingInProgress = true;    
    if(this.viewQueryParams == 'add')
    {
      if(this.foPayService.meseSelezionatoAggiungiPagamentoCarrello!=null && this.foPayService.meseSelezionatoAggiungiPagamentoCarrello!=undefined){
        this.foPayService.cartReq.area = this.foPayService.meseSelezionatoAggiungiPagamentoCarrello;
      }           
      
      this.foPayService.savePaymentCart(() => {
        this.idCartModificata = this.foPayService.cartReq.id;
        this._filterCartListByLastCard();
        this.foPayService.navigateTo = stepname;        
        this.apriInfoPopup();
        this.pageLoadingInProgress = false;
      }, 
      (error) => {
        if(error.errorCode == 'BUSSINESS EXCEPTION'){
          this.showErrorMessage = error.message;
        } else {
          this.showErrorMessage = 'Errore generico durante la registrazione del carrello. Riprovare in la registrazione in seguito. ';
        }
        this.pageLoadingInProgress = false;
      }
    );

  } else {            
      this.foPayService.insertPaymentCart(() => {
        this.idCartModificata = null;
        this._filterCartListByLastCard();
        this.foPayService.navigateTo = stepname;        
        this.apriInfoPopup();
        this.pageLoadingInProgress = false;
      }, 
      (error) => {
        if(error.errorCode == 'BUSSINESS EXCEPTION'){
          this.showErrorMessage = error.message;
        }else{
          this.showErrorMessage = 'Errore generico durante la registrazione del carrello. Riprovare in la registrazione in seguito. ';
        }
        this.pageLoadingInProgress = false;
      });
    }
  }

  _findCartByPaymentCodeMeseTipo(paymentCode: String, mese: string, tipo: String, area: String)
  {
    let output: boolean = false;
    const lst = this.foPayService.cartList;
    Object.keys(lst).forEach((key, index) => {          
      if( lst[key].paymentCode == paymentCode && lst[key].month == mese && lst[key].type == tipo && lst[key].area == area){
        output = true;
      }            
    });
    return output;
  }

  _filterCartListByLastCard(){
      let filterCardList: PaymentStoreCartRequest[]=[];
      const lst = this.foPayService.cartList;    
      Object.keys(lst).forEach((key, index) => {          
        if( lst[key].paymentCode == this.foPayService.cartReq.paymentCode){
          let cartItem:PaymentStoreCartRequest = new PaymentStoreCartRequest(
            lst[key].id,
            lst[key].amount,
            lst[key].year,
            lst[key].area,
            lst[key].subjectName,
            lst[key].idAnag,
            lst[key].subjectCode,
            lst[key].status,
            lst[key].paymentCode,
            lst[key].paymentType,
            lst[key].currentDate,
            lst[key].payDate,
            lst[key].email,
            lst[key].month,
            lst[key].type,
            0,
            lst[key].codiceFiscalePIva,
            lst[key].iuv)
            filterCardList.push(cartItem);
        }            
      });
      this.foPayService.loadCartList(filterCardList);     
      this._setFirstCart();
  }

  _setFirstCart(){
    let codiceFiscalePIva = this.foPayService.cartReq.codiceFiscalePIva;
    let lst = this.foPayService.cartList;    

    //Si considera l'ultimo item inserito nel carrello
    if(this.idCartModificata == null){
      let martsKeyListOrdered = Object.keys(lst).sort((a, b) => lst[b].id - lst[a].id);
      let martsLastKey = martsKeyListOrdered[0];
      this.foPayService.cartReq = lst[martsLastKey];
    } else {
      Object.keys(lst).forEach((key, index) => {          
        if( lst[key].id == this.idCartModificata){
          this.foPayService.cartReq = lst[key];
        }
      });
    }    
    
    this.areaPagamentoSelezionata = this.foPayService.cartReq.area;   

    // preserve codiceFiscalePIva from intial form
    this.foPayService.cartReq.codiceFiscalePIva = codiceFiscalePIva;
  }

  cleanAllSelection(){
    // this.foPayService.cartReq.amount = null;
    // this.foPayService.cartReq.type = null;
    // this.foPayService.cartReq.month = null;
    // this.foPayService.cartReq.area = null;
    this.ngOnInit();
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
                  this.ricercaVersamentiRequest = new RicercaVersamentiRequest(+this.foPayService.cartReq.idAnag, 
                                                                               this.foPayService.cartReq.year, 
                                                                               this.meseSelDescrizione, 
                                                                               this.provinciaSelezionata.id, 
                                                                               this.tipoVersamentoSelezionato.idTipoVersamento);
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
                                  //sigas-tasks/-/issues/76                                                                 
                                  //this.showErrorMessage = res.message;
                                  this.showErrorMessage = "Per i dati impostati e' gia' presente un carrello pagato o e' gia' stato emesso un bollettino.";
                              }, err => {
                                this.logger.error("errore ");
                            });
                            
                          }else{
                              
                              this.showErrorMessage = null;

                              //this.apriInfoPopup();
                              this.goToStep('carrello-fo')
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
      this.showErrorMessage = null;      
      for(let meseAnn of this.mesiAnnuali){
          if(meseAnn.valore == this.foPayService.cartReq.month){
              this.meseSelDescrizione = meseAnn.descrizione;
              console.log(this.meseSelDescrizione);
          }
      }
      this.message = "";            
      this.showMsg = false;
      if(this.viewQueryParams != 'ins') {
        this.checkCodMeseTipo();
      }
  }
  
  changeTipoVersamento(event: any){
      this.message = "";            
      this.showMsg = false;
      this.showErrorMessage = null;

      if(this.viewQueryParams != 'ins') {
        this.checkCodMeseTipo();
      }
  }

  private checkCodMeseTipo() {
    let areaSelezionata: String = this.foPayService.cartReq.area;
    if(this.foPayService.meseSelezionatoAggiungiPagamentoCarrello!=null && this.foPayService.meseSelezionatoAggiungiPagamentoCarrello!=undefined){
      areaSelezionata = this.foPayService.meseSelezionatoAggiungiPagamentoCarrello;
    }    
    if(this._findCartByPaymentCodeMeseTipo(this.foPayService.cartReq.paymentCode, this.foPayService.cartReq.month, 
                                           this.foPayService.cartReq.type, areaSelezionata))
    {
      this.message = "Il carrello presenta già un versamento con le stesse caratteristiche. Continuando si andrà a sovrascrivere il versamento esistente.";        
      this.showMsg = true;      
    } 
  }
  
  public apriInfoPopup(){
      $('#dialogBox').modal('show');
  }
  
  public chiudiInfoPopUp() {
      $('#dialogBox').modal('hide');
  }

  private _inizializzaVariabiliClasse(){
           
    this.foPayService.cartReq.amount = null;
    this.foPayService.cartReq.month = null;
    this.foPayService.cartReq.type = null;

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

    this.viewQueryParams = this.activatedRoute.snapshot.queryParamMap.get('caller');
    this.loadingCrtVersamentoEsistente = false;

    this.foPayService.cartReq.area = null;
  }

  private _caricaComboTipoPagamento(){
    this.foPayService.retrievePaymentTypes().subscribe(resp => 
      { 
          this.paymentTypes = resp; 
          this.pageLoadingInProgress = false;
      },
      err => { 
          this.logger.error(err);
      }
    );  
  }

  private _gestioneInformazioniProvincia(){
    this.flagLoadingProvinciaSelezionata = true;
    this.luoghiService.provinciaBySigla(this.foPayService.cartReq.area)
      .subscribe(resp => {
        this.provinciaSelezionata = resp;
        this.flagLoadingProvinciaSelezionata = false;
      }, err => {
        this.logger.error('error');
        this.flagLoadingProvinciaSelezionata = false;
    });

    this.foPayService.getAllPiemonteCounties().subscribe(res => {
        this.countiesList = res;
      }, err => {
        this.logger.error("errore ");
    });
    this.areaPagamentoSelezionata = this.foPayService.cartReq.area;
  }

}
