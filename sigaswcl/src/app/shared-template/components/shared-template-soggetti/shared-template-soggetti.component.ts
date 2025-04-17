import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {LoggerService} from '../../../core/services/logger.service';
import {AnagraficaSoggettoVO} from '../../../commons/vo/soggetti-vo';
import {ComuniVO} from '../../../commons/vo/luoghi-vo/comuni-vo';
import {ProvinceVO} from '../../../commons/vo/luoghi-vo/province-vo';
import {LuoghiService} from '../../../shared/service/luoghi-service';
import {DatiTemplateCompilatiVO} from '../../../commons/vo/template/dati-template-compilati-vo';

@Component({
    selector: 'shared-template-soggetti',
    templateUrl: './shared-template-soggetti.component.html'
})
export class SharedTemplateSoggettiComponent implements OnInit, OnDestroy {

    @Input()
    anagraficaSoggettoVO: AnagraficaSoggettoVO;

    @Input()
    datiCompilati: DatiTemplateCompilatiVO;

    @Input()
    isAnteprima: boolean;

    @Input()
    isStampa: boolean;

    @Output()
    formValid: EventEmitter<boolean> = new EventEmitter<boolean>();

    public signor: string;
    comune: ComuniVO;
    provincia: ProvinceVO;
    public subscribers: any = {};
    loadCmnProv: boolean;
    private provinceModel: Array<ProvinceVO>;
    private comuniModel: Array<ComuniVO>;

    constructor(
        private logger: LoggerService,
        private luoghiService: LuoghiService
    ) {
    }

    ngOnInit(): void {
        this.logger.info(SharedTemplateSoggettiComponent.name);
        this.loadProvinceComuni();
        this.anagraficaSoggettoVO.denominazione = 'Spett.le ' + this.anagraficaSoggettoVO.denominazione;
        this.datiCompilati.intestazione = this.anagraficaSoggettoVO.denominazione;
        this.datiCompilati.indirizzo = this.anagraficaSoggettoVO.indirizzo;
    }

    loadProvinceComuni() {
        this.logger.info('carico le province');
        this.subscribers.province = this.luoghiService.getAllProvince().subscribe(data => {
            this.provinceModel = data;
            this.provinceModel.push(new ProvinceVO(-1, 'Nessuna Provincia Selezionata', ''));
            this.provinceModel.forEach(prov => {
                if (prov.id == this.anagraficaSoggettoVO.idProvincia) {
                    this.provincia = prov;
                }
            });
            this.loadComuni(this.anagraficaSoggettoVO.idProvincia);
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
                this.comuniModel.forEach(com => {
                    if (com.id == this.anagraficaSoggettoVO.idComune) {
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
        this.logger.info(SharedTemplateSoggettiComponent.name);
    }
}
