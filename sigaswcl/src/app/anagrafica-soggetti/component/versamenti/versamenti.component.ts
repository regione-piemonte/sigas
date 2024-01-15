import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { Subject } from "rxjs";
import { saveAs } from "file-saver";
import { LoggerService } from "../../../core/services/logger.service";
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { DataTableDirective } from 'angular-datatables';
import { DataTableIt } from "../../../commons/class/commons-data-table";
import { AnagraficaSoggettiService } from '../../service/anagrafica-soggetti.service';
import { LuoghiService } from '../../../shared/service/luoghi-service';
import { ProvinceVO } from "../../../commons/vo/luoghi-vo/province-vo";
import { TipoVersamentiVO } from "../../../commons/vo/tipo-versamenti-vo";
import { VersamentiPrVO } from "../../../commons/vo/versamenti-pr-vo";
import { ConsumiPrVO } from "../../../commons/vo/consumi-pr-vo";
import { AllarmiSoggettoVO } from "../../../commons/vo/allarmi-soggetto-vo";
import { ExceptionVO } from "../../../core/commons/vo/exceptionVO";

import { RicercaVersamentiRequest } from '../../../commons/request/ricerca-versamenti-request';
import { ConfermaVersamentoRequest } from '../../../commons/request/conferma-versamento-request';
import {UtilityService} from '../../../core/services/utility/utility.service';
import {MessageEnum} from '../../../core/services/utility/enum/messageEnum';

import * as moment from 'moment';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateCustomParserFormatter} from '../../../commons/class/dateformat';
import { AngularFileUploaderComponent } from 'angular-file-uploader';

import { ItemVersamentiReport } from '../../../commons/request/ItemVersamentiReport';


@Component({
  selector: 'app-versamenti',
  templateUrl: './versamenti.component.html',
  styleUrls: ['./versamenti.component.scss'],
  providers: [
              {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter}
          ]
})

@DestroySubscribers()
export class VersamentiComponent implements OnInit, AfterViewInit {
    
  @ViewChild(DataTableDirective) dtElement: DataTableDirective;
    
  private dtTrigger: Subject<any> = new Subject();
  private loaderDT: boolean;
  private loaderDatiRias: boolean;
  private loaderProvince:boolean;
  private provinceModel: Array<ProvinceVO>;
  private loaderTipoVersamenti:boolean;
  private tipoVersamentiModel: Array<TipoVersamentiVO>;
  private filterDisabled: boolean;
  private anniVersamenti: Array<string>;
  private anniVersamentiPerRicerca: Array<string>;
  private listaAnni:Number[];
  private loaderYear: boolean;
  private mesiVersamenti: Array<string>;
  private loaderMonth: boolean;
  private dtOptions: any;
  private idAnagSogg: number;
  private elencoVersamenti: Array<VersamentiPrVO>;
  private elencoVersamentiCalcolati: Array<VersamentiPrVO>;
  private elencoVersamentiFinal: Array<VersamentiPrVO>;
  private consumi: ConsumiPrVO; 
  private allarmeSogg: AllarmiSoggettoVO;
  private tipoAllarme: string;
  private versato: number;
  //Filtro ricerca versamenti
  public anno: string;
  public mese: string;
  public ultimoMese: number;
  public differenza: number;
  public importo_prec: number;
  public conguaglio_prec: number;
  public rateo_calc: number;
  public rateo_prec: number;
  private provinciaConsumo: ProvinceVO
  public idTipoVersamento: number;
  private allarmeVersamento: boolean;
  private loaderExcel: boolean;
  private mesiAnnuali: Array<any>;

  //Inserimento nuovo versamento
  private loaderConsProvince: boolean;
  private consumiPr: Array<ConsumiPrVO>;
  private nuovoVersamento: boolean;
  private versamentoToSave: VersamentiPrVO;
  private provinciaTrovata: ProvinceVO;
  private tutteProvince: ProvinceVO;
  
  //Modifica versamento
  private updateVersamento: boolean;
  //Common req
  private ricercaVersamentiRequest: RicercaVersamentiRequest;
  private confermaVersamentoRequest: ConfermaVersamentoRequest;
  
  private showMessageError: boolean;
  private showSuccess: boolean;
  private messageError: string;
  private messageSuccess: string;
  
  public subscribers: any = {};
  
  private dataVersamento: any;
  

  private showMessageErrorModRateo: boolean;
  private showSuccessModRateo: boolean;
  private messageErrorModRateo: string;
  private messageSuccessModRateo: string;
  public isSaveDisabled: boolean;

  private elencoVersamentiStessoMese: Array<VersamentiPrVO>;

  //Evolutiva 202107-01      
  //private versamentiPr: any; 
  
  private loaderEvolutiva202107: boolean;
  private conguaglioCalcolato : any;
  private selConguaglioCalcolato: any;
  //----------------------------

  //Evolutiva conguaglio calcolato
  private visualizzaSpanConguaglioCalcolato: boolean = false;

  constructor(
    private router: Router,
    private logger: LoggerService,
    private anagraficaSoggettiService: AnagraficaSoggettiService,
    private luoghiService:LuoghiService,
    private utilityService: UtilityService
  ) { }
  

