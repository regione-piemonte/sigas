import { AfterViewInit, Component, OnDestroy, OnInit, Renderer2 } from "@angular/core";
import { DestroySubscribers } from "../../../core/commons/decorator/destroy-unsubscribers";
import { Router } from "@angular/router";
import { ImportazioneUTFService } from "../../service/importazione-utf.service";

@Component({
    selector: 'app-importazione-utf-tab-container',
    templateUrl: './importazione-utf-tab-container.component.html',
    styleUrls: ['./importazione-utf-tab-container.component.scss']
})  
@DestroySubscribers()
export class ImportazioneUTFTabContainerComponent implements OnInit, AfterViewInit, OnDestroy 
{

    private isDisabled: String = "disabled";
    private isDisabledCheck: boolean = true;

  /********************************************************************
   * Costruttore
   *********************************************************************/
   constructor(
        private router: Router,
        private renderer: Renderer2,                
        private importazioneUTFService: ImportazioneUTFService,              
    ) {         

        this.importazioneUTFService
            .importUTFAnnualitaSelezionataSubjectObservable
            .subscribe((annualita: Number) => {
                this._abilitaTabItems();
        });

        this.importazioneUTFService
            .importUTFAnnualitaSelezionataChangedSubjectObservable            
            .subscribe((changed: boolean) => {
                if(changed){
                    this._disabilitaTabItems();
                }                
        });

    }

   /********************************************************************
    * Funzioni associate ad Eventi
    *********************************************************************/
    ngOnDestroy(): void {
        //throw new Error("Method not implemented.");
    }
    ngAfterViewInit(): void {
        //throw new Error("Method not implemented.");
    }
    ngOnInit(): void {
        //throw new Error("Method not implemented.");
    }

   /********************************************************************
    * Funzioni private
    *********************************************************************/

   private _abilitaTabItems(){
        this.isDisabled = "marts";
        this.isDisabledCheck = false;
   }

   private _disabilitaTabItems(){
        this.isDisabled = "disabled";
        this.isDisabledCheck = true
   }
    
}