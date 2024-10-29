import {Component, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';
import {LoggerService} from '../../../core/services/logger.service';
import {UserService} from '../../../core/services/user.service';
import {DatiTemplateCompilatiVO} from '../../../commons/vo/template/dati-template-compilati-vo';
import {DatiTemplateVO} from '../../../commons/vo/template/dati-template-vo';
import {NgForm} from '@angular/forms';
import {SharedTemplateIntestazioneComponent} from '../../../shared-template/components/shared-template-intestazione/shared-template-intestazione.component';
import {DatePipe} from '@angular/common';
import {ProvinceVO} from '../../../commons/vo/luoghi-vo/province-vo';
import {ComuniVO} from '../../../commons/vo/luoghi-vo/comuni-vo';
import {LuoghiService} from '../../../shared/service/luoghi-service';

@Component({
    selector: 'template-lettera-risposta',
    templateUrl: './template-lettera-risposta.component.html'
})
export class TemplateLetteraRispostaComponent implements OnInit, OnDestroy {

    public subscribers: any = {};
    public funzionario: string;
    public isAnteprima: boolean;
    public isStampa: boolean;
    formIntestazioneValid: boolean;
    public datiCompilati: DatiTemplateCompilatiVO = new DatiTemplateCompilatiVO();

    public signor: string;
    comune: ComuniVO;
    provincia: ProvinceVO;

    loadCmnProv: boolean;
    @Input()
    data: DatiTemplateVO;
    @Output()
    formValid: EventEmitter<boolean> = new EventEmitter<boolean>();
    @ViewChild(SharedTemplateIntestazioneComponent)
    intestazione: SharedTemplateIntestazioneComponent;
    private provinceModel: Array<ProvinceVO>;
    private comuniModel: Array<ComuniVO>;
    @ViewChild('formTemplate')
    private formTemplate: NgForm;

    constructor(
        private logger: LoggerService,
        private userService: UserService,
        private luoghiService: LuoghiService
    ) {
    }

    ngOnInit(): void {
        console.log(this.data);
        this.logger.info(TemplateLetteraRispostaComponent.name);
        this.loadProvinceComuni();
        this.subscribers.userProfile = this.userService.profilatura$.subscribe(user => {
            if (user != null) {
                this.funzionario = user.nome + ' ' + user.cognome;
            }
        });
        this.subscribers.form = this.formTemplate.valueChanges.subscribe(data => {
            this.formValid.next(this.formTemplate.valid && this.formIntestazioneValid);
        });

        const datepipe: DatePipe = new DatePipe('en-US');
        let formattedDateProtocollazione = datepipe.transform(this.data.dataProtocollazione, 'dd/MM/yyyy');

        this.data.anagraficaSoggettoVO.denominazione = 'Spett.le ' + this.data.anagraficaSoggettoVO.denominazione;
        this.datiCompilati.intestazione = this.data.anagraficaSoggettoVO.denominazione;
        this.datiCompilati.indirizzo = this.data.anagraficaSoggettoVO.indirizzo;
        this.datiCompilati.oggetto = 'Riscontro alla Vostra prot. N ' + this.data.numeroProtocollo + ' del ' + formattedDateProtocollazione;
    }

    setAnteprima(flag: boolean) {
        this.isAnteprima = flag;
        this.intestazione.setAnteprima(flag);
    }

    setStampa(flag: boolean) {
        this.isStampa = flag;
        this.intestazione.setAnteprima(flag);
    }

    getDatiCompilati(): DatiTemplateCompilatiVO {
        return this.datiCompilati;
    }

    setDatiCompilati(dati: DatiTemplateCompilatiVO) {
        this.datiCompilati = dati;
    }

    setFormIntestazioneValid(event: boolean) {
        this.formIntestazioneValid = event;
        this.formValid.next(this.formTemplate.valid && this.formIntestazioneValid);
    }

    loadProvinceComuni() {
        this.logger.info('carico le province');
        this.subscribers.province = this.luoghiService.getAllProvince().subscribe(data => {
            this.provinceModel = data;
            this.provinceModel.push(new ProvinceVO(-1, 'Nessuna Provincia Selezionata', ''));
            this.provinceModel.map(prov => {
                if (prov.id == this.data.anagraficaSoggettoVO.idProvincia) {
                    this.provincia = prov;
                }
            });
            this.loadComuni(this.data.anagraficaSoggettoVO.idProvincia);
        }, err => {
            this.logger.error('Errore nel recupero delle province');
        });
    }

    loadComuni(id: number) {
        this.logger.info('carico i comuni della provincia con id:' + id);
        this.loadCmnProv = true;
        if (id != null && id != -1) {
            this.subscribers.comuniByIdProvincia = this.luoghiService.getComuneByIdProvincia(id).subscribe(data => {
                this.comuniModel = data;
                this.comuniModel.map(com => {
                    if (com.id == this.data.anagraficaSoggettoVO.idComune) {
                        this.comune = com;
                    }
                });
                this.loadCmnProv = false;
                this.datiCompilati.capComuneProvinvcia = this.comune.denominazione + ', (' + this.provincia.sigla + ')';

            }, err => {
                this.logger.error('Errore nel recupero dei comuni');
                this.loadCmnProv = false;
            });
        } else {
            this.comuniModel = new Array<ComuniVO>();
            this.loadCmnProv = false;
        }
    }

    ngOnDestroy(): void {
        this.logger.info(TemplateLetteraRispostaComponent.name);
    }
}
