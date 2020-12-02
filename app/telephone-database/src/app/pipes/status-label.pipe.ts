import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'statusLabel'
})
export class StatusLabelPipe implements PipeTransform {
  transform(value: string): string {
    if (value === 'A'){
      return 'Activo';
    } else if (value === 'I'){
      return 'Inactivo';
    } else {
      return 'Desconocido';
    }
  }

}
