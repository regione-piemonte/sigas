import { Observable, Subscription } from 'rxjs';
import { Component, OnInit, OnDestroy, Inject } from "@angular/core";
//import { RiepilogoPagamentoVO } from "../../commons/vo/riepilogo-pagamento-vo";
import { LoggerService } from "../../../core/services/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Location } from '@angular/common';
import { PaymentMethodVO } from "../../commons/vo/payment-method-vo";
//import { TipoVersanteVO } from "../../commons/vo/tipo-versante-vo";
//import { DOCUMENT } from '@angular/platform-browser';
import { saveAs } from "file-saver";
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
//import { RiepilogoPagamentoRequest } from "../../commons/request/riepilogo-pagamento-request";

import { PaymentFoService } from '../../service/pagamento-fo.service';
import { UtilityCtrlVO } from "../../commons/vo/utility-ctrl-vo";

declare var $: any;

@Component({
    selector: 'app-cart-payments',
    templateUrl: './cart-payments.component.html',
    styleUrls: ['./cart-payments.component.scss']
})
@DestroySubscribers()
export class CartPaymentComponent implements OnInit, OnDestroy {

    private dialogBoxType: string;
    private dialogBoxTitle: string;
    private dialogBoxMessage: string;

    private pageLoadingInProgress: boolean;

    confermaEmailModel: string;
    paymentMethods: Array<PaymentMethodVO>;
    
    tipoPagamentoLoader: boolean;
    flgCartSaved: boolean = false;

    msgAlertSuccess: string;
    causale: string;

    public subscribers: any = {};

    private msgCart: UtilityCtrlVO;

    private message: string;
    private showMsg: boolean;
    private levelMessage: string;


    constructor(
        private logger: LoggerService,
        private foPayService: PaymentFoService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private location: Location,
        //@Inject(DOCUMENT) private document: any
    ) { }

    ngOnInit(): void {
        if(!this.foPayService.cartReq.year || !this.foPayService.cartReq.subjectName)
            this.router.navigateByUrl("home/selezione-soggetto-pagamento");

//if(true){ this.foPayService.cartReq.year = '2021';this.foPayService.cartReq.subjectName = '19 novembre - 11111111111'; }
        this.foPayService.loadCart(() => { this.setFirstCart() } );



        this.foPayService.retrievePaymentMethods().subscribe(
            resp => { 
                this.paymentMethods = resp; 
                if(resp.length == 1)
                    this.foPayService.cartReq.paymentType = ''+resp[0].idTipoPagamento;
                this.pageLoadingInProgress = false;
            },
            err => { this.logger.error(err); });

        this.confermaEmailModel = this.foPayService.cartReq.email;
        
        
        this.foPayService.retriveMessageAndParameters("", "carrelloNotificato")
        .subscribe(res => {
          this.msgCart = res;
          if(parseInt(this.foPayService.cartReq.status,10)==40){
              this.message = this.msgCart.message;
              this.levelMessage = this.msgCart.levelMessagge;
              this.showMsg = true;
          }
        }, err => {
          this.logger.error("errore ");
      });
    }

    ngAfterViewChecked() {
        /*
        let d = new Date();
        d.setDate(d.getDate() - 1);
        if ($('.input-group.date').length) {
            $('.input-group.date').each(function (index) {
                $(this).datetimepicker({
                    format: 'DD/MM/YYYY',
                    minDate: d,
                    disabledDates: [d]
                });
            });
        }
        */
    }

    tipoPagamento() {
        this.tipoPagamentoLoader = false;
    }

    private cartItem2delete: any;
    dialogBoxShowDestroyCartItem(id:number) {
        this.cartItem2delete = id;
        this.dialogBoxType = 'DestroyCartItem';
        this.dialogBoxTitle = 'Carrello';
        this.dialogBoxMessage = 'Si vuole eliminare il pagamento selezionato dal carrello?';
    }

    dialogBoxShowDestroyAllCart() {
        this.dialogBoxType = 'DestroyAllCart';
        this.dialogBoxTitle = 'Carrello';
        this.dialogBoxMessage = 'Si vuole eliminare tutti i pagamenti aggiunti al carrello?';
    }

    dialogBoxDismissButton() {
    }

