import { Component, OnInit, Input } from "@angular/core";

@Component({
    selector: 'sigas-success',
    templateUrl: './sigas-success.component.html',
    styleUrls: ['./sigas-success.component.css']
})
export class SigasSuccessComponent implements OnInit {

    @Input() message;

    ngOnInit(): void {
        console.log("sono in init");
        if (!this.message) {
            this.message = "Operazione avvenuta con successo";
        }
    }
}