import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appPhoneFormatter]'
})
export class PhoneFormatterDirective {

  constructor(private element: ElementRef) { }

  @HostListener('input', ['$event']) onInput(event: InputEvent): void{
    const inputElement = this.element.nativeElement as HTMLInputElement;
    let value = inputElement.value;
    value = value.replace(/[^0-9-]/g, '');
    value = value.replace(/^-+/, '');
    if(value.length >= 12){
      value = value.replace(/(\d{3})-?(\d{3})-?(\d{0,4})/, '$1-$2-$3');
      value = value.substring(0, 12);
    }
    else if(value.length >= 8){
      value = value.replace(/(\d{3}-\d{3})(\d)/, '$1-$2');
    }
    else if(value.length >= 4){
      value = value.replace(/(\d{3})(\d)/, '$1-$2');
    }



    // if(value.length >= 12){
    //   value = value.substring(0, 12);
    // }

    inputElement.value = value;
  }
}