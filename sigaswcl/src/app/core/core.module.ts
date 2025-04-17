import { NgModule, LOCALE_ID } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HandleExceptionInterceptor } from './interceptor/handleExceptionInterceptor';
import { ConfigService } from './services/config.service';
import { LoggerService } from './services/logger.service';
import { HeaderComponent } from './component/header/header.component';
import { FooterComponent } from './component/footer/footer.component';
import { BreadcrumbsComponent } from './component/breadcrumbs/breadcrumbs.component';
import { ErrorComponent } from './component/error/error.component'; 
import { SigasAlertComponent } from '../shared/component/sigas-alert/sigas-alert.component';
import { SharedModule } from '../shared/shared.module';
import { TemplateModule } from '../template/template.module';
import { DepositoCauzionaleService } from './services/depositocauzionale.service';

@NgModule({
  imports: [
SharedModule,
    CommonModule,
    RouterModule,
    TemplateModule,
    HttpClientModule
  ],
  exports: [
    HeaderComponent, 
    FooterComponent,
    BreadcrumbsComponent
  ],
  declarations: [
    HeaderComponent, 
    FooterComponent, BreadcrumbsComponent, ErrorComponent
  ],
  providers: [
    ConfigService,
    LoggerService,
    DepositoCauzionaleService,
    { provide: HTTP_INTERCEPTORS, useClass: HandleExceptionInterceptor, multi: true },
    { provide: LOCALE_ID, useValue: 'it-IT' }
  ]
})
export class CoreModule { }
