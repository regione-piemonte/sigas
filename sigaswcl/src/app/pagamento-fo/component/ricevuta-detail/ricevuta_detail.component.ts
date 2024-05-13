import {AfterViewInit, Component, OnDestroy, OnInit, Renderer2} from '@angular/core';
import {LoggerService} from '../../../core/services/logger.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Location} from '@angular/common';
import {DestroySubscribers} from '../../../core/commons/decorator/destroy-unsubscribers';
import {PaymentRTInfoVO} from '../../commons/vo/payment-rt-vo';

import {PaymentFoService} from '../../service/pagamento-fo.service';
import {SharedCacheService} from '../../../core/services/shared-cache/shared-cache.service';
import {SelezioneRicevutaComponent} from '../seleziona-ricevuta/seleziona-ricevuta.component';

@Component({
    selector: 'app-ricevuta-detail',
    templateUrl: './ricevuta_detail.component.html',
    styleUrls: ['./ricevuta_detail.component.scss']
})
@DestroySubscribers()
export class RicevutaDetailComponent implements OnInit, OnDestroy, AfterViewInit {

    rtInfo: PaymentRTInfoVO;

    constructor(
        private logger: LoggerService,
        private foPayService: PaymentFoService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private location: Location,
        private sharedCacheService: SharedCacheService,
        private renderer: Renderer2
    ) {
    }

    ngAfterViewInit(): void {
        const element = this.renderer.selectRootElement('#idDivToContPrin');
        setTimeout(() => element.focus(), 0);
    }

    ngOnInit(): void {
        this.rtInfo = this.sharedCacheService.get(SelezioneRicevutaComponent.RICEVUTA_PAG_KEY);
    }

    goBack() {
        this.sharedCacheService.delete(SelezioneRicevutaComponent.RICEVUTA_PAG_KEY);
        this.location.back();
    }

    ngOnDestroy(): void {
    }
}
