import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'lineCode'
})
export class LineCodePipe implements PipeTransform {
  private list = ['ADSL', 'Básica', 'RDSI'];
  transform(value: number): string {
    return this.list[value];
  }

}
