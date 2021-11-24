import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { Routing } from '../../../commons/routing'
import { PaymentFoService } from '../../service/pagamento-fo.service';
import { LoggerService } from "../../../core/services/logger.service";
import { SigasDialogComponent } from "../../../shared/component/sigas-dialog/sigas-dialog.component";
import { UtilityCtrlVO } from "../../commons/vo/utility-ctrl-vo";
import { SubjectVO } from "../../commons/vo/subject-vo";
declare var $: any;

@Component({
  selector: 'app-selezione-soggetto',
  templateUrl: './selezione-soggetto.component.html',
  styleUrls: ['./selezione-soggetto.component.scss']
})
@DestroySubscribers()
export class SelezioneSoggetto implements OnInit {
  
  private cartOption: number;

  private loaderInserimentoSoggettoPage: boolean;

  private yearDisabled: boolean;
  private subjectDisabled: boolean;

  public subscribers: any = {};


    paymentSuccess: boolean;
    showErrorMessage: string = null;
    
    url_chiamante_esterno: string;
    idPagamento: string;
    digest: string;
    codEsito: string;


  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private logger: LoggerService,
    private foPayService: PaymentFoService
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
  }

  private yearsList: Array<string>;
  private countiesList: Array<string>;
  private loadingPageFlag: boolean;
  private loaderAzienda: boolean;
  private controlPay: UtilityCtrlVO;
  
  private messageWarning: string;
  private showMsgWarning: boolean=false;
  private levelMessage: string;
  private listSubject : Array<SubjectVO>; 
  
  ngOnInit() {
      this.clearMes();
      if(this.idPagamento && this.digest && this.codEsito){
          console.log('Sono in redirect ppay');
          this.paymentSuccess = this.codEsito == '000';

          if(!this.paymentSuccess) {
              this.storeCodEsito();
          }
      }
      
      
    this.loadingPageFlag = true;
    this.loaderAzienda = true;

    if(this.foPayService.navigateTo != 'selezione-soggetto-pagamento') {
      this.foPayService.cartReq.subjectName = null;
      this.foPayService.cartReq.year = null;
      this.foPayService.cartReq.area = null;
      this.cartOption = null;
      this.foPayService.cartReq.codiceFiscalePIva = null;
    }
    else if(this.foPayService.cartReq.year)
      setTimeout(() => { this.changeYear(false) }, 500);

    //console.log('---FROM: '+this.foPayService.navigateTo+' YEAR:'+this.foPayService.cartReq.year + ' SUBJECT: ' + this.foPayService.cartReq.subjectName + ' AREA: ' + this.foPayService.cartReq.area)
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

    this.yearDisabled = this.subjectDisabled = !!this.foPayService.cartReq.subjectName;
 
  }
  
  storeCodEsito() {
      this.foPayService.cartReq.status = "ERROR";
      this.foPayService.cartReq.paymentCode = this.idPagamento;

      this.foPayService.savePaymentCart(() => {
          this.messageWarning = "Il pagamento non è stato completato con successo.";
          this.showMsgWarning = true;
          this.levelMessage = 'WARNING';
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

  /*
  getCurrentArea(): any {
    return this.foPayService.cartReq.subjectName && this.subjects[this.foPayService.cartReq.subjectName] || {};
  }
  */

  changeSubject(event: any) {
      this.clearMes();

      
      this.listSubject.forEach((item, index) => {
          if(this.foPayService.cartReq.subjectName!=undefined && item.denominazione==this.foPayService.cartReq.subjectName){
              this.foPayService.cartReq.codiceFiscalePIva = item.codiceFiscalePIva;
          }
      });
    this.foPayService.reloadCart(); 
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
    return !!Object.keys(this.foPayService.cartList).length;
  }


  changeArea(event: any) {
    //console.log('------------changeSubject:' + this.foPayService.cartReq.subjectName + ' - area: ' + this.foPayService.cartReq.area);
  }

  onSubmit() {
    if(this.cartOption == 1) {
      if(!this.foPayService.cartReq.area)
        this.router.navigateByUrl("home/selezione-soggetto-detail")
      else {
        this.foPayService.retrieveSubjectPaymentFoById(this.subjectKeys[this.foPayService.cartReq.subjectName]).subscribe(
          res => {
            if(res && res.length > 1) 
                this.router.navigateByUrl("home/selezione-soggetto-detail");
              else if(res && res.length == 1) {
                this.foPayService.cartReq.idAnag = ''+res[0].idAnag;
                this.foPayService.cartReq.subjectCode = res[0].codiceAzienda;
                this.router.navigateByUrl("home/pagamento-fo-importo");
              }
          },
          err => { this.logger.error("errore " + err); });
      }
    }
    else if(this.cartOption == 2)
      this.router.navigateByUrl("home/carrello-fo");
  }
  
  goBack() {
      this.foPayService.cartReq.subjectName = null;
      this.foPayService.cartReq.year = null;
      this.foPayService.cartReq.area = null;
      this.cartOption = null;
      this.foPayService.cartReq.codiceFiscalePIva = null;
//      this.router.navigateByUrl(Routing.HOME);
  }
  
  changeCartOption(event: any){
      if(this.cartOption == 2){
          this.listSubject.forEach((item, index) => {
              if(this.foPayService.cartReq.subjectName!=undefined && item.denominazione==this.foPayService.cartReq.subjectName){
                  this.foPayService.cartReq.codiceFiscalePIva = item.codiceFiscalePIva;
              }
          });
      }
      
  }

}
