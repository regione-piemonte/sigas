import { Injectable } from "@angular/core";

/*
import { Observable } from "rxjs/Observable";
import { ImpiantoVO } from "../commons/vo/impianto-vo";
import { TipologiaPermessoVO } from "../commons/vo/tipologia-permesso-vo";
import { DichiarazioneVO } from "../commons/vo/dichiarazione-vo";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { LoggerService } from "../../core/services/logger.service";
import { AccontoVO } from "../commons/vo/acconto-vo";
import { MeseVO } from "../commons/vo/mese-vo";
import { TablePagamentoVO } from "../commons/vo/table-pagamento-vo";
import { AccontoCommonsRequest } from "../commons/request/acconto-commons-request";
import { CalcolaAccontoRequest } from "../commons/request/calcola-acconto-request";
import { SalvaDichiarazioneResponse } from "../commons/response/salva-dichiarazione-response";
import { VerificaPagamentiPredispostiResponse } from "../commons/response/verifica-pagamenti-predisposti-response";
import { SalvaAccontoRequest } from "../commons/request/salva-acconto-request";
import { TipoVersanteVO } from "../commons/vo/tipo-versante-vo";
import { TipoPagamentoVO } from "../commons/vo/tipo-pagamento-vo";
import { RiepilogoPagamentoVO } from "../commons/vo/riepilogo-pagamento-vo";
import { SalvaCarrelloResponse } from "../commons/response/salva-carrello-response";

import { RiepilogoPagamentoRequest } from "../commons/request/riepilogo-pagamento-request";
*/

@Injectable()
export class InserimentoPagamentoService {

    private _idImpianto: number;
    public get idImpianto(): number {
        return this._idImpianto;
    }
    public set idImpianto(idImpianto: number) {
        this._idImpianto = idImpianto;
    }

    private _idDichiarazioneImpianto: number;
    public get idDichiarazioneImpianto(): number {
        return this._idDichiarazioneImpianto;
    }
    public set idDichiarazioneImpianto(idDichiarazioneImpianto: number) {
        this._idDichiarazioneImpianto = idDichiarazioneImpianto;
    }

    private _anno: number;
    public get anno(): number {
        return this._anno;
    }
    public set anno(anno: number) {
        this._anno = anno;
    }

    private _optradio: number;
    public get optradio(): number {
        return this._optradio;
    }
    public set optradio(optradio: number) {
        this._optradio = optradio;
    }
}

