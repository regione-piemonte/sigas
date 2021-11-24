import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {LoggerService} from '../../../core/services/logger.service';
import {DatiTemplateVO} from '../../../commons/vo/template/dati-template-vo';
import {DatiTemplateCompilatiVO} from '../../../commons/vo/template/dati-template-compilati-vo';

@Component({
    selector: 'shared-template-intestazione',
    templateUrl: './shared-template-intestazione.component.html',
    styleUrls: ['./shared-template-intestazione.component.scss']
})
export class SharedTemplateIntestazioneComponent implements OnInit {

    public subscribers: any = {};
    public isAnteprima: boolean;
    public dataOdierna: string;
    public viewMailTributiField: boolean;

    @Input()
    datiTemplate: DatiTemplateVO;

    @Input()
    datiCompilati: DatiTemplateCompilatiVO;

    @Output()
    formValid: EventEmitter<boolean> = new EventEmitter<boolean>();

    constructor(
        private logger: LoggerService,
    ) {
    }

    ngOnInit(): void {
        this.logger.info(SharedTemplateIntestazioneComponent.name);
        this.dataOdierna = new Date().toLocaleDateString();
        this.formValid.next(true);
        if (this.datiTemplate.mailSettoreTributi != null) {
            this.viewMailTributiField = true;
        } else {
            this.viewMailTributiField = false;
        }
    }

    setAnteprima(flag: boolean) {
        this.isAnteprima = flag;
    }
}
