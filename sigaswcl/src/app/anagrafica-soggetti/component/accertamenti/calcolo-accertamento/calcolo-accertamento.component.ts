import { AllarmiSoggettoVO } from './../../../../commons/vo/allarmi-soggetto-vo';
import { AllarmiVO } from './../../../../commons/vo/allarmi-vo';
import { ProvinceVO } from './../../../../commons/vo/luoghi-vo/province-vo';
import { LuoghiService } from './../../../../shared/service/luoghi-service';
import { saveAs } from 'file-saver';
import { TipoTassiVO } from './../../../../commons/vo/tipo-tassi-vo';
import { AnagraficaSoggettiService } from './../../../service/anagrafica-soggetti.service';
import { TassiService } from './../../../../tassi/service/tassi.service';
import { Component, OnInit, Input, Output, EventEmitter, OnDestroy } from '@angular/core';
import { VersamentiPrVO } from '../../../../commons/vo/versamenti-pr-vo';
import { ISubscription, Subscription } from 'rxjs/Subscription';
import { LoggerService } from '../../../../core/services/logger.service';

import { TassiVO } from 'src/app/commons/vo/tassi-vo';
import * as moment from 'moment';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateCustomParserFormatter} from '../../../../commons/class/dateformat';


@Component({
  selector: 'app-calcolo-accertamento',
  templateUrl: './calcolo-accertamento.component.html',
  styleUrls: ['./calcolo-accertamento.component.scss'],
  providers: [
              {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter}
          ]
})
export class CalcoloAccertamentoComponent implements OnInit {
  @Input() accertamenti: Array<VersamentiPrVO>;
  @Output() back = new EventEmitter<boolean>();
  @Output()accerVuoto = new EventEmitter< Array<VersamentiPrVO>>();
  dataAccertamento;
  subscription: Subscription;
  showAccertementiCalcolati = false;
  elencoTipoTassi: Array<TipoTassiVO>;
  elencoTassi: Array<TassiVO>;
  response: string;
  totaleAccertamenti = 0;
  loaderExcel: boolean;
  provinceSel: Array<ProvinceVO>;
  private messageSuccess: string;
  private showSuccess: boolean;
  showError: boolean;
  allarmiAttivi = 0;
  allarmeVersamento: boolean;
  note: string;
  errorDateEmpy: boolean;


  constructor(
    private serviceSoggetto: AnagraficaSoggettiService,
    private logger: LoggerService,
    private tassiService: TassiService,
    private luoghiService: LuoghiService) { }

 public subscribers: any = {};

  ngOnInit() {
    this.showError = false;
    this.errorDateEmpy = false;
    this.note = "";
    this.allarmeVersamento = false;
    this.provinceSel = [];
    console.log(this.accertamenti);

    this.accertamenti.forEach(acc => {
      this.subscribers.elencoProvince = this.luoghiService.provinciaBySigla(acc.provincia)
      .subscribe(resp => {
        console.log(resp);
        this.provinceSel.push(resp);
      }, err => {
        this.logger.error('error');
      });
    });    

    this.subscription = this.tassiService.tassiByAccertamenti().subscribe(
      res => {
        this.elencoTassi = res;
      }
    );
  }
  
