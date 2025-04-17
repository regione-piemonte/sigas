import { Observable, Subscription } from 'rxjs';
import { Component, OnInit, OnDestroy, Inject, AfterViewInit, Renderer2 } from "@angular/core";
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
import { PaymentStoreCartRequest } from '../../commons/request/payment-store-cart-request';

declare var $: any;

@Component({
    selector: 'app-cart-payments',
    templateUrl: './cart-payments.component.html',
    styleUrls: ['./cart-payments.component.scss']
})
@DestroySubscribers()
export class CartPaymentComponent implements OnInit, OnDestroy, AfterViewInit 
{
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
    private cartItem2delete: any;
    private deleteAllCartOk: boolean = false;
    private deleteAllCartError: string = null;
    private paymentStartError: boolean = false;
    private paymentStartedOk: boolean = false;

    private paymentNoticeError: boolean = false;
    private paymentNoticeErrorText: string;
    private viewQueryParams: string;
    private flagCancellazioneInEsecuzione: boolean = false;
    private titoloSezioneH1: string;

    constructor(
        private logger: LoggerService,
        private foPayService: PaymentFoService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private location: Location,
        //@Inject(DOCUMENT) private document: any
        private renderer: Renderer2
    ) { }

    ngAfterViewInit(): void {
        const element = this.renderer.selectRootElement('#idDivToContPrin');
        setTimeout(() => element.focus(), 0);
    }

    ngOnInit(): void {
        this.caricaTitoloSezioneH1();

        if(!this.foPayService.cartReq.year || !this.foPayService.cartReq.subjectName)
            this.router.navigateByUrl("home/selezione-soggetto-pagamento");

        this.viewQueryParams = this.activatedRoute.snapshot.queryParamMap.get('caller');

        this.foPayService.loadCart(() => { 

            //Marst
            const lst = this.foPayService.cartList;              
            //let filterKeyList = Object.keys(lst).filter((key, index) => {return lst[key].status!="45"});
            let filterKeyList = Object.keys(lst);
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
            //Marts
            
            this.setFirstCart() 
        });

        this.foPayService.retrievePaymentMethods().subscribe(
            resp => { 
                this.paymentMethods = resp; 
                if(resp.length == 1)
                    this.foPayService.cartReq.paymentType = ''+resp[0].idTipoPagamento;
                this.pageLoadingInProgress = false;
            },
            err => { 
                this.logger.error(err); 
            }
        );

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
       //VOID
    }

    tipoPagamento() {
        this.tipoPagamentoLoader = false;
    }
    
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

    dialogBoxConfirmButton() {
        if(this.dialogBoxType == 'DestroyAllCart') 
        {
            this.flagCancellazioneInEsecuzione = true;
            this.foPayService.destroyAllCart(() => { 
                this.deleteAllCartOk = true
                setTimeout(() => { 
                    this.flagCancellazioneInEsecuzione = false;
                    this.router.navigateByUrl("home/selezione-soggetto-pagamento"); 
                }, 5000);
             },
             (error) => {
                this.flagCancellazioneInEsecuzione = false;
                if(error.errorCode == 'BUSSINESS EXCEPTION')
                    this.deleteAllCartError = error.message;
                else
                    this.deleteAllCartError = 'Errore generico durante la cancellazione del carrello. Riprovare in seguito. ';
             });
        }
        if(this.dialogBoxType == 'DestroyCartItem') {
            this.flagCancellazioneInEsecuzione = true;
            this.foPayService.destroyCartItem(this.cartItem2delete, () => {
                this.flagCancellazioneInEsecuzione = false;
                if(!this.foPayService.howManyCartItems()){
                    this.router.navigateByUrl("home/selezione-soggetto-pagamento");                    
                }                    
            })
        }
    }

    goBack() {
        //this.foPayService.addCurrentCartItemToList();
        //this.router.navigateByUrl("home/pagamento-fo-importo");
                
        if(parseInt(this.foPayService.cartReq.status,10) >= 40){
            //stato 40 -> IN ELABORAZIONE
            return this.router.navigateByUrl("home/selezione-soggetto-pagamento");
        }

        if(this.viewQueryParams==null || this.viewQueryParams==undefined){
            //this.router.navigate([`home/selezione-soggetto-pagamento`], {queryParams: {caller: 'det'}});            
           return  this.router.navigateByUrl("home/selezione-soggetto-pagamento");
        } else {
            return this.router.navigate([`home/pagamento-fo-importo`], {queryParams: {caller: 'add'}});
        }
        
    }

    saveCart() {
        this.foPayService.storePaymentInfoToAllCartItems().subscribe(
            res => {
                this.foPayService.cartReq.status="20";
                this.flgCartSaved = true;
            },
            err => { 
                this.logger.error("errore " + err); 
            }
        );
    }
    
