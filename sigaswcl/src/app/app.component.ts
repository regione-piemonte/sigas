import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import { LoggerService } from './core/services/logger.service';

const DISABLE_HEADER_FOOTER = "disableHeaderFooter";
const DISABLE_BREADCRUMBS = "disableBreadcrumbs";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy{
  
  headerEnable: boolean = true;
  breadcrumbsEnable: boolean = true;


  ngOnInit(): void {
    this.loggerService.info("create app component");
  }

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private loggerService: LoggerService) {
    //DISABILITA MENU SE SI VA NELLA PAGINA DI ERRORE
    this.router.events.pipe(
        filter(event => event instanceof NavigationEnd)
      ).subscribe(event => {
      let root: ActivatedRoute = this.activatedRoute.root;
     
      this.headerEnable = true;
      this.breadcrumbsEnable = true;
      for (let child of root.children) {
        if (child.snapshot.data.hasOwnProperty(DISABLE_HEADER_FOOTER)) {
          this.headerEnable = false;
        } else {
          this.headerEnable = true;
        }
        if (child.snapshot.data.hasOwnProperty(DISABLE_BREADCRUMBS)) {
          this.breadcrumbsEnable = false;
        } else {
          this.breadcrumbsEnable = true;
        }

      }
    });
  }

  ngOnDestroy(): void {
    this.loggerService.info("destroy app component");
  }

}
