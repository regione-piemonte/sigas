import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {DestroySubscribers} from '../../../core/commons/decorator/destroy-unsubscribers';
import {UserService} from '../../../core/services/user.service';
import {LoggerService} from '../../../core/services/logger.service';
import {Roles} from '../../../core/commons/commons-core';
import {SharedCacheService} from '../../../core/services/shared-cache/shared-cache.service';


@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
@DestroySubscribers()
export class HomeComponent implements OnInit {

    public subscribers: any = {};
    public loader: boolean;
    public homepage: string;

    constructor(private router: Router, private userService: UserService,
                private loggerService: LoggerService,
                private sharedCache: SharedCacheService) {
    }

    ngOnInit() {
        this.loader = true;
        this.subscribers.funzionarioVo = this.userService.profilatura$
            .subscribe((data) => {
                    this.loader = false;
                },
                (err) => {
                    this.loggerService.error('ERRORE IN FASE DI RICEZIONE DELLA PROFILATURA');
                });
        this.sharedCache.clean();
    }

}
