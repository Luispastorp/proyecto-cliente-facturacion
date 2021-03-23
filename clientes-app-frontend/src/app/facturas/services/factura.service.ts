import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Factura } from '../models/factura';
import { Producto } from '../models/producto';


@Injectable({
  providedIn: 'root'
})
export class FacturaService {

  private urlFacturas: string = 'http://localhost:8080/facturas';

  private urlProductos: string = 'http://localhost:8080/productos';

  constructor(private http: HttpClient) { }

  getFactura(id: number): Observable<Factura>{
    return this.http.get<Factura>(`${this.urlFacturas}/${id}`);
  }

  delete(id: number): Observable<void>{
    return this.http.delete<void>(`${this.urlFacturas}/${id}`);
  }

  filtrarProductos(term:string):Observable<Producto[]>{
    return this.http.get<Producto[]>(`${this.urlProductos}/filtrar/${term}`);
  }

  create(factura: Factura):Observable<Factura>{
    return this.http.post<Factura>(this.urlFacturas, factura);
  }
}