  ngOnInit() {
    window.scrollTo(0, 0);
    this.dtOptions = {            
            destroy: true,
            head: 20,
            pagingType: 'full_numbers',
            pageLength: 5,
            processing: true,
            language: DataTableIt.language,
            responsive: true,
            searching: false,
            paging: false,
            ordering: false,
            columnDefs: [
              { className: 'dt-center', "targets": [0,1,2,3,4,5,6,7] },
              { width: '10%', targets: 0 },
              { width: '10%', targets: 1 },
              { width: '10%', targets: 2 },
              { width: '10%', targets: 3 },
              { width: '10%', targets: 4 },
              { width: '15%', targets: 5 },
              { width: '15%', targets: 6 },
              { width: '20%', targets: 7 }
            ],
            dom: 'Bfrtip'            
          };

    this.tutteProvince = new ProvinceVO(0,'','');
    this.mesiAnnuali = [
        {
            'valore': '1',
            'descrizione': 'Gennaio'
        },
        {
            'valore': '2',
            'descrizione': 'Febbraio'
        },
        {
            'valore': '3',
            'descrizione': 'Marzo'
        },
        {
            'valore': '4',
            'descrizione': 'Aprile'
        },
        {
            'valore': '5',
            'descrizione': 'Maggio'
        },
        {
            'valore': '6',
            'descrizione': 'Giugno'
        },
        {
            'valore': '7',
            'descrizione': 'Luglio'
        },
        {
            'valore': '8',
            'descrizione': 'Agosto'
        },
        {
            'valore': '9',
            'descrizione': 'Settembre'
        },
        {
            'valore': '10',
            'descrizione': 'Ottobre'
        },
        {
            'valore': '11',
            'descrizione': 'Novembre'
        },
        {
            'valore': '12',
            'descrizione': 'Dicembre'
        }
    ];

    this.selConguaglioCalcolato = false;
    this.allarmeVersamento = false;
    this.showMessageError = false;
    this.showSuccess = false;
    if (this.anagraficaSoggettiService.headerDichiarante) {
        this.idAnagSogg = this.anagraficaSoggettiService.headerDichiarante.idAnag;
    }
    this.ricercaVersamentiRequest = new RicercaVersamentiRequest(null,null,'',null,null);
    this.initVersamentiSoggetto();  
    this.loadAnniVersamento();
    this.loadAnniVersamentoPerRicerca();

    //Evolutiva 202107-01        
    /*
    this.anagraficaSoggettiService.versamentiPr.subscribe(data => {
        this.versamentiPr = data;
    });    
    */
    //----------------------
    
  } 
  
  ngAfterViewInit(){
      //Define datatable 
      this.dtTrigger.next();
    }
  
  loadAnniVersamento(){
      this.logger.info("carico gli anni versamento")
      this.loaderYear = true;
      if (this.anagraficaSoggettiService.ricercaAnniVersamenti() != null)  
      {
            this.subscribers.ricercaAnniVersamenti = this.anagraficaSoggettiService.ricercaAnniVersamenti()
                .subscribe(
                    res => {
                        //this.anniVersamenti = res;
                        this.anniVersamenti = res.lista_annualita;
                        this.loaderYear = false;
                        
                        //this.versamentoToSave.annualita = +this.anniVersamenti[0];
                        if(res.anno_ultimo_versamento!=null&&res.anno_ultimo_versamento!=undefined){
                            this.versamentoToSave.annualita = +res.anno_ultimo_versamento;                            
                        } else {
                            this.versamentoToSave.annualita = +res.lista_annualita[0];
                        }                        
                    }, 
                    err => {
                        this.logger.error("errore ");
                        this.loaderYear = false;
                });
    }
  }

  loadAnniVersamentoPerRicerca(){
        this.logger.info("carico gli anni versamento")
        this.loaderYear = true;
        if (this.anagraficaSoggettiService.ricercaAnniVersamentiPerRicerca() != null)  
        {
            this.subscribers.anniVersamentiPerRicerca = this.anagraficaSoggettiService.ricercaAnniVersamentiPerRicerca()
                .subscribe(
                    res => {
                        this.anniVersamentiPerRicerca = res;
                                               
                    }, 
                    err => {
                        this.logger.error("errore ");
                        this.loaderYear = false;
                });
    }
  }
  
  loadProvinceVersamenti() {
      this.logger.info("carico le province")
      this.loaderProvince = true;
      this.subscribers.province = this.anagraficaSoggettiService.ricercaProvinceVersamenti().subscribe(data => {
          this.loaderProvince = false;
          this.provinceModel = data;
      }, err => {
          this.logger.error("Errore nel recupero delle province");
          this.loaderProvince = false;
      });
  }
  
  loadTipoVersamenti() {
      this.logger.info("carico i tipo versamenti")
      this.loaderTipoVersamenti = true;
      this.subscribers.tipoVersamenti = this.anagraficaSoggettiService.ricercaTipoVersamenti().subscribe(data => {
          this.loaderTipoVersamenti = false;
          this.tipoVersamentiModel = data;
      }, err => {
          this.logger.error("Errore nel recupero tipo versamento");
          this.loaderTipoVersamenti = false;
      });
  }
  
  loadMesiVersamento() {
      this.logger.info("carico i mesi versamenti")
      this.loaderMonth = true;
      this.subscribers.meseVersamenti = this.anagraficaSoggettiService.ricercaMeseVersamenti(this.anno).subscribe(data => {
          this.mesiVersamenti = data;
          this.loaderMonth = false;
      }, err => {
          this.logger.error("Errore nel recupero dei mesi versamenti ");
          this.loaderMonth = false;
      });
  }    
  
  isEquals(mese: String, meseTarget: String): boolean {
    return mese === meseTarget;
  }

