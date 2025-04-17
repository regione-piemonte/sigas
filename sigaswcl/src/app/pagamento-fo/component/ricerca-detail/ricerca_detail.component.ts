import { Observable, Subscription } from 'rxjs';
import { Component, OnInit, OnDestroy, Inject, AfterViewInit, Renderer2 } from "@angular/core";
import { LoggerService } from "../../../core/services/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Location } from '@angular/common';
import { PaymentMethodVO } from "../../commons/vo/payment-method-vo";
import { saveAs } from "file-saver";
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { SubjectVO } from "../../commons/vo/subject-vo";

import { PaymentFoService } from '../../service/pagamento-fo.service';
import { TipoVersamentiVO } from "../../../commons/vo/tipo-versamenti-vo";
import { PaymentStoreCartRequest } from '../../commons/request/payment-store-cart-request';

declare var $: any;

@Component({
    selector: 'app-search-detail',
    templateUrl: './ricerca_detail.component.html',
    styleUrls: ['./ricerca_detail.component.scss']
})
@DestroySubscribers()
export class SearchDetailComponent implements OnInit, OnDestroy, AfterViewInit {

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
    private subject: SubjectVO;
    private amountDetail: any = { 'totalToPay': 833.06, 'adjustment': 137 };

    execDownloadBolletino: boolean = false;
    execDownloadBolletinoError: boolean = false;
    execDownloadExcel: boolean = false;
    
    private paymentTypes: Array<TipoVersamentiVO>;

    public dataSourceTableCartList: Array<{ [key: string]: PaymentStoreCartRequest }> = [];

    constructor(
        private logger: LoggerService,
        private foPayService: PaymentFoService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private location: Location,
        private renderer: Renderer2
    ) { }

    ngAfterViewInit(): void {
        const element = this.renderer.selectRootElement('#idDivToContPrin');
        setTimeout(() => element.focus(), 0);
    }

    ngOnInit(): void {        
        this.pageLoadingInProgress = true;

        this.foPayService.retrievePaymentTypes().subscribe(resp => 
        { 
            this.paymentTypes = resp;           
        },
        err => { 
            this.logger.error(err); 
    
        });

        setTimeout(() => {
            this.foPayService.searchPaidCartItems(this.foPayService.searchReq.year, this.foPayService.searchReq.subjectName, null, this.foPayService.searchReq.id).subscribe(
                res => {
                    this.foPayService.loadCartList(res);
                    if(res && res.length){
                        this.foPayService.cartReq.paymentCode = res[0].paymentCode;
                        this.setFirstCart()
                    }
                    if(this.foPayService.cartReq.type !="5" && this.foPayService.cartReq.type !="6"){
                        Object.keys(this.foPayService.cartList).forEach((key, index) => {          
                            let cartItem:PaymentStoreCartRequest = new PaymentStoreCartRequest(
                                this.foPayService.cartList[key].id,
                                this.foPayService.cartList[key].amount,
                                this.foPayService.cartList[key].year,
                                this.foPayService.cartList[key].area,
                                this.foPayService.cartList[key].subjectName,
                                this.foPayService.cartList[key].idAnag,
                                this.foPayService.cartList[key].subjectCode,
                                this.foPayService.cartList[key].status,
                                this.foPayService.cartList[key].paymentCode,
                                this.foPayService.cartList[key].paymentType,
                                this.foPayService.cartList[key].currentDate,
                                this.foPayService.cartList[key].payDate,
                                this.foPayService.cartList[key].email,
                                (this.foPayService.cartList[key].month!=null)?this.foPayService.cartList[key].month.toString():null,
                                this.foPayService.cartList[key].type,
                                0,
                                this.foPayService.cartList[key].codiceFiscalePIva,
                                this.foPayService.cartList[key].iuv)
                                this.dataSourceTableCartList[cartItem.cartKey] = cartItem;                                        
                        });
                    } else {
                        var elencoProvince: String = "";
                        var sommaImportoListaDepositiCauzionali:number = 0;                       
                        Object.keys(this.foPayService.cartList).forEach((key, index) => {
                            elencoProvince = elencoProvince + this.foPayService.cartList[key].area;
                            sommaImportoListaDepositiCauzionali += parseFloat((''+this.foPayService.cartList[key].amount).replace(',','.'));
                            if(index == 0){
                                let cartItem:PaymentStoreCartRequest = new PaymentStoreCartRequest(
                                    this.foPayService.cartList[key].id,
                                    null,
                                    this.foPayService.cartList[key].year,
                                    null,
                                    this.foPayService.cartList[key].subjectName,
                                    this.foPayService.cartList[key].idAnag,
                                    this.foPayService.cartList[key].subjectCode,
                                    this.foPayService.cartList[key].status,
                                    this.foPayService.cartList[key].paymentCode,
                                    this.foPayService.cartList[key].paymentType,
                                    this.foPayService.cartList[key].currentDate,
                                    this.foPayService.cartList[key].payDate,
                                    this.foPayService.cartList[key].email,
                                    null,
                                    this.foPayService.cartList[key].type,
                                    0,
                                    this.foPayService.cartList[key].codiceFiscalePIva,
                                    this.foPayService.cartList[key].iuv)
                                    this.dataSourceTableCartList[cartItem.cartKey] = cartItem;
                            }                                                                              
                        });
                        if(elencoProvince.length > 2){
                            elencoProvince = "Tutte le province";
                        }
                        Object.keys(this.dataSourceTableCartList).forEach((key, index) => {
                            this.dataSourceTableCartList[key].area = elencoProvince;
                            this.dataSourceTableCartList[key].amount = sommaImportoListaDepositiCauzionali.toString();
                        });
                    }                                      
                    this.pageLoadingInProgress = false;
                },
                err => { this.logger.error("-------errore " + err); });
                    
            this.foPayService.retrieveFoPaymentSubjectDetail(this.foPayService.searchReq.subjectId, this.foPayService.searchReq.year).subscribe(
                res => {
                    if(res)
                        this.subject = res;
                },
                err => { this.logger.error("errore " + err);
                
            });            
        }, 1000);        
    }

