import { Injectable } from '@angular/core';
import { Cliente } from './cliente';
import { Observable, throwError } from 'rxjs';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { map, catchError} from 'rxjs/operators';
import { Router } from '@angular/router';
import { formatDate } from '@angular/common';
import swal from 'sweetalert2';
import { Region } from './region';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  urlCliente: string = 'http://localhost:8080/clientes';

  private httpHeaders = new HttpHeaders({'Content-Type': 'application/json'});

  constructor(private http: HttpClient, private router: Router) { }

  getRegiones(): Observable<Region[]>{
    return this.http.get<Region[]>(this.urlCliente + '/regiones');
  }

  getClientes(page: number): Observable<any>{
    //return of(clientesJson);
    return this.http.get(this.urlCliente + '/page/' + page).pipe(
      map((response: any) => {
        (response.content as Cliente[]).map(cliente =>{
          cliente.nombre = cliente.nombre.toUpperCase();
          cliente.createAt = formatDate(cliente.createAt, 'medium', 'en-US');
          return cliente;
        });
        return response;
      })
    );
  }

  create(cliente: Cliente) : Observable<Cliente>{
    return this.http.post<Cliente>(this.urlCliente, cliente, {headers: this.httpHeaders}).pipe(
      catchError(e => {
        if(e.status==400){
          return throwError(e);
        }
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, "Error en la base de Datos", 'error');
        return throwError(e);
      })
    );
  }

  getCliente(id: number): Observable<Cliente>{
    return this.http.get<Cliente>(`${this.urlCliente}/${id}`).pipe(
      catchError(e => {
        this.router.navigate(['']);
        console.error(e.error.mensaje);
        swal.fire("Error al buscar al cliente", e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  update(cliente: Cliente): Observable<Cliente>{
    return this.http.put<Cliente>(`${this.urlCliente}/${cliente.id}`, cliente, {headers: this.httpHeaders}).pipe(
      catchError( e =>{
        if(e.status==400){
          return throwError(e);
        }
        console.error(e.error.mensaje);
        swal.fire(e.error.mensaje, e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  delete(id: number): Observable<Cliente>{
    return this.http.delete<Cliente>(`${this.urlCliente}/${id}`, {headers: this.httpHeaders}).pipe(
      catchError(e =>{
        console.error(e.error.mensaje);
        swal.fire("Error al eliminar", e.error.mensaje, 'error');
        return throwError(e);
      })
    );
  }

  subirfoto(archivo: File, id): Observable<Cliente>{
    let formData = new FormData;
    formData.append("archivo", archivo);
    formData.append("id", id);
    return this.http.post(`${this.urlCliente}/upload`, formData).pipe(
      map((response: any) => response.cliente as Cliente),
      catchError(e => {
        swal.fire(e.error.mensaje, "Error al subir las foto en la BD", 'error');
      return throwError(e);
      })
    );
  }

}