    payCart() {
        //-----------------------
        //SOLO PER BYPASS
        //-----------------------
        //this.router.navigateByUrl("home/start-ppay");
        //this.paymentStartedOk = true;
        //-----------------------

        this.pageLoadingInProgress = true;
        if( this.foPayService.cartReq.status == "GENERATO AVVISO PAGAMENTO"){
            this.router.navigateByUrl("home/start-ppay");
            this.paymentStartedOk = true;
            this.pageLoadingInProgress = false;
        } else {
            this.deleteAllCartOk = false
            this.deleteAllCartError = null;
            this.paymentNoticeError = false;
            this.paymentNoticeErrorText = "";
        
            this.paymentStartError = this.paymentStartedOk = false;
            this.foPayService.startCartPayment().subscribe(
                res => {
                    this.router.navigateByUrl("home/start-ppay");
                    this.paymentStartedOk = true;
                },
                err => { 
                    this.paymentStartError = true;
                    this.logger.error("errore " + err);
                    this.pageLoadingInProgress = false; 
                });
            //this.pageLoadingInProgress = false;
        }   
                
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
        if(this.flagCancellazioneInEsecuzione){
            return false;
        } else {
            return !status 
                   || status === 'BOZZA'
                   || parseInt(status,10) <= 20 /*COMPLETO*/
                   || parseInt(status,10) >= 51 /*ERRORE PAGAMENTO*/;
        }        
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
                return "IN ELABORAZIONE";
            case 45: /*CREATO AVVISO PAGAMENTO  */
                return "GENERATO AVVISO PAGAMENTO";
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

    disabilitaDownloadBollettino(): boolean {
        /*
        if(this.foPayService.cartReq.iuv==null || this.foPayService.cartReq.iuv==undefined || this.foPayService.cartReq.iuv==""){
            return true;
        }
        */
        let statusNumeral = parseInt(this.foPayService.cartReq.status,10);
        /*
        if(statusNumeral < 30 || statusNumeral > 40){
            return true;
        }
        */
        if(statusNumeral > 45){
            return true;
        }
        return false;        
    }

    downloadAvvisoPagamento(){
        let esecuzioneOK: String = "000";
        this.paymentStartError = false;
        this.deleteAllCartOk = false
        this.deleteAllCartError = null;
        this.paymentNoticeError = false;
        this.paymentNoticeErrorText = "";
        this.pageLoadingInProgress = true;        
        if(this.foPayService.cartReq.iuv!=null && this.foPayService.cartReq.iuv!=undefined && this.foPayService.cartReq.iuv!=""){
            this.foPayService.getPaymentNotice(this.foPayService.cartReq.iuv).subscribe(
                res => {                    
                    this.paymentNoticeError = false;
                    this.paymentNoticeErrorText = "";

                    saveAs(res, "avviso_pagamento_" + this.foPayService.cartReq.subjectName + ".pdf");
                    this.pageLoadingInProgress = false;                   
                },
                err => { 
                    this.logger.error("errore " + err);
                    this.paymentNoticeError = true;
                    this.pageLoadingInProgress = false;
                    //
                }
            );
        } else {
            this.foPayService.generaPaymentNotice().subscribe(
                res => {
                    this.paymentNoticeError = false;
                    this.paymentNoticeErrorText = "";                    
                    saveAs(res, "avviso_pagamento_" + this.foPayService.cartReq.subjectName + ".pdf");
                    
                    this.foPayService.searchCartItems(this.foPayService.searchReq.year, this.foPayService.searchReq.subjectName,null).subscribe(
                        res => {
                            //this.foPayService.loadCartList(res);
                            //Marst
                            let filterList: PaymentStoreCartRequest[];
                            filterList = res.filter((item) => {
                                return item.paymentCode == this.foPayService.cartReq.paymentCode
                            });                            
                            this.foPayService.loadCartList(filterList);
                            //Marts
                            this.setFirstCart();
                            this.pageLoadingInProgress = false;
                        },
                        err => {
                            this.logger.error("-------errore " + err);
                            this.pageLoadingInProgress = false;
                        }
                    )
                    
                },
                err => { 
                    this.logger.error("errore " + err); 
                }
            )                        
        }           
    }

    private caricaTitoloSezioneH1(){    
        if(this.foPayService.cartReq.type=="5" || this.foPayService.cartReq.type=="6")
        {
            this.titoloSezioneH1 = "Carrello pagamenti anno di competenza " + this.foPayService.cartReq.year + " " +
            "della denominazione azienda: " + this.foPayService.cartReq.subjectName; 
           
        } else {
            this.titoloSezioneH1 = "Carrello pagamenti mese di competenza " + this.getMonths() + " " + this.foPayService.cartReq.year + " " +
            "della denominazione azienda: " + this.foPayService.cartReq.subjectName; 
        }        
    }
}