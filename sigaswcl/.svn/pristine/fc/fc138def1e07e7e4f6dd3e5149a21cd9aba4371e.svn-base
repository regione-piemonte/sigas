import { Component, OnInit, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { DOCUMENT } from '@angular/platform-browser';
import { ProfilaturaVO } from '../../commons/vo/profilatura-vo';
import { UserService } from '../../services/user.service';
import { LoggerService } from '../../services/logger.service';
import { ConfigService } from '../../services/config.service';
import { BaseMenu, SubMenu, Roles } from '../../commons/commons-core';
import { RoutingImpostazione } from '../../../commons/routing';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  public profilaturaVO: ProfilaturaVO;
  public baseMenu: Array<BaseMenu>;
  public subMenu: Array<SubMenu>;

  constructor(
    private userService: UserService,
    private config: ConfigService,
    private logger: LoggerService,
    private router: Router,
    @Inject(DOCUMENT) private document: any
  ) { }

  ngOnInit() {
    this.profilaturaVO = new ProfilaturaVO([],"","","",false,false,false,false);
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
      this.document.location.href = this.config.getSSOLogoutURL();
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
    // MENU ANAGRAFICA
    if (useCase.filter(x => {return x === Roles.HOME})) {
      this.baseMenu.push("ANAGRAFICA");
    }
    // MENU IMPOSTAZIONI
    if (useCase.filter(x => {return x === Roles.HOME})) {
      this.baseMenu.push("IMPOSTAZIONI");
    }
    
    this.subMenu = new Array<SubMenu>();
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
