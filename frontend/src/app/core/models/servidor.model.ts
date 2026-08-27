import { Secretaria } from './secretaria.model';

export interface Servidor {
  id?: number;
  nome: string;
  email: string;
  dataNascimento: string;
  secretaria?: Secretaria;
  secretariaId?: number;
}