  calcolaAccertamenti(data){
    if (data === '') {
      this.showAccertementiCalcolati = false;
      this.errorDateEmpy = true;

    } else{
      //let dataAccertamento = new Date(data);      
      var dataAccertamentoSel = new Date(data.year, data.month - 1, data.day);
      this.errorDateEmpy = false;
    this.showAccertementiCalcolati = this.checkDateAccertamenti(dataAccertamentoSel);
    if (this.showAccertementiCalcolati === false){
      this.showError = true;
    } else {
      this.showError = false;
    }

    this.showSuccess = false;
    //this.dataAccertamento = data;
    
    /*
    const _dataAccertamento = moment(data);
    this.dataAccertamento = {
            year:  _dataAccertamento.year(),
            month: Number( _dataAccertamento.format('MM')),
            day: Number( _dataAccertamento.format('DD'))
     };
     */
    this.dataAccertamento = {
      year:  data.year,
      month: data.month,
      day: data.day
    };        
    this.totaleAccertamenti = 0;
    this.subscribers = this.accertamenti.map(accertamento => {
     accertamento.interessiMora = accertamento.importo * 0.06;
     accertamento.sanzioni = accertamento.importo * 0.30;
     accertamento.interessi = this.calcolaInteressi(accertamento);
     accertamento.importoComplessivo = this.calcolaImportoComplessivo(accertamento);
     accertamento.dataAccertamento = new Date(this.dataAccertamento.year, this.dataAccertamento.month - 1, this.dataAccertamento.day);     
     this.totaleAccertamenti += accertamento.importoComplessivo;
      this.allarmeVersamento = this.checkAllarmiAttivi(accertamento.allarme.status);
    }, error => this.logger.error('errore'));
    }
  }

  onSaveAccertamento() {
//    const dateToSave = new Date(this.dataAccertamento);
    const dateToSave = new Date(this.dataAccertamento.year, this.dataAccertamento.month - 1, this.dataAccertamento.day);
    this.saveAccertamento();

  }

  saveAccertamento() {
    if ( this.note !== '' ) {
      this.accertamenti.forEach(acc => {
        acc.note = this.note;
      });
    }
    this.subscribers.confermaModificaAccertamento =
    this.serviceSoggetto.confermaModificaAccertamento(this.accertamenti).subscribe(
      resp => {
        console.log(resp);
        this.messageSuccess = 'Accertamento salvato con successo.';
        this.showSuccess = true;
      }, err => {
        this.logger.error('errore');
      }, () => {
       // alert('Accertamento Salvato');
      }
    );
  }

  spegniAllarme(status): number {
    if (status === 1) {
        return 0;
    }
     return status;
  }






  calcolaImportoComplessivo (acc: VersamentiPrVO ): number {
    return acc.importo + acc.interessi +  acc.interessiMora + acc.sanzioni;
  }

