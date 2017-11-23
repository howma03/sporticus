import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'appendOrdinal'
})
export class AppendOrdinalPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    let result: string = value;
    if (isNaN(value) || value < 1) {
      result = '';
    } else if (value % 100 == 11 || value % 100 == 12) {
      result += 'th';
    } else {
      var lastDigit = value % 10;
      if (lastDigit === 1) {
        result += 'st';
      } else if (lastDigit === 2) {
        result += 'nd';
      } else if (lastDigit === 3) {
        result += 'rd';
      } else if (lastDigit > 3) {
        result += 'th';
      }
    }
    return result;
  }
}
