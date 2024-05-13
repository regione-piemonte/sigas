import { AfterViewInit, Component, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { LoggerService } from '../../../core/services/logger.service';
import { AnagraficaSoggettiService } from '../../service/anagrafica-soggetti.service';
import { AnagraficaSoggettoVO } from '../../../commons/vo/soggetti-vo';
import { DichiaranteVO } from '../../../commons/vo/dichiarante-vo';
import { RimborsiComponent } from '../rimborsi/rimborsi.component';
import { AccertamentiComponent } from '../accertamenti/accertamenti.component';
import { ConsumiPrComponent } from '../consumi-pr/consumi-pr.component';

@Component({
  selector: 'app-dettaglio-soggetto',
  templateUrl: './dettaglio-soggetto.component.html',
  styleUrls: ['./dettaglio-soggetto.component.scss']
})

@DestroySubscribers()
export class DettaglioSoggettoComponent implements OnInit, AfterViewInit {

  @ViewChild(RimborsiComponent) rimborsi:RimborsiComponent;
  @ViewChild(AccertamentiComponent) accertamenti:AccertamentiComponent;
  @ViewChild(ConsumiPrComponent) consumi:ConsumiPrComponent;


  private loaderPage: boolean;
  private soggetto: AnagraficaSoggettoVO;
  private dichiarante: DichiaranteVO;
  private annoDichiarazione: string;

  public subscribers: any = {};

  constructor(
    private router: Router,
    private logger: LoggerService,
    private anagraficaSoggettiService: AnagraficaSoggettiService,
    private renderer: Renderer2
  ) { }

  ngAfterViewInit(): void {
    const element = this.renderer.selectRootElement('#idDivToContPrin');
    setTimeout(() => element.focus(), 0);
  }

  ngOnInit() {
   
    // Move on top
    window.scrollTo(0, 0);

    this.dichiarante = new DichiaranteVO(0,"");
   
    if (this.anagraficaSoggettiService.headerDichiarante != undefined && this.anagraficaSoggettiService.headerDichiarante.idAnag != undefined) {
      this.loaderPage = true;
      this.subscribers.ricercaSoggettoByID = this.anagraficaSoggettiService.ricercaSoggettoByID()    
        .subscribe(res => {
          this.soggetto = res;
          this.loaderPage = false;
          this.dichiarante = this.anagraficaSoggettiService.headerDichiarante;
          this.annoDichiarazione = this.anagraficaSoggettiService.annoDichiarazione;
        }, err => {
          this.logger.error("errore ");
          this.loaderPage = false;
      });
    } else {
      this.router.navigateByUrl(Routing.ELENCO_CONSUMI);
    }

  }

  refresh() {
    this.rimborsi.reInit();
  }

  refreshAcc() {
    this.accertamenti.reInit();
  }
  
  refreshConsumi(){
      this.consumi.reInit();
  }

  goBack() {
    this.router.navigateByUrl(Routing.ELENCO_CONSUMI);
  }

}