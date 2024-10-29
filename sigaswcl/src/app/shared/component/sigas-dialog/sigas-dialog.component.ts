import {Component, Input, OnInit} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs';

//Con bootstap va fatto cosi
declare var jquery: any;
declare var $: any;

/*
* IMPORTARE COMPONENTE IN PAGINA
* AGGIUNGERE
* @ViewChild(SigasDialog) sigasDialog: SigasDialog;
* AZIONE DI SALVATAGGIO
* this.sigasDialog.salva.subscribe(data=>{
      console.log("salvataggio effettuato");
    });
* COME APRIRLO
  open() {
    this.sigasDialog.open();
  }
*/
@Component({
    selector: 'sigas-dialog',
    templateUrl: './sigas-dialog.component.html',
    styleUrls: ['./sigas-dialog.component.css']
})
export class SigasDialogComponent implements OnInit {

    @Input() testo: string;
    @Input() titolo: string;
    @Input() buttonConfirmText: string;
    @Input() buttonAnnullaText: string;
    @Input() subMessages: Array<string>;
    @Input() hideButton: boolean;
    //SUBJECT PER IL SALVATAGGIO -DA  EFFETTUARE IL SUBSCRIBE
    private saveSubject = new Subject<Boolean>();
    public salvaAction: Observable<Boolean> = this.saveSubject.asObservable();
    //SUBJECT PER IL SALVATAGGIO -DA  EFFETTUARE IL SUBSCRIBE
    private closeSubject = new Subject<Boolean>();
    public closeAction: Observable<Boolean> = this.closeSubject.asObservable();
    private XSubject = new Subject<Boolean>();
    public XAction: Observable<Boolean> = this.XSubject.asObservable();

    ngOnInit(): void {
        if (!this.titolo) {
            this.titolo = 'titolo non definito';
        }
        if (!this.buttonAnnullaText) {
            this.buttonAnnullaText = 'No';
        }
        if (!this.buttonConfirmText) {
            this.buttonConfirmText = 'Si';
        }

    }

    //APRE
    public open() {
        $('#myModal').modal('show');
    }

    //CHIUDE
    public close() {
        $('#myModal').modal('hide');
    }

    private saveS(): void {
        this.close();
        this.saveSubject.next(true);
    }

    private closeS(): void {
        this.close();
        this.closeSubject.next(true);
    }

    private closeX(): void {
        this.close();
        this.XSubject.next(true);
    }


}
