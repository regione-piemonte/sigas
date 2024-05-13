import { Component, OnInit, Inject, HostListener } from '@angular/core';
import { Router } from '@angular/router';
import { DOCUMENT } from '@angular/common';
import { ProfilaturaVO } from '../../commons/vo/profilatura-vo';
import { UserService } from '../../services/user.service';
import { LoggerService } from '../../services/logger.service';
import { ConfigService } from '../../services/config.service';
import { BaseMenu, SubMenu, Roles } from '../../commons/commons-core';
import { RoutingImpostazione } from '../../../commons/routing';
import { RoutingAccreditamento } from '../../../commons/routing';
import { RoutingAccreditamentoFO } from '../../../commons/routing';
import { RoutingDocumentazioneFO } from '../../../commons/routing';
import { RoutingDocumentazioneBO } from '../../../commons/routing';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
}) 
export class HeaderComponent implements OnInit {

  public profilaturaVO: ProfilaturaVO;
  public baseMenu: Array<BaseMenu>;
  public subMenu: Array<SubMenu>;

showMessage: boolean;
message: string;
levelMessage:string;
 
  constructor( 
    private userService: UserService,
    private config: ConfigService,
    private logger: LoggerService,
    private router: Router,
    @Inject(DOCUMENT) private document: any
  ) { }

  @HostListener('window:keydown.enter', ['$event'])
  onKeydown(event: KeyboardEvent) {
    console.log('>>>>>>MARTS>>>>>' + event.key);
   // var marts =  document.getElementById("martsFocus");
   //marts.focus();
  }

  ngOnInit() { 
    this.profilaturaVO = new ProfilaturaVO([],"","","",false,false,false,false,false,false,false,"","",false,"");
    this.userService.getProfilatura();
    this.userService.profilatura$.subscribe(data => {
      this.profilaturaVO = data;
      this.initMenu(data);
    },
      (err) => {
        this.logger.error("Errore recupero Servizio Profilatura");
      }
    )
  }

  logOut() {
    this.userService.logOut().subscribe(data => {
      this.logger.info("logout success");
      sessionStorage.clear();
      this.document.location.href = this.config.getSSOLogoutURL(location.hostname);
    },
      err => {
        this.logger.error("error logout");
      }
    )
  }

