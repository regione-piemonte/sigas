import { AllegatoFieldVO } from "../vo/allegato-field-vo";

export class SalvaAllegatoRequest {
    public file: File;
    public filename: string;
    public idTipoAllegato: number;
    public allegatoField: Array<AllegatoFieldVO>; //i metedati
}