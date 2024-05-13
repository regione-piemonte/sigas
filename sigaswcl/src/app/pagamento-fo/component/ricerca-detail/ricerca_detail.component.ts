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
        this.foPayService.searchPaidCartItems(this.foPayService.searchReq.year, this.foPayService.searchReq.subjectName, null, this.foPayService.searchReq.id).subscribe(
            res => {
                this.foPayService.loadCartList(res);
                if(res && res.length)
                    this.foPayService.cartReq.paymentCode = res[0].paymentCode;
            },
            err => { this.logger.error("-------errore " + err); });
                
        this.foPayService.retrieveFoPaymentSubjectDetail(this.foPayService.searchReq.subjectId, this.foPayService.searchReq.year).subscribe(
            res => {
                if(res)
                    this.subject = res;
            },
            err => { this.logger.error("errore " + err); });
    }

    goBack() {
        this.location.back();
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

    getAmount(v){
        return parseFloat((''+v).replace(',','.'));
    }

    getStatus(status) {
        return status == '51'? "ERRORE PAGAMENTO":"PAGATO";
    }
}