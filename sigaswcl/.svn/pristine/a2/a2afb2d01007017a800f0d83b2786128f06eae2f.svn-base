import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute, NavigationEnd, PRIMARY_OUTLET, Params } from "@angular/router";
import { LoggerService } from "../../services/logger.service";
import { filter } from 'rxjs/operators';


@Component({
  selector: 'app-breadcrumbs',
  templateUrl: './breadcrumbs.component.html',
  styleUrls: ['./breadcrumbs.component.scss']
})
export class BreadcrumbsComponent implements OnInit {

  @Input() breadcrumbsEnabled: boolean;
  
  public breadcrumbs: IBreadcrumb[];

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private logger: LoggerService
  ) { }

  ngOnInit() {
    const ROUTE_DATA_BREADCRUMB: string = "breadcrumb";

        //subscribe to the NavigationEnd event
        this.router.events.pipe(filter(event => event instanceof NavigationEnd)).subscribe(event => {
            //set breadcrumbs
            let root: ActivatedRoute = this.activatedRoute.root;
            this.breadcrumbs = this.getBreadcrumbs(root);
        }, err => {
            this.logger.error(err);
        });
  }

  private getBreadcrumbs(route: ActivatedRoute, url: string = "", breadcrumbs: IBreadcrumb[] = []): IBreadcrumb[] {
    const ROUTE_DATA_BREADCRUMB: string = "breadcrumb";

    let children: ActivatedRoute[] = route.children;

    if (children.length === 0) {
        return breadcrumbs;
    }

    for (let child of children) {
        if (child.outlet !== PRIMARY_OUTLET) {
            continue;
        }

        //verify the custom data property "breadcrumb" is specified on the route
        if (!child.snapshot.data.hasOwnProperty(ROUTE_DATA_BREADCRUMB)) {
            let url = child.snapshot.routeConfig.path;
            return this.getBreadcrumbs(child, url, breadcrumbs);
        }

        //get the route's URL segment
        let routeURL: string = child.snapshot.url.map(segment => segment.path).join("/");

        //append route URL to URL
        url += `/${routeURL}`;

        //add breadcrumb
        let breadcrumb: IBreadcrumb = {
            label: child.snapshot.data[ROUTE_DATA_BREADCRUMB],
            params: child.snapshot.params,
            url: url
        };
        breadcrumbs.push(breadcrumb);

        //recursive
        return this.getBreadcrumbs(child, url, breadcrumbs);
    }
}

}

interface IBreadcrumb {
  label: string;
  params: Params;
  url: string;

}