  gestioneRicercaVersamentiCalcoli(): void{                                                                                   
    this.elencoVersamentiCalcolati.forEach((versamentoCal,index) => {
        if (versamentoCal.tipo.denominazione.toUpperCase( ) != 'ACCERTAMENTO') {
            if(this.consumi!=null){
                if((versamentoCal.mese!='Gennaio') || 
                   (versamentoCal.mese ==='Gennaio' && index > 0 ))
                {                                                                                
                    this.elencoVersamentiStessoMese = this.elencoVersamentiCalcolati.filter(item => this.isEquals(item.mese, versamentoCal.mese));
                    this.elencoVersamentiStessoMese.map(item => {item.note = moment(item.insDate).format("DD-MM-YYYY")});
                    this.elencoVersamentiStessoMese.sort((a,b) => a.idVersamento - b.idVersamento);
                    if(this.elencoVersamentiStessoMese.length > 1){                                                    
                        if(this.elencoVersamentiStessoMese.indexOf(versamentoCal)===0)
                        {
                            versamentoCal.importo_prec = this.differenza + this.rateo_calc;
                            versamentoCal.differenza = versamentoCal.importo_prec - versamentoCal.importo;                                             
                            this.differenza = versamentoCal.differenza;
                            console.log("===0 id: " + versamentoCal.idVersamento + " importo_prec " + versamentoCal.importo_prec  )                                                                                                            
                        }else{                                                        
                            if(this.elencoVersamentiStessoMese.indexOf(versamentoCal)==0){
                                versamentoCal.importo_prec = this.differenza + this.rateo_calc;
                                versamentoCal.differenza = versamentoCal.importo_prec - versamentoCal.importo;                                
                                this.differenza = versamentoCal.differenza;
                            }else{
                                versamentoCal.importo_prec = this.differenza;
                                versamentoCal.differenza = versamentoCal.importo_prec - versamentoCal.importo;
                                this.differenza = versamentoCal.differenza;
                            }                            
                        }
                    }else{
                        versamentoCal.importo_prec = this.differenza + this.rateo_calc;
                        versamentoCal.differenza = versamentoCal.importo_prec - versamentoCal.importo;
                        this.differenza = versamentoCal.differenza;
                    }                                                
                }else{
                    versamentoCal.importo_prec =  this.rateo_calc +  this.consumi.conguaglio_calc;
                    versamentoCal.differenza = versamentoCal.importo_prec - versamentoCal.importo;
                    this.differenza = versamentoCal.importo_prec - versamentoCal.importo;
                }
            }
        }
        this.versato = 0;
        this.elencoVersamenti.forEach(versamento => {
            if (versamento.tipo.denominazione.toUpperCase( ) != 'ACCERTAMENTO') {
                for (var i = 0 ; i <= 11; i++) {
                    if (versamento.mese === this.mesiAnnuali[i].descrizione ) {
                        this.ultimoMese = this.mesiAnnuali[i].valore;
                        versamento.codMese = this.mesiAnnuali[i].valore;
                    }
                }        
                if(versamento.idVersamento==versamentoCal.idVersamento){
                    console.log("travaso id: " + versamento.idVersamento + "versamentoCal.idVersamento " + versamentoCal.idVersamento + " importo_prec " + versamentoCal.importo_prec  )                                                                                                            
                    versamento.importo_prec = versamentoCal.importo_prec;
                    versamento.differenza = versamentoCal.differenza;                                                
                    //versamento.note = moment(versamentoCal.insDate).format("DD-MM-YYYY");
                }                                            
            }                                        
            this.versato += versamento.importo;
            console.log(this.versato);
        });                
    });
    this.elencoVersamenti.sort((a,b) => {                                                        
        if(a.codMese == b.codMese){                                             
            return  a.idVersamento - b.idVersamento;
        }                                                        
        else{
            return a.codMese - b.codMese;
        }        
    });
    this.elencoVersamentiFinal = this.elencoVersamenti.map(verItem => Object.assign({},verItem));
    console.log("elencoVersamentiFinal", this.elencoVersamentiFinal);
    this.rerender();    
  }

