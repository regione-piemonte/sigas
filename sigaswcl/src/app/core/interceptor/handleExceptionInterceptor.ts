import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/timeout';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import { ConfigService } from '../services/config.service';
import { LoggerService } from '../services/logger.service';
import { MessageRestError, Esito } from '../commons/commons-core';
import { Router } from '@angular/router';
import { ExceptionVO } from '../commons/vo/exceptionVO';


@Injectable()
export class HandleExceptionInterceptor implements HttpInterceptor {
    
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req).timeout(this.config.getTimeout() * 1000)
            .map((event: HttpEvent<any>) => {
                if (event instanceof HttpResponse) {
                    this.loggerService.info('EVENT RESPONSE:', event);
                    if (event.body.hasOwnProperty('esito') && event.body.esito == Esito.ERROR) {
                        let ex: ExceptionVO = this.parseObjectReturn(event.body.object);//JSON ONJECT
                        throw ex;
                    }
                    if (event.body.hasOwnProperty('esito') && event.body.esito == Esito.SUCCESS) {
                        event = event.clone({ body: event.body.object }) //JSON OBJECT
                    }
                }
                return event;

            })
            .catch((err: any, caught) => {
                //CASISTICA ERRORI DI RISPOSTA
                if (err instanceof HttpErrorResponse) {
                    if (err.status === 400) {
                        this.loggerService.error("400 : Errore di sintassi errata");
                        return Observable.throw(this.parseObjectReturn(err.error.object));
                    }
                    else if (err.status === 401) {
                        this.loggerService.error("401: La richiesta richiede autenticazione ");
                        this.router.navigate(['/errorrest'], { queryParams: { err: err.status, message: MessageRestError.NOTAUTHENTICATED }, skipLocationChange: true });
                    }
                    else if (err.status === 403) {
                        this.loggerService.error("403:Non si autorizzati ad accedere alla servizio rest chiamato");
                        if (err.error != null && err.error.object != null && err.error.object.errorCode == "ADOL")
                            this.router.navigate(['/usernotregistered'], { queryParams: { err: err.status, message: MessageRestError.UNAUTHORIZED }, skipLocationChange: true });
                        else if (err.error != null && err.error.object != null && err.error.object.errorCode == "SNV")
                            this.router.navigate(['/errorrest'], { queryParams: { err: err.status, message: err.error.object.message }, skipLocationChange: true });
                        else
                            this.router.navigate(['/errorrest'], { queryParams: { err: err.status, message: MessageRestError.UNAUTHORIZED }, skipLocationChange: true });
                    }
                    else if (err.status === 404) {
                        this.loggerService.error("404:Il server non ha trovato nulla che corrisponda all'URI di richiesta.");
                        this.router.navigate(['/errorrest'], { queryParams: { err: err.status, message: MessageRestError.NOTFOUND }, skipLocationChange: true });
                    }
                    else if (err.status === 409) {
                        this.loggerService.error("409: I dati sono stati modificati o cancellati da un'altra transazione.");
                        this.router.navigate(['/errorrest'], { queryParams: { err: err.status, message: MessageRestError.CONFLICT }, skipLocationChange: true });
                    }
                    else if (err.status >= 500) {
                        this.loggerService.error("500+:Il server ha riscontrato una condizione imprevista che gli ha impedito di soddisfare la richiesta.");
                        this.router.navigate(['/errorrest'], { queryParams: { err: err.status, message: MessageRestError.GENERIC }, skipLocationChange: true });
                    }
                    else {
                        this.loggerService.error("Il codice di errore ritornato non è conosciuto dal FE:" + err);
                        this.router.navigate(['/errorrest'], { queryParams: { err: "undefined", message: MessageRestError.GENERIC }, skipLocationChange: true });
                    }
                    return Observable.throw(err);
                }
                //CASISTICA 200 CON ERROR
                else if (err instanceof ExceptionVO) {
                    //POPUP
                    return Observable.throw(err);
                }
                else if (err.name == "TimeoutError") {
                    this.loggerService.error("Si è verificato un timeout");
                    return Observable.throw(new ExceptionVO("599", "Si è verificato un timeout", "TMO"));
                }
                else {
                    this.loggerService.error("L'errore ritornato non è conosciuto dal FE:" + err);
                    this.router.navigate(['/errorrest'], { queryParams: { err: err.status, message: MessageRestError.GENERIC }, skipLocationChange: true });
                }
            });

    }

    private parseObjectReturn(object: any): ExceptionVO {
        if (typeof (object) === 'string' || object instanceof String) {
            object = JSON.parse(object.toString());
            if (object.hasOwnProperty('esito') && object.esito == Esito.ERROR) {
                object = object.object;
            }

        }
        let status: string = object.status;
        let message: string = object.message;
        let errorCode: string = object.errorCode;
        return new ExceptionVO(status, message, errorCode);
    }

    constructor(private config: ConfigService, private loggerService: LoggerService, private router: Router) { }


}