    private deleteAllCartOk: boolean = false;
    private deleteAllCartError: string = null;
    dialogBoxConfirmButton() {
        if(this.dialogBoxType == 'DestroyAllCart') {
            this.foPayService.destroyAllCart(() => { 
                this.deleteAllCartOk = true

                setTimeout(() => { this.router.navigateByUrl("home/selezione-soggetto-pagamento"); }, 5000);
             },
             (error) => {
                if(error.errorCode == 'BUSSINESS EXCEPTION')
                    this.deleteAllCartError = error.message;
                else
                    this.deleteAllCartError = 'Errore generico durante la cancellazione del carrello. Riprovare in seguito. ';
             });
        }

        if(this.dialogBoxType == 'DestroyCartItem') {
            this.foPayService.destroyCartItem(this.cartItem2delete, () => {
                    if(!this.foPayService.howManyCartItems())
                        this.router.navigateByUrl("home/selezione-soggetto-pagamento");
                })
        }
    }

    goBack() {
        this.foPayService.addCurrentCartItemToList();
        this.router.navigateByUrl("home/pagamento-fo-importo");
    }

    saveCart() {
        this.foPayService.storePaymentInfoToAllCartItems().subscribe(
            res => {
                this.foPayService.cartReq.status="20";
                this.flgCartSaved = true;
            },
            err => { 
                this.logger.error("errore " + err); 
            });
    }

    private paymentStartError: boolean = false;
    private paymentStartedOk: boolean = false;
    payCart() {
        //-----------------------
        //SOLO PER BYPASS
        //-----------------------
        //this.router.navigateByUrl("home/start-ppay");
        //this.paymentStartedOk = true;
        //-----------------------
       
        this.paymentStartError = this.paymentStartedOk = false;
        this.foPayService.startCartPayment().subscribe(
            res => {
                this.router.navigateByUrl("home/start-ppay");
                this.paymentStartedOk = true;
            },
            err => { 
                this.paymentStartError = true;
                this.logger.error("errore " + err); 
            });
        this.pageLoadingInProgress = false;
        
        
    }

    downloadExcelCart() {
        this.foPayService.saveCart();
    }    

    ngOnDestroy(): void {
    }

    getTotalAmount(){
        let amounts:number = 0;
        const lst = this.foPayService.cartList;
        Object.keys(lst).forEach((key, index) => {
            amounts += parseFloat((''+lst[key].amount).replace(',','.'));
        });

        return amounts;
    }

    getAmount(val) {
        return parseFloat((''+val).replace(',','.'));
    }

    setFirstCart(){
        let codiceFiscalePIva = this.foPayService.cartReq.codiceFiscalePIva;
        const lst = this.foPayService.cartList;

        this.foPayService.cartReq = lst[Object.keys(lst)[0]];
        // preserve codiceFiscalePIva from intial form
        this.foPayService.cartReq.codiceFiscalePIva = codiceFiscalePIva;
    }

    getMonths(): string {
        let resMonths = '';
        const cartItems = this.foPayService.cartList;
        const months = this.foPayService.cartReq.months;

        Object.keys(months).forEach(keyMonth => {
            Object.keys(cartItems).forEach(keyCart => {
                if(cartItems[keyCart].monthString() == months[keyMonth])
                    if(resMonths.indexOf(cartItems[keyCart].monthString()) == -1)
                        resMonths += (resMonths?' - ':'') + months[keyMonth];
            });
        });

        return resMonths;
    }

    isDeletable(): boolean {
        let status = this.foPayService.cartReq.status;

        return !status 
            || status === 'BOZZA'
            || parseInt(status,10) <= 20 /*COMPLETO*/
            || parseInt(status,10) >= 51 /*ERRORE PAGAMENTO*/;
    }

    getStatus(): String {
            let status = this.foPayService.cartReq.status;
            if(!status) return "";
            
            switch(parseInt(status,10)) {
            case 10: /*APERTO                */
                return "BOZZA";
            case 20: /*COMPLETO              */
                return "COMPLETO";
            case 30: /*PAGAMENTO AVVIATO     */
                return "IN PAGAMENTO";
            case 40: /*PAGAMENTO NOTIFICATO  */
                return "DA PAGARE";
            case 50: /*PAGATO                */
                return "PAGATO";
            case 51: /*ERRORE PAGAMENTO      */
                return "ERRORE PAGAMENTO";
            }
            
            return this.foPayService.cartReq.status;
        
    }

    checkMails(): boolean {
        return this.confermaEmailModel == this.foPayService.cartReq.email;
    }
}