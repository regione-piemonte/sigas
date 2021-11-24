import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {LoggerService} from '../logger.service';
import {ConfigService} from '../config.service';
import {UtilityCtrlVO} from './vo/utilityCtrlVO';
import {MessageEnum} from './enum/messageEnum';

@Injectable({
    providedIn: 'root'
})
export class UtilityService {

    static CONFERMA_LETTERA_MSG = 'confermaCreazioneLetteraRisp';
    static CONFERMA_INDIETRO_PROT_LETTERA_RISP_MSG = 'confermaIndietroProtLetteraRisp';
    private RETRIEVE_MESSAGE_API = '/utility/retriveMessageAndParameters';

    constructor(private httpClient: HttpClient, private logger: LoggerService, private config: ConfigService) {
    }

    /**
     * Get message from key.
     * @param messageKey
     */
    public getMessageByKey(messageKey: MessageEnum): Observable<UtilityCtrlVO> {
        try {
            const queryParam = new HttpParams().set('descChiaveMessaggio', messageKey);
            return this.httpClient
                .get<UtilityCtrlVO>(this.config.getBEServer() + '/rest' + this.RETRIEVE_MESSAGE_API, {params: queryParam});
        } catch (e) {
            this.logger.error(e);
        }
    }

}
