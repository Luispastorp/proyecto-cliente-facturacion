import { Injectable } from '@angular/core';
import { Cliente } from './cliente';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  urlCliente: string = 'http://localhost:8080/clientes';

  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  getClientes(): Observable<Cliente[]>{
    //return of(clientesJson);
    return this.http.get<Cliente[]>(this.urlCliente);
  }

  create(cliente: Cliente) : Observable<Cliente>{
    return this.http.post<Cliente>(this.urlCliente, cliente, {headers: this.httpHeaders});
  }

  getCliente(id: number): Observable<Cliente>{
    return this.http.get<Cliente>(`${this.urlCliente}/${id}`);
  }

  update(cliente: Cliente): Observable<Cliente>{
    return this.http.put<Cliente>(`${this.urlCliente}/${cliente.id}`, cliente, {headers: this.httpHeaders});
  }

  delete(id: number): Observable<Cliente>{
    return this.http.delete<Cliente>(`${this.urlCliente}/${id}`, {headers: this.httpHeaders});
  }

}
