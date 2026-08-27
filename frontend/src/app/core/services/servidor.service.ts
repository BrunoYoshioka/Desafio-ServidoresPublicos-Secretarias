import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Servidor } from '../models/servidor.model';

@Injectable({
  providedIn: 'root'
})
export class ServidorService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/servidores';

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