    goBack() {
        this.location.back();
    }

    downloadExcelCart() {
        this.execDownloadExcel = true;
        this.foPayService.saveCart();
        this.execDownloadExcel = false;
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

    getAmount(v){
        if(v=='' || v==null || v==undefined){
            return '';
        }        
        return parseFloat((''+v).replace(',','.'));
    }    

    getStatus(status): String {        
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
        return status;    
    }

    disabilitaDownloadBollettino(): boolean {
        let disabilita: boolean = true;
        const lst = this.foPayService.cartList;
        Object.keys(lst).forEach((key, index) => {
            if(parseInt(lst[key].status) == 45){
                disabilita = false;
            }
        })        
        return disabilita;        
    }

    setFirstCart(){
        let codiceFiscalePIva = this.foPayService.cartReq.codiceFiscalePIva;
        const lst = this.foPayService.cartList;

        this.foPayService.cartReq = lst[Object.keys(lst)[0]];
        // preserve codiceFiscalePIva from intial form
        this.foPayService.cartReq.codiceFiscalePIva = codiceFiscalePIva;
    }

    downloadAvvisoPagamento(){
        let esecuzioneOK: String = "000";
        this.execDownloadBolletino = true;        
              
        if(this.foPayService.cartReq.iuv!=null && this.foPayService.cartReq.iuv!=undefined && this.foPayService.cartReq.iuv!=""){
            this.foPayService.getPaymentNotice(this.foPayService.cartReq.iuv).subscribe(
                res => {                                        
                    saveAs(res, "avviso_pagamento_" + this.foPayService.cartReq.subjectName + ".pdf");
                    this.execDownloadBolletino = false;
                    this.execDownloadBolletinoError = false;                   
                },
                err => { 
                    this.logger.error("errore " + err);                    
                    this.execDownloadBolletino = false;
                    this.execDownloadBolletinoError = true;                    
                }
            );
        }          
    }
    
    getTipoVersamentoByCode(id: number){
        var marst = this.foPayService.cartList;
        let tipoVersamentoArray = this.paymentTypes.filter((item) => {
          return item.idTipoVersamento == id; 
        });
        if(tipoVersamentoArray!=null && tipoVersamentoArray.length>0){
          return tipoVersamentoArray[0].denominazione;
        } else {
          return ""
        }   
    }
}