  initMenu(profilaturaVO: ProfilaturaVO): void {
    this.logger.info("Profilatura nav bar response OK");

    let useCase: Array<String> = profilaturaVO.useCase;
    let primoAccesso = profilaturaVO.flagPrimoAccesso;

    this.baseMenu = new Array<BaseMenu>();
    this.subMenu = new Array<SubMenu>();
    
    if(profilaturaVO.abilitaUtenteRegione){
        // MENU ANAGRAFICA
        if (useCase.filter(x => {return x === Roles.HOME})) {
          this.baseMenu.push("ANAGRAFICA");
        }
        // MENU IMPOSTAZIONI
        if (useCase.filter(x => {return x === Roles.HOME})) {
          this.baseMenu.push("IMPOSTAZIONI");
        }
        
        // if (useCase.find(x => x === Roles.HOME)) {
        if (useCase.filter(x => {return x === Roles.HOME})) {
        // if (profilaturaVO.importazioneUTF)
          this.subMenu.push(new SubMenu(RoutingImpostazione.IMPORTAZIONE_UTF, 'IMPORTAZIONE_UTF', false));
        // else
          // this.subMenu.push(new SubMenu("", 'IMPORTAZIONE_UTF', true));
        // if (profilaturaVO.aliquote)
          this.subMenu.push(new SubMenu(RoutingImpostazione.ALIQUOTE, 'ALIQUOTE', false));
        // else
        //   this.subMenu.push(new SubMenu("", 'ALIQUOTE', true));
        // if (profilaturaVO.tassiInteresse)
          this.subMenu.push(new SubMenu(RoutingImpostazione.TASSI_INTERESSE, 'TASSI_INTERESSE', false));
        // else
        //   this.subMenu.push(new SubMenu("", 'TASSI_INTERESSE', true));
          
      }
        
        if (useCase.filter(x => {return x === Roles.HOME})) {
            this.baseMenu.push("ELENCO_RICHIESTE");
          }
        
        if (useCase.filter(x => {return x === Roles.HOME})) {
            this.baseMenu.push("DOCUMENTAZIONE_IN_ARRIVO");
            this.subMenu.push(new SubMenu(RoutingDocumentazioneBO.DOCUMENTI_IN_ARRIVO, "DOCUMENTI_IN_ARRIVO", false));
          }
    }
    
    if(profilaturaVO.privatoNonAccareditato){
        this.baseMenu.push("ACCREDITAMENTO");
        this.baseMenu.push("STATO_RICHIESTA");
        
        this.subMenu.push(new SubMenu(RoutingAccreditamentoFO.INSERIMENTO_OPERATRE_SERVIZI, "INSERIMENTO_OPERATORE_SERVIZI", false));
        this.subMenu.push(new SubMenu(RoutingAccreditamentoFO.MODIFICA_ACCREDITAMENTO_ELENCO, "MODIFICA_ACCREDITAMENTO", false));
//        this.subMenu.push(new SubMenu(RoutingAccreditamentoFO.RICERCA_PRATICHE, "RICERCA_PRATICHE", false));     
        this.subMenu.push(new SubMenu(RoutingAccreditamentoFO.ELENCO_PRATICHE, "RICERCA_PRATICHE", false));     
    }
  
    if(profilaturaVO.abilitaPrivato){
        this.baseMenu.push("ACCREDITAMENTO");
        this.baseMenu.push("STATO_RICHIESTA");
        this.baseMenu.push("PAGAMENTO_FO");
        this.baseMenu.push("DOCUMENTAZIONE");
        
        this.subMenu.push(new SubMenu(RoutingDocumentazioneFO.INOLTRA_DOCUMENTAZIONE, "INOLTRA_DOCUMENTAZIONE", false));
        this.subMenu.push(new SubMenu(RoutingDocumentazioneFO.CONSULTA_DOCUMENTAZIONE, "CONSULTA_DOCUMENTAZIONE", false));
        
        this.subMenu.push(new SubMenu(RoutingAccreditamentoFO.INSERIMENTO_OPERATRE_SERVIZI, "INSERIMENTO_OPERATORE_SERVIZI", false));
        this.subMenu.push(new SubMenu(RoutingAccreditamentoFO.MODIFICA_ACCREDITAMENTO_ELENCO, "MODIFICA_ACCREDITAMENTO", false));
//        this.subMenu.push(new SubMenu(RoutingAccreditamentoFO.RICERCA_PRATICHE, "RICERCA_PRATICHE", false));    
        this.subMenu.push(new SubMenu(RoutingAccreditamentoFO.ELENCO_PRATICHE, "RICERCA_PRATICHE", false));     
        //TO-DO Aggiungere pagamenti
    }

    // xmf: MENU FO
//    if(useCase.filter(x => {return x === Roles.HOME_FO}).length)
//      this.baseMenu.push("PAGAMENTO_FO");
//    
    this.message=profilaturaVO.messaggioWarning;
    this.showMessage=profilaturaVO.showMessage;
    this.levelMessage=profilaturaVO.levelMessage;
    if(this.showMessage){
        this.timerShowMessage();
    }
      
    console.log("I ruoli sono: "+profilaturaVO.ruoli);
  }
  
  timerShowMessage() {
      let intervalId: number = 0;
      let seconds: number = 8;
      intervalId = window.setInterval(() => {
          seconds -= 1;
          if (seconds === 0) {
              this.showMessage = false;
              clearInterval(intervalId);
          }
      }, 1000);
  }

  //RENDE VISIBILE IL MENU BASE
  abilitatoBaseMenu(menu: string) {
    if (this.baseMenu !=null && this.baseMenu.find(x => x === menu))
      return true;
    else
      return false;
  }

  //DISABILITA IL SUB MENU 
  disableSubMenu(submenu: string) {
    let sub: Array<SubMenu> = this.subMenu.filter(x => {
      return x.name === submenu;
    });
    if (sub != undefined && sub != null && sub.length == 1)
      return sub[0].disable;

    this.logger.error("sub menu non trovato: " + submenu);

    return true;
  }

  //NAVIGAZIONE SUB MENU
  navigate(submenu: string) {
    for (let sub of this.subMenu) {
      if (sub.name === submenu && sub.disable == false)
        this.router.navigateByUrl(sub.routing);
    }
  }

}
