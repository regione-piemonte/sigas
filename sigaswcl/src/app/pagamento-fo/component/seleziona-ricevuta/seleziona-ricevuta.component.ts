import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {DestroySubscribers} from '../../../core/commons/decorator/destroy-unsubscribers';
import {Routing} from '../../../commons/routing';
import {PaymentFoService} from '../../service/pagamento-fo.service';
import {LoggerService} from '../../../core/services/logger.service';
import {SharedCacheService} from '../../../core/services/shared-cache/shared-cache.service';

@Component({
    selector: 'app-seleziona-ricevuta',
    templateUrl: './seleziona-ricevuta.component.html',
    styleUrls: ['./seleziona-ricevuta.component.scss']
})

@DestroySubscribers()
export class SelezioneRicevutaComponent implements OnInit {
    public static RICEVUTA_PAG_KEY = 'RICEVUTA_PAGAMENTO';
    private loaderInserimentoSoggettoPage: boolean;
    private noIUVFound: boolean = false;

    constructor(private router: Router, private logger: LoggerService,
                private foPayService: PaymentFoService, private sharedCacheService: SharedCacheService) {}

    ngOnInit() {
        this.loaderInserimentoSoggettoPage = true;
    }

    onSubmit() {
        this.foPayService.getPaymentCartRT(this.foPayService.searchReq.iuv).subscribe(
            res => {
                if (!(this.noIUVFound = !res.iuv)) {
                    this.sharedCacheService.put(SelezioneRicevutaComponent.RICEVUTA_PAG_KEY, res);
                    this.router.navigateByUrl('home/visualizza-ricevuta');
                }
            },
            err => {
                this.noIUVFound = true;
            });
    }

    goBack() {
        this.router.navigateByUrl(Routing.HOME);
    }
}
