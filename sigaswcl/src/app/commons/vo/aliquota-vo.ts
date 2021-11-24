import { TipoAliquotaVO } from './tipo-aliquota-vo';

export class AliquotaVO {

    constructor(
        public aliquota: number,
        public id: number,
        public progRigo: string,
        public tipoAliquote: TipoAliquotaVO,
        public validitaEnd: Date,
        public validitaStart: Date,
        public version: number,
        public modificabile: boolean
    ) { }
}
