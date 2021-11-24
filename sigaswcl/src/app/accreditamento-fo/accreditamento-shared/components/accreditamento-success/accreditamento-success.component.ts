import { Component, OnInit, Input } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router"; 
import { DestroySubscribers } from "../../../../core/commons/decorator/destroy-subscribers";

@Component({
    selector: 'app-accreditamento-success',
    templateUrl: './accreditamento-success.component.html',
})
@DestroySubscribers()
export class AccreditamentoSuccessComponent implements OnInit {

    messageAccreditamento: string;
    public subscribers: any = {};
    ngOnInit(): void {
        this.subscribers.route = this.route.queryParams.subscribe(queryParams => {
            this.messageAccreditamento = queryParams["message"];
            if (!this.messageAccreditamento) {
                this.messageAccreditamento = "Accreditamento avvenuto con successo";
            }
        });
    }

    constructor(private route: ActivatedRoute, private router: Router) {

    }
}