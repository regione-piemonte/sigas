import { Observable, Subscription } from 'rxjs';
import { Component, OnInit, OnDestroy, Inject } from "@angular/core";
import { LoggerService } from "../../../core/services/logger.service";
import { Router, ActivatedRoute } from "@angular/router";
import { Location } from '@angular/common';
import { PaymentRedirectVO } from "../../commons/vo/payment-redirect-vo";

import { PaymentFoService } from '../../service/pagamento-fo.service';

declare var $: any;

@Component({
    selector: 'app-start-pago-pa',
    templateUrl: './start-pago-pa.html',
    styleUrls: ['./start-pago-pa.scss']
})
export class StartPagoPaComponent implements OnInit, OnDestroy {

    private paymentInfo: PaymentRedirectVO = new PaymentRedirectVO();
    private isPaymentReady: boolean = false;
    private maxRetryCounter: number = 0;

    constructor(
        private logger: LoggerService,
        private foPayService: PaymentFoService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private location: Location
    ) {
        this.maxRetryCounter = 0;
    }

    ngOnInit(): void {
        setTimeout(() => { this.getPaymentInfo() }, 1000);
    }

    ngOnDestroy(): void {
    }

    getPaymentInfo() {        
        this.foPayService.getPaymentPagoPaURLInfo().subscribe(    
            res => {
                if(res) {
                    this.paymentInfo = res;
                   
                    if(res.url){
                        //setTimeout(() => { $('#postform')[0].submit(); }, 5000);                        
                        setTimeout(() => { 
                            window.location.href = this.paymentInfo.url; 
                        }, 5000);                        
                    } else if(++this.maxRetryCounter < 40) {
                        // 20 minutes retry at most
                        setTimeout(() => { this.getPaymentInfo() }, 30000);
                    } else {
                        this.gotoPayment();
                    }
                                                               
                } else {
                    this.gotoPayment();
                }
                    
            },
            err => { 
                this.logger.error("errore " + err); 
                this.gotoPayment(); 
            });
    }
    /*
    getPaymentInfo() {
        this.foPayService.getPaymentPagoPaRedirectInfo().subscribe(        
            res => {
                if(res) {
                    this.paymentInfo = res;
                    if(this.paymentInfo.waitingUserMessage.indexOf('risulta in manutenzione') > 0){                        
                        this.isPaymentReady = true;
                    }else{
                        if(res.url)
                            setTimeout(() => { $('#postform')[0].submit(); }, 5000);
                        else if(++this.maxRetryCounter < 40) // 20 minutes retry at most
                            setTimeout(() => { this.getPaymentInfo() }, 30000);
                        else
                            this.gotoPayment();
                    }                   
                }
                else
                    this.gotoPayment();
            },
            err => { 
                this.logger.error("errore " + err); 
                this.gotoPayment(); 
            });
    }
    */

    gotoPayment() {
        this.router.navigateByUrl("home/selezione-soggetto-pagamento");
    }

    doSubmit() {        
        $('#postform')[0].submit();
    }

    goBack() {
        this.location.back();
    }

}