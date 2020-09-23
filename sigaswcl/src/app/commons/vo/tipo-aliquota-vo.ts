import { TipoConsumoVO } from './tipo-consumo-vo';

export class TipoAliquotaVO {

    constructor(
        public idTipoAliquota: number,
        public descrizione: string,
        public nomeAliquota: string,
        public nuovoAllacciamento: boolean,
        public tipo: string,
        public tipoConsumo: TipoConsumoVO
) { }
}
