import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'annualita'
})
export class AnnualitaPipe implements PipeTransform {

  transform(annualita: any, annualitaselezionata: any): any {
    if (annualitaselezionata === undefined || annualitaselezionata === 'all') {
      return annualita;
    }

    return annualita.filter(accertamento => {
      return accertamento.annualita.toString().toLowerCase().includes(annualitaselezionata.toLowerCase());
    });
  }

}
