import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function idadeRangeValidator(min: number, max: number): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    if (!control.value) {
      return null;
    }

    const dataNascimento = new Date(control.value);
    const hoje = new Date();
    
    let idade = hoje.getFullYear() - dataNascimento.getFullYear();
    const m = hoje.getMonth() - dataNascimento.getMonth();
    
    if (m < 0 || (m === 0 && hoje.getDate() < dataNascimento.getDate())) {
      idade--;
    }

    if (idade < min || idade > max) {
      return { idadeInvalida: { min, max, idadeAtual: idade } };
    }

    return null;
  };
}
