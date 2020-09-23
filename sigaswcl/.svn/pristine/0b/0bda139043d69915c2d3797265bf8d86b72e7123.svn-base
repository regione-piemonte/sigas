import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'provincia'
})
export class ProvinciaPipe implements PipeTransform {

  transform(accertamenti: any, provinciaSelezionata: any): any {
    if (provinciaSelezionata === undefined || provinciaSelezionata === 'all') {
      return accertamenti;
    }

    return accertamenti.filter(accertamento => {
      return accertamento.provincia.toLowerCase().includes(provinciaSelezionata.toLowerCase());
    });
  }

}
