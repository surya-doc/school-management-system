import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'phonenoFormatter'
})
export class PhonenoFormatterPipe implements PipeTransform {

  transform(value: string): string {
    if(!value){
      return '';
    }
    value = value.replace(/\D/g, '');
    const formatedVal = value
    .replace(/^(\d{0,3})(\d{0,3})(\d{0,4})/, '$1-$2-$3')
    .replace(/--/g, '-');
    return formatedVal;
  }

}
