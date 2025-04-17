import { AfterViewInit, Component, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { Routing } from '../../../commons/routing'
import { PaymentFoService } from '../../service/pagamento-fo.service';
import { LoggerService } from "../../../core/services/logger.service";
import { SigasDialogComponent } from "../../../shared/component/sigas-dialog/sigas-dialog.component";
import { UtilityCtrlVO } from "../../commons/vo/utility-ctrl-vo";
import { SubjectVO } from "../../commons/vo/subject-vo";
import { UtilsService } from "../../../shared/service/utils-service";
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';
import { DataTableIt } from '../../commons/class/commons-data-table';
import { PagamentoSelezioneSoggettoItemTableVO } from '../../../commons/vo/pagamento-selezione-soggetto-item-table-vo';
import { PaymentStoreCartRequest } from '../../commons/request/payment-store-cart-request';
import { TipoVersamentiVO } from "../../../commons/vo/tipo-versamenti-vo";
import { DepositoCauzionaleService } from "../../../core/services/depositocauzionale.service";
import { RigeneraBollettinoPagamentoRequestVO } from '../../../core/commons/vo/RigeneraBollettinoPagamentoRequestVO';

declare var $: any;

@Component({
  selector: 'app-selezione-soggetto',
  templateUrl: './selezione-soggetto.component.html',
  styleUrls: ['./selezione-soggetto.component.scss']
})
@DestroySubscribers()
export class SelezioneSoggetto implements OnInit, AfterViewInit {
  
  private cartOption: number;
  private loaderInserimentoSoggettoPage: boolean;
  private yearDisabled: boolean;
  private subjectDisabled: boolean;
  private cfPivaDisabled: boolean;
  public subscribers: any = {};
  paymentSuccess: boolean;
  showErrorMessage: string = null;  
  url_chiamante_esterno: string;
  idPagamento: string;
  digest: string;
  codEsito: string;

  private yearsList: Array<string>;
  private countiesList: Array<string>;
  private loadingPageFlag: boolean;
  private loaderAzienda: boolean;
  private controlPay: UtilityCtrlVO;
  
  private messageWarning: string;
  private showMsgWarning: boolean=false;
  private levelMessage: string;
  private listSubject : Array<SubjectVO>; 

  @ViewChild(DataTableDirective) dtElement: DataTableDirective;
  private dtOptions: any;  
  private dtTrigger: Subject<any> = new Subject();
  private loaderDT: boolean;
  viewCardListTable: boolean = false;
  pagamentoSelezioneSoggettoItemTableVOList : Array<PagamentoSelezioneSoggettoItemTableVO> = []; 
  elencoCarrelliClass: String;
  private paymentTypes: Array<TipoVersamentiVO>;
  private opzioniCarrelloSpinnerFlag: boolean = false; 
  private callerQueryParms: string;
  private viewQueryParams: String;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private logger: LoggerService,
    private foPayService: PaymentFoService,
    private renderer: Renderer2,
    private utilsService: UtilsService,
    private depositoCauzionaleService: DepositoCauzionaleService,    
  ) { 
      if(this.route.queryParams) {
          //url_chiamante_esterno?idPagamento={identificativoPagamento}&digest={digest}&codEsito=[000|100]
          this.route.queryParams.subscribe(params => {
              this.url_chiamante_esterno = params['url_chiamante_esterno'];
              this.idPagamento = params['idPagamento'];
              this.digest = params['digest'];
              this.codEsito = params['codEsito'];              
          });
      }     

      this.foPayService.loadCardListEvent.subscribe((event: boolean) => {
          this.caricaTabellaCarrelli();
          this.opzioniCarrelloSpinnerFlag = false;
          this.loaderDT = false;
          if(event) {              
            //TO DO 
          }     
      });
  }

  ngAfterViewInit(): void {
    const element = this.renderer.selectRootElement('#idDivToContPrin');
    setTimeout(() => element.focus(), 0);

    //Define datatable 
    this.dtTrigger.next();
  }  
  
  ngOnInit() 
  {    
    this.viewQueryParams = this.route.snapshot.queryParamMap.get('caller');    
    if(this.viewQueryParams != "add" && this.viewQueryParams != "det")
    {
      this.foPayService.meseSelezionatoAggiungiPagamentoCarrello = null;      
    } else if(this.viewQueryParams == "det"){
      this.foPayService.meseSelezionatoAggiungiPagamentoCarrello = this.foPayService.cartReq.area;
    }

    this.elencoCarrelliClass = "{ display: none;}";
    this.dtOptions = {            
      destroy: true,
      head: 20,
      pagingType: 'full_numbers',
      pageLength: 5,
      processing: true,
      language: DataTableIt.language,
      responsive: true,
      searching: false,
      paging: false,
      ordering: false,             
      columnDefs: [
        { className: 'dt-center'},
        { orderable: false, width: '10%', targets: 0 },
        { orderable: false, width: '15%', targets: 1 },
        { orderable: false, width: '10%', targets: 2 },
        { orderable: false, width: '10%', targets: 3 },
        { orderable: false, width: '15%', targets: 4 },
        { orderable: false, width: '10%', targets: 5 },
        { orderable: false, width: '30%', targets: 6 },
      ]
    };

    this.foPayService.retrievePaymentTypes().subscribe(resp => 
      { 
          this.paymentTypes = resp;           
      },
      err => { 
          this.logger.error(err); 

    }); 

    this.clearMes();


    /********************************************************
     * GESTIONE ERRORE PAGAMENTO - REDIRECT DA PPAY
     ********************************************************/
    //if(this.idPagamento && this.digest && this.codEsito){
    if(this.idPagamento && this.codEsito){
        console.log('Sono in redirect ppay');
        this.paymentSuccess = this.codEsito == '000';
        if(!this.paymentSuccess) {
            this.storeCodEsito();
        }
    }
    /********************************************************
     * GESTIONE ERRORE PAGAMENTO - REDIRECT DA PPAY - FINE
     ********************************************************/      
      
    this.loadingPageFlag = true;
    this.loaderAzienda = true;

    if(this.foPayService.navigateTo != 'selezione-soggetto-pagamento' && this.viewQueryParams != "det") 
     {
      
      //Marts      
      this.subjects = {};
      this.foPayService.loadCartList([]); 
      this.foPayService.meseSelezionatoAggiungiPagamentoCarrello = null;           
      //Marts

      this.foPayService.cartReq.subjectName = null;
      this.foPayService.cartReq.year = null;
      this.foPayService.cartReq.area = null;
      this.cartOption = null;
      this.foPayService.cartReq.codiceFiscalePIva = null;
    }
    else if(this.foPayService.cartReq.year){
      setTimeout(() => { this.changeYear(false) ; this.foPayService.reloadCart();}, 500);
    }      
    
    this.foPayService.navigateTo = null;

    this.foPayService.searchYearsForFoUser()
      .subscribe(res => {
        this.yearsList = res;
        this.loadingPageFlag = false;
      }, err => {
        this.logger.error("errore ");
        this.loadingPageFlag = false;
    });

    this.foPayService.getAllPiemonteCounties()
      .subscribe(res => {
        this.countiesList = res;
      }, err => {
        this.logger.error("errore ");
    });
    
    this.foPayService.retriveMessageAndParameters("lunghezzaMinimaCodFiscalePIvaPpay", "msgLunghezzaMinimaCodFiscalePIvaPpay")
      .subscribe(res => {
        this.controlPay = res;
      }, err => {
        this.logger.error("errore ");
    });

    this.yearDisabled = this.subjectDisabled = this.cfPivaDisabled = !!this.foPayService.cartReq.subjectName;
 
    if(this.foPayService.subjectSelected != null) {
      this.foPayService.cartReq.year = this.foPayService.subjectSelected.year;
      this.changeYear(true);
      this.foPayService.cartReq.subjectName = this.foPayService.subjectSelected.subjectName;
      this.foPayService.cartReq.codiceFiscalePIva = this.foPayService.subjectSelected.codiceFiscalePIva;
      
      setTimeout(() => {
        this.changeSubject(null);
        this.foPayService.subjectSelected = null;
      }, 1500);
    }
      
  }  
  
  storeCodEsito() {
      this.foPayService.cartReq.status = "ERROR";
      this.foPayService.cartReq.paymentCode = this.idPagamento;

      this.foPayService.savePaymentCart(() => {                    
          this.depositoCauzionaleService.rigeneraBollettinoPagamento(this.idPagamento).subscribe(
              res => {
                this.messageWarning = "Il pagamento non è stato completato con successo.";
                this.showMsgWarning = true;
                this.levelMessage = 'WARNING';
              },
              error => {
                if(error.errorCode == 'BUSSINESS EXCEPTION')
                  this.showErrorMessage = error.message;
                else
                    this.showErrorMessage = 'Errore generico durante la registrazione dello stato del carrello.';
              });         
      }, 
      (error) => {
          if(error.errorCode == 'BUSSINESS EXCEPTION')
              this.showErrorMessage = error.message;
          else
              this.showErrorMessage = 'Errore generico durante la registrazione dello stato del carrello.';
      });
  }

  private subjectKeys: any = {}; 
  private subjects: { [key: string]: 
      { [key: string]: string }
    } = {};

   clearMes(){
       this.showErrorMessage = null;
       this.showMsgWarning = false;
       this.messageWarning = null;
       this.levelMessage = null;
   }
  changeYear(isEvent: any) {
      this.clearMes();
      this.loaderAzienda = false;
    if(isEvent) {
      this.subjects = {};
      this.foPayService.cartReq.subjectName = null;
      this.foPayService.cartReq.area = null;
      this.cartOption = null;
      this.foPayService.cartReq.codiceFiscalePIva = null;
    }

    this.foPayService.retrieveSubjectsForFoUser(this.foPayService.cartReq.year).subscribe(
        res => {
            this.listSubject = res;
            this.loaderAzienda = true;
            if(res) {
              this.subjects = {};
              res.forEach((item, index) => {
                var areas: any = this.subjects[item.denominazione];
                if(!areas) areas = {};
    
                // not used anymore
                areas[item.siglaProvincia] = 1;
                this.subjects[item.denominazione] = areas;
                this.subjectKeys[item.denominazione] = item.idAnag;
              });
            }

        },
        err => { this.logger.error("errore " + err); });
  } 

  changeSubject(event: any) {
      this.clearMes();      
      this.listSubject.forEach((item, index) => {
          if(this.foPayService.cartReq.subjectName!=undefined && item.denominazione==this.foPayService.cartReq.subjectName){
              this.foPayService.cartReq.codiceFiscalePIva = item.codiceFiscalePIva;
          }
      });
      this.opzioniCarrelloSpinnerFlag = true;
      this.loaderDT = true;     
      this.foPayService.reloadCart();  
      this.foPayService.selectCartsEvent.subscribe((event: boolean) => {
        if (event) {
          this.cartOption = 2;
        }
      });
  }
  
  changeCodFisPiva(){
      console.log('sono qui');
      console.log(this.foPayService.cartReq.codiceFiscalePIva.length);
      console.log(this.controlPay.parameterInt);
      if(this.foPayService.cartReq.codiceFiscalePIva.length<this.controlPay.parameterInt){
              console.log(this.controlPay.levelMessagge);
              this.messageWarning = this.controlPay.message;
              this.showMsgWarning = true;
              this.levelMessage = this.controlPay.levelMessagge;
      }else{
              this.messageWarning = "";
              this.showMsgWarning = false;
              this.levelMessage = "";
      }
              
  }

  cartViewEnabled(): boolean {
    //OLD CODE
    //return !!Object.keys(this.foPayService.cartList).length;

    let enabled = false;
    //enabled = !!Object.keys(this.foPayService.cartList).length;
    const lst = this.foPayService.cartList;    
    Object.keys(lst).forEach((key, index) => {          
      if(parseInt(lst[key].status) >= 10 && parseInt(lst[key].status) <= 30){
        enabled = true;
      }            
    });
    return enabled;
  }

  getAmount(v){
    return parseFloat((''+v).replace(',','.'));
  } 

  caricaTabellaCarrelli()
  {
    this.loaderDT = true;
    this.pagamentoSelezioneSoggettoItemTableVOList = [];
    const lst = this.foPayService.cartList;    
    Object.keys(lst).forEach((key, index) => {          
      //if( parseInt(lst[key].status) >= 20 && parseInt(lst[key].status) <= 30){
      if( (parseInt(lst[key].status) >= 10 && parseInt(lst[key].status) <= 30) || parseInt(lst[key].status) == 45){
        let pagamentoSelezioneSoggettoItemTableVO = <PagamentoSelezioneSoggettoItemTableVO>{};
        pagamentoSelezioneSoggettoItemTableVO.paymentCode = lst[key].paymentCode;
        pagamentoSelezioneSoggettoItemTableVO.amount = parseFloat((''+lst[key].amount).replace(',','.'));
        pagamentoSelezioneSoggettoItemTableVO.month = lst[key].monthString();
        pagamentoSelezioneSoggettoItemTableVO.area = lst[key].area
        pagamentoSelezioneSoggettoItemTableVO.type = lst[key].type;
        pagamentoSelezioneSoggettoItemTableVO.status = parseInt(lst[key].status);
        this.pagamentoSelezioneSoggettoItemTableVOList.push(Object.assign({}, pagamentoSelezioneSoggettoItemTableVO));
      }            
    });
    this.pagamentoSelezioneSoggettoItemTableVOList = this._reduceItemTableList(this.pagamentoSelezioneSoggettoItemTableVOList);
    this.rerenderTable();
    this.loaderDT = false;    
  }

  _reduceItemTableList( pagamentoSelezioneSoggettoItemTableVO: Array<PagamentoSelezioneSoggettoItemTableVO>){
    
    //Applicabile con "target": "ES6" su tsconfig.json
    //JENKINS va errore di compilazione
    //--------------------------------------------------------
    //var paymentCodeList = [...new Set(pagamentoSelezioneSoggettoItemTableVO.map((item) => { return item.paymentCode; }, 0))];
    
    var paymentCodeList = pagamentoSelezioneSoggettoItemTableVO
                          .map(item => item.paymentCode)
                          .filter((value, index, self) => self.indexOf(value) === index)

    return paymentCodeList.map((item) => {
              var totalAmount = pagamentoSelezioneSoggettoItemTableVO
                                .filter((itemPagamentoSelezionato) => { 
                                  return itemPagamentoSelezionato.paymentCode == item; 
                                })
                                .reduce((acc, itemFilter) => { 
                                  return acc + itemFilter.amount; 
                                }, 0);
              var totalMonth: String = pagamentoSelezioneSoggettoItemTableVO
                                      .filter((itemPagamentoSelezionato) => { 
                                        return itemPagamentoSelezionato.paymentCode == item; 
                                      })
                                      .map((filterItem) => { 
                                        return filterItem.month; 
                                      })
                                      .join(', ');              
              
              /*
              var provincia = pagamentoSelezioneSoggettoItemTableVO
                              .filter((itemPagamentoSelezionato) => { 
                                return itemPagamentoSelezionato.paymentCode == item; 
                              })[0].area
              */              

              var provincia = pagamentoSelezioneSoggettoItemTableVO
                              .filter((itemPagamentoSelezionato) => { 
                                return itemPagamentoSelezionato.paymentCode == item; 
                              })
                              .map((filterItem) => { 
                                return filterItem.area; 
                              })
                              
              var provinciaUniqueList = provincia                                        
                                        .filter((value, index, self) => self.indexOf(value) === index)
                                        .join(', ');              
             
              var tipo = pagamentoSelezioneSoggettoItemTableVO
                          .filter((itemPagamentoSelezionato) => { 
                            return itemPagamentoSelezionato.paymentCode == item; 
                          })
                          .map((filterItem) => { 
                            return this._getTipoVersamentoByCode(+filterItem.type); 
                          })
                          .join(', ');

              if(tipo.indexOf("Deposito Cauzionale") >= 0){
                tipo =  pagamentoSelezioneSoggettoItemTableVO
                        .filter((itemPagamentoSelezionato) => { 
                          return itemPagamentoSelezionato.paymentCode == item; 
                        })
                        .map((filterItem) => { 
                          return this._getTipoVersamentoByCode(+filterItem.type); 
                        })[0];
                /*
                totalMonth =  pagamentoSelezioneSoggettoItemTableVO.filter((itemPagamentoSelezionato) => { 
                                return itemPagamentoSelezionato.paymentCode == item; 
                              })[0].month;
                */
                totalMonth = '';

                if(provincia != null && provincia != undefined && provincia.length > 1){
                  provinciaUniqueList = "Tutte le province";
                }
              }

              var status = pagamentoSelezioneSoggettoItemTableVO
                          .filter((itemPagamentoSelezionato) => { 
                          return itemPagamentoSelezionato.paymentCode == item; 
                          })[0].status;
                              
              var itemTable : PagamentoSelezioneSoggettoItemTableVO = <PagamentoSelezioneSoggettoItemTableVO>{};
              itemTable.paymentCode = item;
              itemTable.amount = totalAmount;
              itemTable.month = totalMonth;
              itemTable.area = provinciaUniqueList;
              itemTable.type = tipo;
              itemTable.status = status;
              return itemTable;
          });    
  }

  _getTipoVersamentoByCode(id: number){
    let tipoVersamentoArray = this.paymentTypes.filter((item) => {
      return item.idTipoVersamento == id; 
    });
    if(tipoVersamentoArray!=null && tipoVersamentoArray.length>0){
      return tipoVersamentoArray[0].denominazione;
    } else {
      return ""
    }
  }

  changeArea(event: any) {
    this.foPayService.meseSelezionatoAggiungiPagamentoCarrello = this.foPayService.cartReq.area;
  } 
  

  onSubmit() {
    if(this.utilsService.verificaIdentificativoFiscale(this.foPayService.cartReq.codiceFiscalePIva) == false) 
    {
      this.messageWarning = "C.F / P.IVA formato non valido.";
      this.showMsgWarning = true;
      this.levelMessage = this.controlPay.levelMessagge;
      return;
    }
    //Impostazione pgamento
    if(this.cartOption == 1) {
      //if(!this.foPayService.cartReq.area)
      //  this.router.navigateByUrl("home/selezione-soggetto-detail")
      //else {
        this.foPayService.retrieveSubjectPaymentFoById(this.subjectKeys[this.foPayService.cartReq.subjectName]).subscribe(
          res => {
            if(res && res.length > 1) 
                this.router.navigateByUrl("home/selezione-soggetto-detail");
              else if(res && res.length == 1) {
                this.foPayService.cartReq.idAnag = ''+res[0].idAnag;
                this.foPayService.cartReq.subjectCode = res[0].codiceAzienda;                
                //this.router.navigateByUrl("home/pagamento-fo-importo");
                this.router.navigate([`home/pagamento-fo-importo`], {queryParams: {caller: 'ins'}});
              }
          },
          err => { this.logger.error("errore " + err); });
      //}
    }
    //Carrello pagamenti
    else if(this.cartOption == 2)
      this.router.navigateByUrl("home/carrello-fo");
  }

  aggiungiPagamentoCarrello(cartItem: PagamentoSelezioneSoggettoItemTableVO){

    // if(this.foPayService.meseSelezionatoAggiungiPagamentoCarrello==null || 
    //   this.foPayService.meseSelezionatoAggiungiPagamentoCarrello==undefined)
    // {
    //   this.messageWarning = "La funzionalita' di aggiungi pagamento al carrello prevede la selezione di una provincia dall'elenco.";
    //   this.showMsgWarning = true;
    //   this.levelMessage = 'WARNING';
    //   return;
    // }

    this.foPayService.saveSubjectSelected();

    this.foPayService.retrieveSubjectPaymentFoById(this.subjectKeys[this.foPayService.cartReq.subjectName]).subscribe(
      res => {
          if(res && res.length == 1) 
          {
            this.foPayService.cartReq.idAnag = ''+res[0].idAnag;
            this.foPayService.cartReq.subjectCode = res[0].codiceAzienda;

            this.foPayService.loadCart(() => 
            {       
                const lst = this.foPayService.cartList;              
                let filterKeyList = Object.keys(lst).filter((key, index) => {return lst[key].paymentCode==cartItem.paymentCode});
                let filterCardList: PaymentStoreCartRequest[]=[];
                filterKeyList.forEach((key) => {
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
                });
                
                //Necessario introdurre nuova vairabile in quato sovrascritta  nei services successivi
                this.foPayService.meseSelezionatoAggiungiPagamentoCarrello = this.foPayService.cartReq.area;

                this.foPayService.loadCartList(filterCardList);     
                this.setFirstCart()                         
                this.router.navigate([`home/pagamento-fo-importo`], {queryParams: {caller: 'add'}});        
            });            
          }
      },
      err => { 
        this.logger.error("errore " + err); 
      });
  }

  pagaCarrello(cartItem: PagamentoSelezioneSoggettoItemTableVO){
    this.foPayService.saveSubjectSelected();
      this.foPayService.loadCart(() => {       
        const lst = this.foPayService.cartList;              
        let filterKeyList = Object.keys(lst).filter((key, index) => {return lst[key].paymentCode==cartItem.paymentCode});
        let filterCardList: PaymentStoreCartRequest[]=[];
        filterKeyList.forEach((key) => {
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
        });
        this.foPayService.loadCartList(filterCardList);     
        this.setFirstCart()         
        this.router.navigateByUrl("home/carrello-fo");          
    });    
  }
  
  setFirstCart(){
    let codiceFiscalePIva = this.foPayService.cartReq.codiceFiscalePIva;
    const lst = this.foPayService.cartList;

    this.foPayService.cartReq = lst[Object.keys(lst)[0]];
    // preserve codiceFiscalePIva from intial form
    this.foPayService.cartReq.codiceFiscalePIva = codiceFiscalePIva;
  }
  
  goBack() {
      /*
      this.foPayService.cartReq.subjectName = null;
      this.foPayService.cartReq.year = null;
      this.foPayService.cartReq.area = null;
      this.cartOption = null;
      this.foPayService.cartReq.codiceFiscalePIva = null;
      */
      this.ngOnInit(); 
  }
  
  changeCartOption(event: any){
      if(this.cartOption == 2){
        this.rerenderTable();
          this.listSubject.forEach((item, index) => {
              if(this.foPayService.cartReq.subjectName!=undefined && item.denominazione==this.foPayService.cartReq.subjectName){
                  this.foPayService.cartReq.codiceFiscalePIva = item.codiceFiscalePIva;
              }
          });          
      }      
  }

  rerenderTable(): void 
  {
    try{
        this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
          // Destroy the table first
          dtInstance.destroy();
          // Call the dtTrigger to rerender again
          this.dtTrigger.next();
        });
    } catch (err) {
        console.log(err);
    }
  }

  showAggiungiPagamento(status: number) {
    return status >= 10 && status <= 30;
  }

  getStatus(status: number) {
    switch(status) {
      case 10: /*APERTO                */
          return "Bozza";
      case 20: /*COMPLETO              */
          return "Completato";
      case 30: /*PAGAMENTO AVVIATO     */
          return "In pagamento";
      case 40: /*PAGAMENTO NOTIFICATO  */
          return "In elaborazione";
      case 45: /*CREATO AVVISO PAGAMENTO  */
          return "Generato avviso pagamento";
      case 50: /*PAGATO                */
          return "Pagato";
      case 51: /*ERRORE PAGAMENTO      */
          return "Errore pagamento";
      }
  }
}
