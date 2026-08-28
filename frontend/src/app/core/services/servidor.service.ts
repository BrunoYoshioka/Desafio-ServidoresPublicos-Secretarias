import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Servidor } from '../models/servidor.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ServidorService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/servidores`;

  listar(): Observable<Servidor[]> {
    return this.http.get<Servidor[]>(this.apiUrl);
  }

  salvar(servidor: Servidor): Observable<Servidor> {
    return this.http.post<Servidor>(this.apiUrl, servidor);
  }

  atualizar(id: number, servidor: Servidor): Observable<Servidor> {
    return this.http.put<Servidor>(`${this.apiUrl}/${id}`, servidor);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
