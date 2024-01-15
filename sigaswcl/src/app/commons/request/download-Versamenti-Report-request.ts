import { ItemVersamentiReport } from '../request/ItemVersamentiReport';

export class DownloadVersamentiReport {

    constructor(
        public id_anag: number,
        public denominazioneAnag: string,
        public anno: string,
        public id_provincia: number,
        public id_tipo_versamento: number,
        public mese: string,
        public itemVersamentiReportList: Array<ItemVersamentiReport>
    ) { }
}