  calcolaInteressi(acc: VersamentiPrVO): number {
    let totaleInteressi = 0;
    this.elencoTassi.forEach( tasso => {
      let tassoStartDate = new Date(tasso.dataStart);
      let tassoEndDate = new Date (tasso.dataEnd);
      let dataAccertamento = new Date(this.dataAccertamento.year, this.dataAccertamento.month - 1, this.dataAccertamento.day);
      let dataVersamento = new Date(acc.dataVersamento);
      console.log('tasso start',tassoStartDate.getTime());
      console.log('tasso end',tassoEndDate.getTime());
      console.log('dataAccertamento', dataAccertamento.getTime());
      tasso.numGiorni = 0;
      let numGiorni = 0;
      let totInt = 0;
      if((tassoStartDate.getTime() <= dataAccertamento.getTime() && dataAccertamento.getTime() <= tassoEndDate.getTime()) &&
          (tassoStartDate.getTime() <= dataVersamento.getTime() && dataVersamento.getTime() <= tassoEndDate.getTime())) {
        numGiorni = Math.floor(
          Date.UTC(dataAccertamento.getFullYear() , dataAccertamento.getMonth(), dataAccertamento.getDate()) -
          Date.UTC(dataVersamento.getFullYear() , dataVersamento.getMonth(), dataVersamento.getDate())) /
          (1000 * 60 * 60 * 24);
      }

      if ( tassoStartDate.getTime() <= dataVersamento.getTime() && dataVersamento.getTime() <= tassoEndDate.getTime() && 
      dataAccertamento.getTime() >= tassoEndDate.getTime()) {
        numGiorni = Math.floor(
          Date.UTC(tassoEndDate.getFullYear() , tassoEndDate.getMonth(), tassoEndDate.getDate()) -
          Date.UTC(dataVersamento.getFullYear() , dataVersamento.getMonth(), dataVersamento.getDate())) /
          (1000 * 60 * 60 * 24);
        }

      if (tassoStartDate.getTime() >= dataVersamento.getTime() &&
          tassoStartDate.getTime() <= dataAccertamento.getTime() && dataAccertamento.getTime() <= tassoEndDate.getTime()) {
            numGiorni = Math.floor(
              Date.UTC(dataAccertamento.getFullYear() , dataAccertamento.getMonth(), dataAccertamento.getDate()) -
              Date.UTC(tassoStartDate.getFullYear() , tassoStartDate.getMonth(), tassoStartDate.getDate())) /
              (1000 * 60 * 60 * 24);
      }

      if (dataVersamento.getTime() >= tassoEndDate.getTime() &&
        dataAccertamento.getTime() >= tassoEndDate.getTime()) {
        numGiorni = Math.floor(
          Date.UTC(tassoEndDate.getFullYear() , tassoEndDate.getMonth(), tassoEndDate.getDate()) -
          Date.UTC(tassoStartDate.getFullYear() , tassoStartDate.getMonth(), tassoStartDate.getDate())) /
          (1000 * 60 * 60 * 24);
      }

      if (dataVersamento.getTime() <= tassoStartDate.getTime()
        && dataAccertamento.getTime() >= tassoEndDate.getTime()) {
          numGiorni = Math.floor(
            Date.UTC(tassoEndDate.getFullYear() , tassoEndDate.getMonth(), tassoEndDate.getDate()) -
            Date.UTC(tassoStartDate.getFullYear() , tassoStartDate.getMonth(), tassoStartDate.getDate())) /
            (1000 * 60 * 60 * 24);
        }
      console.log('Numero Giorni', numGiorni);
        tasso.totInteresse = ((acc.importo * tasso.valore) / 365 / 100) * numGiorni ;
        totInt += tasso.totInteresse;
        totaleInteressi += totInt;
        console.log(' Totale interesse in Map', totInt) ;
    });
    console.log(' Totale interesse: ', totaleInteressi) ;
    return totaleInteressi;
  }

  goBack() {
    this.totaleAccertamenti = 0;
    console.log('accertamenti prima', this.accertamenti);
    this.accertamenti = [];
    console.log('accertamenti dopo', this.accertamenti);
    this.showAccertementiCalcolati = false;
    this.subscription.unsubscribe();
    this.accerVuoto.emit([]);
    this.back.emit(false);
  }

  salvaPrestampato() {
    console.log('Scarica Excel');
    this.showSuccess = false;
    this.loaderExcel = true;
    this.serviceSoggetto.scaricaExcelElencoAccertamenti(this.accertamenti).subscribe(
      res => {
        this.loaderExcel = false;
        saveAs(res, "export_elenco_accertamenti_" + 
        this.serviceSoggetto.headerDichiarante.denominazione + "_"
        + this.serviceSoggetto.annoDichiarazione + '.xls');

      },
      err => {
          this.logger.error("errore in download excel");
      });
  }

  gestisciAllarme() {
    this.allarmeVersamento = !this.allarmeVersamento;
    this.accertamenti.forEach( acc => {
      console.log(acc);
      if( this.allarmeVersamento === true) {
        const allarmeToSave = new AllarmiSoggettoVO(null, acc.idConsumi, null, 5, null, '', null, '');
        acc.allarme = allarmeToSave;
      } else {
        acc.allarme = null;
      }
    });
  }

checkAllarmiAttivi(status): boolean {
    if (status === 1) {
        return true;
    }
    return false;
  }

  checkDateAccertamenti(dataAccertmento: Date): boolean {

    for ( let acc of this.accertamenti) {
      let dataScadenza = new Date(acc.dataVersamento);
      console.log('data scadenza' ,dataScadenza.getTime())
      console.log('data versamento',dataAccertmento.getTime());
      if (dataScadenza.getTime() > dataAccertmento.getTime()) {
        console.log('devo tornare false;');
        return false;
      }
    }
    return true;
  }
}
