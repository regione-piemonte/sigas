import { NuovoAllacciamentoVO } from './nuovo-allacciamento-vo';

export class ConsumiPrVO {
    public concilia: boolean;
    public scarti: boolean;
    public conguaglio_dich_prec: number;
    public rateo_dich_prec: number;

    constructor(
        public id_consumi: number,
        public annualita: number,
        public user_import: string,
        public provincia_erogazione: string,
        public data_presentazione: string,
        public stato_dich: string,
        public usi_industriali_1200: number,
        public usi_industriali_up: number,
        public usi_civili_120: number,
        public usi_civili_480: number, 
        public usi_civili_1560: number,
        public usi_civili_up: number,
        public tot_nuovi_allacciamenti: number,
        public tot_industriali: number,
        public tot_civili: number,
        public rettifiche: number,
        public arrotondamenti: number,
        public rateo_dich: number,
        public conguaglio_dich: number, 
        public conguaglio_calc: number,
        public addizionale_liquidata: number,
        public totaleCalcolato: number,
        public totaleDich: number,
        public totaleDichOrigine: number,
        public note: string,        
        public nuoviAllacciamenti: Array<NuovoAllacciamentoVO>,
        public compensazione: number
       
    ) { }
}