  gestioneRicercaConsumiForProvinceAndAnnualita(): void{
    this.subscribers.ricercaConsumiForProvince =
    this.anagraficaSoggettiService.ricercaConsumiForProvinceAndAnnualita(+this.anno-1, this.provinciaConsumo.sigla )
    .subscribe( res => {
                            this.consumi = res; 
                            //Evolutiva 202107-01
                            //------------------------ 
                            this.selConguaglioCalcolato=null;                                                    
                            if(this.provinciaConsumo.sigla === 'TUTTE' || this.provinciaConsumo.sigla === ''){
                                /******************************************************************
                                 DA SCOMMENTARE QUALORA SI DECIDESSE DI POPOLARE LE TEXT AREA
                                *******************************************************************
                                this.conguaglioCalcolato = 0;       
                                this.anagraficaSoggettiService.ricercaConsumiForProvinceByAnno(+this.anno-1).subscribe(consumiArray => {
                                    consumiArray.map(elem => { 
                                        this.conguaglioCalcolato += elem.conguaglio_calc;
                                    });
                                    this.selConguaglioCalcolato = this.conguaglioCalcolato;
                                    setTimeout(() => {
                                        this.loaderEvolutiva202107=false;                                                       
                                    }, 1000)            
                                });
                                */
                                this.loaderEvolutiva202107=false;
                            }else{
                                //this.selConguaglioCalcolato=null;
                                if(res != null && res.conguaglio_calc!=undefined && res.conguaglio_calc != null){
                                    //this.selConguaglioCalcolato = res.conguaglio_calc;
                                    if(res.compensazionePrVO!=null&&res.compensazionePrVO!=undefined){
                                        this.selConguaglioCalcolato = res.compensazionePrVO.conguaglio_compensato
                                        this.visualizzaSpanConguaglioCalcolato = true;
                                    } else {
                                        this.selConguaglioCalcolato = res.conguaglio_calc;
                                    }
                                }                                
                                setTimeout(() => {
                                    this.loaderEvolutiva202107=false;                                    
                                }, 1000)
                            }                        
                            //-------------------------
                        }, 
                err => {this.logger.error( "errore " );},
                 () => {                            
                        console.log('consumi: '+this.consumi);
                        
                        if ( this.consumi != null ) {
                            this.loadAllarmi( this.consumi.id_consumi.toString() );
                        }

                        this.loaderDatiRias = false;
                        
                        this.differenza = 0;
                        if(this.consumi!=null){
                            this.rateo_calc = Math.round((this.consumi.totaleDich/12) * 100)/100;
                        } 

                        var ricVerCalReq: RicercaVersamentiRequest = new RicercaVersamentiRequest(this.idAnagSogg, this.anno, 
                                                                                                 "Tutti", this.provinciaConsumo.id, 
                                                                                                 this.idTipoVersamento );
                        this.subscribers.ricercaVersamenti = 
                                this.anagraficaSoggettiService.ricercaVersamentiCalcoli(ricVerCalReq)
                                                                    .subscribe(respCalcoli => {this.elencoVersamentiCalcolati = respCalcoli;} , 
                                                                            err => {this.logger.error( "errore " );}, 
                                                                            () => {
                                                                                    this.elencoVersamentiCalcolati.forEach(versamento => {
                                                                                    if (versamento.tipo.denominazione.toUpperCase( ) != 'ACCERTAMENTO') {
                                                                                        for (var i = 0 ; i <= 11; i++) {
                                                                                            if (versamento.mese === this.mesiAnnuali[i].descrizione ) {
                                                                                                this.ultimoMese = this.mesiAnnuali[i].valore;
                                                                                                versamento.codMese = this.mesiAnnuali[i].valore;
                                                                                            }
                                                                                        }
                                                                                    }                                                                                    
                                                                                    });

                                                                                    this.elencoVersamentiCalcolati.sort((a,b) => {                                                        
                                                                                        if(a.codMese == b.codMese){                                             
                                                                                            return  a.idVersamento - b.idVersamento;
                                                                                        }                                                        
                                                                                        else{
                                                                                            return a.codMese - b.codMese;
                                                                                        }                                                        
                                                                                    });

                                                                                    this.gestioneRicercaVersamentiCalcoli();});                           
                        
                        
                });
  }

  //Ricerca versamenti
  changeFilter() {
      this.showMessageErrorModRateo = false;
      this.showSuccessModRateo = false;
      this.messageErrorModRateo = "";
      this.messageSuccessModRateo = ""; 
      this.visualizzaSpanConguaglioCalcolato = false;     
      if ( this.anno != null ) {
          this.loadMesiVersamento();          
          this.loadProvinceVersamenti()          
          this.loadTipoVersamenti();
          if ( this.anno && this.mese && this.provinciaConsumo && this.idTipoVersamento ) {
              this.loaderEvolutiva202107 = true;
              this.filterDisabled = true;
              this.loaderDT = true;
              this.loaderDatiRias = true;
              this.versato = 0;
              this.rateo_calc = null;
              this.ricercaVersamentiRequest = new RicercaVersamentiRequest( this.idAnagSogg, this.anno, this.mese, this.provinciaConsumo.id, this.idTipoVersamento );
              this.anagraficaSoggettiService.ricercaVersamentiReq = this.ricercaVersamentiRequest;
              this.subscribers.ricercaVersamenti = this.anagraficaSoggettiService.ricercaVersamenti()
                  .subscribe(resp => {
                                    this.elencoVersamenti = resp;
                                    if (this.mese != null && this.mese.toUpperCase() !== 'TUTTI') {
                                        this.elencoVersamenti = this.elencoVersamenti.filter(versamento => 
                                            versamento.mese.toUpperCase() === this.mese.toUpperCase()
                                        )
                                    }
                                    if (this.idTipoVersamento !=  null && this.idTipoVersamento != 0) {
                                        let tipoVersamento = this.tipoVersamentiModel.filter( tipoVersamento =>
                                            tipoVersamento.idTipoVersamento == this.idTipoVersamento 
                                        )
                                        this.elencoVersamenti = this.elencoVersamenti.filter(versamento => 
                                            versamento.tipo.denominazione.toUpperCase() === tipoVersamento[0].denominazione.toUpperCase()
                                        )
                                    }                      
                                    this.gestioneRicercaConsumiForProvinceAndAnnualita();                                                                       
                                    
                                    setTimeout(() => {
                                        this.loaderDT = false;                                                       
                                    }, 1000)
                                    
                                    this.filterDisabled = false;
                                    this.rerender();
                            }, 
                            err => {this.logger.error( err );
                                    this.loaderDT = false;
                            } );
          }
      }
    }
  
 
  
  onChangeRateoCalc(rateoCalc: number){
      console.log('onChangeRateoCalc');
      this.differenza = 0;
      this.rateo_calc = rateoCalc;
      this.consumi.totaleDich = rateoCalc*12;

      this.elencoVersamentiCalcolati.forEach(versamento => {
        if (versamento.tipo.denominazione.toUpperCase( ) != 'ACCERTAMENTO') {
            for (var i = 0 ; i <= 11; i++) {
                if (versamento.mese === this.mesiAnnuali[i].descrizione ) {
                    this.ultimoMese = this.mesiAnnuali[i].valore;
                    versamento.codMese = this.mesiAnnuali[i].valore;
                }
            }
        }                                                                                    
        });

        this.elencoVersamentiCalcolati.sort((a,b) => {                                                        
            if(a.codMese == b.codMese){                                             
                return  a.idVersamento - b.idVersamento;
            }                                                        
            else{
                return a.codMese - b.codMese;
            }                                                        
        });

      this.elencoVersamentiCalcolati.map( (versamentoCal, index) => {
          if (versamentoCal.tipo.denominazione.toUpperCase( ) != 'ACCERTAMENTO') {
            if((versamentoCal.mese!='Gennaio') ||
               (versamentoCal.mese ==='Gennaio' && index > 0 ))
            {                                                                
                this.elencoVersamentiStessoMese = this.elencoVersamentiCalcolati.filter(item => this.isEquals(item.mese, versamentoCal.mese));
                this.elencoVersamentiStessoMese.map(item => {item.note = moment(item.insDate).format("DD-MM-YYYY")});
                this.elencoVersamentiStessoMese.sort((a,b) => a.idVersamento - b.idVersamento);
                if(this.elencoVersamentiStessoMese.length > 1){                                                    
                    if(this.elencoVersamentiStessoMese.indexOf(versamentoCal)===0)
                    {
                        versamentoCal.importo_prec = this.differenza + this.rateo_calc;
                        versamentoCal.differenza = versamentoCal.importo_prec - versamentoCal.importo;                                             
                        this.differenza = versamentoCal.differenza;
                        console.log("===0 id: " + versamentoCal.idVersamento + " importo_prec " + versamentoCal.importo_prec  )                                                                                                            
                    }else{                                                        
                        if(this.elencoVersamentiStessoMese.indexOf(versamentoCal)==0){
                            versamentoCal.importo_prec = this.differenza + this.rateo_calc;
                            versamentoCal.differenza = versamentoCal.importo_prec - versamentoCal.importo;                                
                            this.differenza = versamentoCal.differenza;
                        }else{
                            versamentoCal.importo_prec = this.differenza;
                            versamentoCal.differenza = versamentoCal.importo_prec - versamentoCal.importo;
                            this.differenza = versamentoCal.differenza;
                        }                            
                    }
                }else{
                    versamentoCal.importo_prec = this.differenza + this.rateo_calc;
                    versamentoCal.differenza = versamentoCal.importo_prec - versamentoCal.importo;
                    this.differenza = versamentoCal.differenza;
                }                                                
            }else{
                versamentoCal.importo_prec =  this.rateo_calc +  this.consumi.conguaglio_calc;
                versamentoCal.differenza = versamentoCal.importo_prec - versamentoCal.importo;
                this.differenza = versamentoCal.importo_prec - versamentoCal.importo;
            }
          }
          
          this.versato = 0;
          this.elencoVersamenti.forEach(versamento => {
            if (versamento.tipo.denominazione.toUpperCase( ) != 'ACCERTAMENTO') {
                for (var i = 0 ; i <= 11; i++) {
                    if (versamento.mese === this.mesiAnnuali[i].descrizione ) {
                        this.ultimoMese = this.mesiAnnuali[i].valore;
                        versamento.codMese = this.mesiAnnuali[i].valore;
                    }
                }        
                if(versamento.idVersamento==versamentoCal.idVersamento){                   
                    versamento.importo_prec = versamentoCal.importo_prec;
                    versamento.differenza = versamentoCal.differenza;                                                                    
                }                                            
            }                                        
            this.versato += versamento.importo;
            console.log(this.versato);
        });
      
      });

      this.elencoVersamenti.sort((a,b) => {                                                        
            if(a.codMese == b.codMese){                                             
                return  a.idVersamento - b.idVersamento;
            }                                                        
            else{
                return a.codMese - b.codMese;
            }        
      });

      this.elencoVersamentiFinal = this.elencoVersamenti.map(verItem => Object.assign({},verItem));
      console.log("elencoVersamentiFinal", this.elencoVersamentiFinal);
      this.rerender();
      
  }

  loadAllarmi(idConsumo: string) {
      this.logger.info( "carico gli eventuali allarmi" )
      this.subscribers.allarmi = this.anagraficaSoggettiService.ricercaAllarmi(idConsumo).subscribe( data => {
          this.allarmeSogg = data;
              if ( this.allarmeSogg != null && this.allarmeSogg.status == 1 ) {
                  this.allarmeVersamento = true;
              }
      }, err => {
          this.logger.error( "Errore nel recupero allarmi" );
      } );
  }

  gestisciAllarme(){
      
      if(this.elencoVersamenti.length > 0) {                
        this.subscribers.allarmeSoggetto = this.anagraficaSoggettiService.allarmeSoggetto(!this.allarmeVersamento, this.consumi.id_consumi)
        .subscribe(
          data => {
              //this.showSuccess = true;
              //this.messageSuccess = data
              this.loaderDatiRias = false;
          }, err => {
              this.loaderDatiRias = false;
              this.logger.error( "errore gestione allarme" );
          });       
      }       
  }  
    
  rerender(): void {
      try {
        this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
          // Destroy the table first
          dtInstance.destroy();
          // Call the dtTrigger to rerender again
          this.dtTrigger.next();
        });
      } catch (err) {
        console.log(err);
      }
    }
  
  initVersamentiSoggetto() {
      this.versato = 0;
      this.rateo_calc = null;
      if (this.anagraficaSoggettiService.headerDichiarante != null) {
          this.ricercaVersamentiRequest.idAnag = this.anagraficaSoggettiService.headerDichiarante.idAnag;          
          this.ricercaVersamentiRequest.anno = this.anno;
          this.anagraficaSoggettiService.ricercaVersamentiReq = this.ricercaVersamentiRequest;
          this.loaderDT = true;
          this.subscribers.ricercaVersamenti = this.anagraficaSoggettiService.ricercaVersamenti()
              .subscribe(resp => {
              this.elencoVersamenti = resp;
              
              if (this.anno != null && this.provinciaConsumo != null) {
                  this.subscribers.ricercaConsumiForProvince =
                      this.anagraficaSoggettiService.ricercaConsumiForProvinceAndAnnualita( +this.anno-1, this.provinciaConsumo.sigla )
                          .subscribe( res => {
                              this.consumi = res;
                              if ( this.consumi != null ) {
                                  this.loadAllarmi( this.consumi.id_consumi.toString() );
                              }
                              
                              this.differenza = 0;
                              if(this.consumi!=null){
                                  this.rateo_calc = Math.round((this.consumi.totaleDich/12) * 100)/100;
                              }
                              var importoMesePrecedente = 0;                             
                              
                              
                            var ricVerCalReq: RicercaVersamentiRequest = new RicercaVersamentiRequest(this.idAnagSogg, this.anno, 
                                                                                                  "Tutti", this.provinciaConsumo.id, 
                                                                                                  this.idTipoVersamento );
                            this.subscribers.ricercaVersamenti = 
                            this.anagraficaSoggettiService.ricercaVersamentiCalcoli(ricVerCalReq)
                            .subscribe(respCalcoli => {this.elencoVersamentiCalcolati = respCalcoli;} , 
                                err => {this.logger.error( "errore " );}, 
                                () => {
                                        this.elencoVersamentiCalcolati.forEach(versamento => {
                                        if (versamento.tipo.denominazione.toUpperCase( ) != 'ACCERTAMENTO') {
                                            for (var i = 0 ; i <= 11; i++) {
                                                if (versamento.mese === this.mesiAnnuali[i].descrizione ) {
                                                    this.ultimoMese = this.mesiAnnuali[i].valore;
                                                    versamento.codMese = this.mesiAnnuali[i].valore;
                                                }
                                            }
                                        }                                                                                    
                                        });

                                        this.elencoVersamentiCalcolati.sort((a,b) => {                                                        
                                            if(a.codMese == b.codMese){                                             
                                                return  a.idVersamento - b.idVersamento;
                                            }                                                        
                                            else{
                                                return a.codMese - b.codMese;
                                            }                                                        
                                        });

                                        this.gestioneRicercaVersamentiCalcoli();});
                              
                          }, err => {
                              this.logger.error( "errore " );
                          } );
              }
              if (this.mese != null && this.mese.toUpperCase() !== 'TUTTI') {
                  this.elencoVersamenti = this.elencoVersamenti.filter(versamento => 
                      versamento.mese.toUpperCase() === this.mese.toUpperCase()
                  )
              }

              if (this.idTipoVersamento != null && this.idTipoVersamento != 0) {
                  let tipoVersamento = this.tipoVersamentiModel.filter( tipoVersamento => {
                          if (tipoVersamento.idTipoVersamento == this.idTipoVersamento )
                              return  tipoVersamento;
                      })
                      this.elencoVersamenti = this.elencoVersamenti.filter(versamento => 
                          versamento.tipo.denominazione.toUpperCase() === tipoVersamento[0].denominazione.toUpperCase()
                      )
              }

              setTimeout(() => {
                  this.loaderDT = false;
              },1000);              

              }, err => {
              this.logger.error(err);
              this.loaderDT = false;
              });
          }
    }
  
  reInitVersamentiSoggetto() {
    this.versato = 0;
    this.rateo_calc = null;
    if (this.anagraficaSoggettiService.headerDichiarante != null) {
        this.ricercaVersamentiRequest.idAnag = this.anagraficaSoggettiService.headerDichiarante.idAnag;        
        this.ricercaVersamentiRequest.anno = this.anno;
        this.anagraficaSoggettiService.ricercaVersamentiReq = this.ricercaVersamentiRequest;
        this.loaderDT = true;
        this.subscribers.ricercaVersamenti = this.anagraficaSoggettiService.ricercaVersamenti()
            .subscribe(resp => {
            this.elencoVersamenti = resp;            
            if (this.anno != null && this.provinciaConsumo != null) {
                this.subscribers.ricercaConsumiForProvince =
                    this.anagraficaSoggettiService.ricercaConsumiForProvinceAndAnnualita( +this.anno-1, this.provinciaConsumo.sigla )
                        .subscribe( res => {
                            this.consumi = res;
                            if ( this.consumi != null ) {
                                this.loadAllarmi( this.consumi.id_consumi.toString() );
                            }
                            
                            this.differenza = 0;
                            if(this.consumi!=null){
                                this.rateo_calc = Math.round((this.consumi.totaleDich/12) * 100)/100;
                            }                            
                            var importoMesePrecedente = 0;
                                                        
                            
                        var ricVerCalReq: RicercaVersamentiRequest = new RicercaVersamentiRequest(this.idAnagSogg, this.anno, 
                                                                                                "Tutti", this.provinciaConsumo.id, 
                                                                                                this.idTipoVersamento );
                        this.subscribers.ricercaVersamenti = 
                        this.anagraficaSoggettiService.ricercaVersamentiCalcoli(ricVerCalReq)
                        .subscribe(respCalcoli => {this.elencoVersamentiCalcolati = respCalcoli;} , 
                            err => {this.logger.error( "errore " );}, 
                            () => {
                                    this.elencoVersamentiCalcolati.forEach(versamento => {
                                    if (versamento.tipo.denominazione.toUpperCase( ) != 'ACCERTAMENTO') {
                                        for (var i = 0 ; i <= 11; i++) {
                                            if (versamento.mese === this.mesiAnnuali[i].descrizione ) {
                                                this.ultimoMese = this.mesiAnnuali[i].valore;
                                                versamento.codMese = this.mesiAnnuali[i].valore;
                                            }
                                        }
                                    }                                                                                    
                                    });

                                    this.elencoVersamentiCalcolati.sort((a,b) => {                                                        
                                        if(a.codMese == b.codMese){                                             
                                            return  a.idVersamento - b.idVersamento;
                                        }                                                        
                                        else{
                                            return a.codMese - b.codMese;
                                        }                                                        
                                    });

                                    this.gestioneRicercaVersamentiCalcoli();});
                        }, err => {
                            this.logger.error( "errore " );
                        } );
            }
            if (this.mese != null && this.mese.toUpperCase() !== 'TUTTI') {
                this.elencoVersamenti = this.elencoVersamenti.filter(versamento => 
                    versamento.mese.toUpperCase() === this.mese.toUpperCase()
                )
            }

            if (this.idTipoVersamento != null && this.idTipoVersamento != 0) {
                let tipoVersamento = this.tipoVersamentiModel.filter( tipoVersamento => {
                        if (tipoVersamento.idTipoVersamento == this.idTipoVersamento )
                            return  tipoVersamento;
                    })
                    this.elencoVersamenti = this.elencoVersamenti.filter(versamento => 
                        versamento.tipo.denominazione.toUpperCase() === tipoVersamento[0].denominazione.toUpperCase()
                    )
            }

            setTimeout(() => {
                this.loaderDT = false;
            },1000);
            
            this.rerender();
            }, err => {
            this.logger.error(err);
            this.loaderDT = false;
            });
        }
  }
  
  goInserimentoVersamento(){
      this.showMessageError = false;
      this.nuovoVersamento = true;
      this.clearAll();
      //CR REQ24
      //this.calcolaAnniRecenti();
      this.loadAnniVersamento() 
      this.loadTipoVersamenti() ;
      this.versamentoToSave.idAnag = this.idAnagSogg;
  }
  
  salvaModificaRateo(){
      this.loaderDatiRias = true;
      this.anagraficaSoggettiService.updateTotaleDichConsumi(this.consumi).subscribe(
              resp => {
                  console.log(resp);
                  this.messageSuccessModRateo = "Modifica avvenuta con successo";
                  this.showSuccessModRateo = true;
                  this.loaderDatiRias = false;
              }, err => {
                this.logger.error("errore salvataggio salvaModificaRateo");
                this.messageErrorModRateo = "Si è verificato un errore in fase di salvataggio";
                this.showMessageErrorModRateo = true;
                this.loaderDatiRias = false;
            });
  }
  
  editVersamento(versamento: VersamentiPrVO ) {
      this.showMessageError = false;
      this.versamentoToSave = versamento;
      this.updateVersamento = true;
      this.versamentoToSave.idAnag= this.idAnagSogg;
      
      const _dataVersamento = moment(versamento.dataVersamento);
      this.dataVersamento = {
              year: _dataVersamento.year(),
              month: Number(_dataVersamento.format('MM')),
              day: Number(_dataVersamento.format('DD'))
          };
      this.calcolaAnniRecenti();
    }
  
  calcolaAnniRecenti() {
      this.listaAnni = new Array();
      var current = (new Date()).getFullYear();
      for (var i = 0; i < 5; i++) {
        this.listaAnni[i] = current;
        current--;
      }
   }
    
  loadConsumoPerProvince(){
     console.log(this.versamentoToSave.annualita);
     console.log(this.versamentoToSave.provincia);     
     this.loaderConsProvince = true;  
     this.subscribers.ricercaConsumiForProvince = 
         this.anagraficaSoggettiService.ricercaConsumiForProvinceAndAnnualita(this.versamentoToSave.annualita,this.versamentoToSave.provincia)
      .subscribe(res => {
        this.versamentoToSave.consumo = res;
        this.loaderConsProvince = false;
      }, err => {
        this.loaderConsProvince = false;
        this.logger.error("errore ");
    });

  }
  
  clearAll() {
      this.versamentoToSave = new VersamentiPrVO(null, null, null, null,'', '' ,new TipoVersamentiVO(null,'',''),null,null,null,'',null,0,false,null);   
      this.tipoVersamentiModel = null;
      //console.log(this.versamentoToSave);
    }
  
  onSubmitSalva() {
      if (this.versamentoToSave.note.length > 255) {
        	this.utilityService.getMessageByKey(MessageEnum.SIZENOTE)
            	.subscribe(msg => {
				   this.showMessageError = true;
                   this.messageError = msg.message || '';
                        }, error => {
                            this.logger.error(error);
                        });
        } else {
              this.isSaveDisabled = true;
              this.versamentoToSave.dataVersamento =  new Date(this.dataVersamento.year, this.dataVersamento.month - 1, this.dataVersamento.day);
              this.subscribers.confermaInserimentoVersamento = this.anagraficaSoggettiService.confermaInserimentoVersamento( this.versamentoToSave )
              .subscribe( resp => {
                    this.loadAnniVersamento();
                    this.subscribers.provinciaBySigla = this.luoghiService.provinciaBySigla( this.versamentoToSave.provincia ).
                        subscribe( resp => {
                            console.log( resp );
                            this.provinciaTrovata = resp;
                            this.provinciaConsumo = this.provinciaTrovata;
                            this.anno = this.versamentoToSave.annualita.toString();
                            //this.anagraficaSoggettiService.annoDichiarazione = this.versamentoToSave.annualita.toString();
                            this.mese = this.versamentoToSave.mese;
                            this.loadMesiVersamento();
                            this.loadAnniVersamento();                    
                            this.loadAnniVersamentoPerRicerca();
                            this.loadProvinceVersamenti();
                            this.idTipoVersamento = this.versamentoToSave.tipo.idTipoVersamento;
                            this.ricercaVersamentiRequest = new RicercaVersamentiRequest( this.idAnagSogg, this.versamentoToSave.annualita.toString(),
                                this.versamentoToSave.mese, this.provinciaConsumo.id, this.versamentoToSave.tipo.idTipoVersamento );
                            //this.reInitVersamentiSoggetto();
                            this.changeFilter()
                            this.nuovoVersamento = false;
                            this.updateVersamento = false;
                            this.isSaveDisabled = false;
                        }, err => {
                            this.logger.error( 'errore ' );
                        } );
                }, 
                err => {
                    if (err instanceof ExceptionVO && err.status == '200') {
                        console.log("Errore gestito");
                        this.showMessageError = true;
                        this.messageError = err.message;
                    } else{
                        this.nuovoVersamento = false;
                        this.updateVersamento = false;                  
                    }
                    this.isSaveDisabled = false;
                    this.logger.error( 'errore salvataggio versamento' );
                });
            }
  }
  
  onClickModifica() {    
    if (this.versamentoToSave.note.length > 255) {
        this.utilityService.getMessageByKey(MessageEnum.SIZENOTE)
            .subscribe(msg => {
               this.showMessageError = true;
               this.messageError = msg.message || '';
                    }, error => {
                        this.logger.error(error);
                    });
    }else{
        this.versamentoToSave.dataVersamento =  new Date(this.dataVersamento.year, this.dataVersamento.month - 1, this.dataVersamento.day);      
        this.subscribers.confermaModificaVersamento = this.anagraficaSoggettiService.confermaModificaVersamento( this.versamentoToSave )
            .subscribe( resp => {
                this.subscribers.provinciaBySigla = this.luoghiService.provinciaBySigla( this.versamentoToSave.provincia ).
                subscribe( resp => {
                    this.provinciaTrovata = resp;
                    this.provinciaConsumo = this.provinciaTrovata;                  
                    this.anno = this.versamentoToSave.annualita.toString();
                    //this.anagraficaSoggettiService.annoDichiarazione = this.versamentoToSave.annualita.toString();
                    this.mese = this.versamentoToSave.mese;
                    this.loadAnniVersamento();                    
                    this.loadAnniVersamentoPerRicerca();
                    this.loadMesiVersamento();
                    this.loadProvinceVersamenti();
                    this.ricercaVersamentiRequest = new RicercaVersamentiRequest( this.idAnagSogg, this.versamentoToSave.annualita.toString(),
                            this.versamentoToSave.mese, this.provinciaConsumo.id, this.versamentoToSave.tipo.idTipoVersamento );
                    console.log('Anno '+this.anno);
                    this.nuovoVersamento = false;
                    this.updateVersamento = false;
                    //this.reInitVersamentiSoggetto();
                    this.changeFilter();
                    this.isSaveDisabled = false;
                }, err => {
                    this.logger.error( 'errore ' );
                } );
            }, err => {
                this.logger.error( 'errore salvataggio versamento' );
                if (err instanceof ExceptionVO && err.status == '200') {
                    console.log("Errore gestito");
                    this.showMessageError = true;
                    this.messageError = err.message;
                } else{
                    this.nuovoVersamento = false;
                    this.updateVersamento = false;
                }
                this.isSaveDisabled = false;
           
            } );

    }

     

  }
  
  goBackVersamenti() {
      this.updateVersamento = false
      this.nuovoVersamento = false;
      this.versamentoToSave = new VersamentiPrVO(null, null, null, null, '', '' ,new TipoVersamentiVO(0,'',''),null,null,null,'',null,0,false,null); 
      this.loadAnniVersamento();
      this.loadAnniVersamentoPerRicerca();
      //this.reInitVersamentiSoggetto();
      this.changeFilter();
    }
  
  goExcel(){   
    console.log('Scarica Excel');
    this.loaderExcel = true;
    //this.anagraficaSoggettiService.annoDichiarazione = this.anno;
    var itemVersamentiReportList: Array<ItemVersamentiReport> = new Array<ItemVersamentiReport>();
    this.elencoVersamentiFinal.forEach(versamento => {
        itemVersamentiReportList.push(new ItemVersamentiReport(this.anno,versamento.mese,
                                                               versamento.provincia,
                                                               versamento.tipo.denominazione,
                                                               versamento.importo, versamento.importo_prec,
                                                               versamento.differenza, versamento.note));
    });
    this.subscribers.scaricaExcelElencoSoggetti = this.anagraficaSoggettiService.scaricaExcelElencoVersamenti(this.provinciaConsumo.id, +this.idTipoVersamento, (this.mese=="Tutti"?null:this.mese), itemVersamentiReportList)
      .subscribe(
         res => {
           this.loaderExcel = false;
           saveAs(res, "export_elenco_versamento_"+ this.anno +".xls");
           
         },
         err => {
             this.logger.error("errore in download excel");
         }
     );
  }
  
  compareFn(c1: any, c2:any): boolean {     
      return c1 && c2 ? c1.id === c2.id : c1 === c2; 
 }

}
