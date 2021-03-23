import { Factura } from "./factura";
import { Producto } from "./producto";

export class ItemFactura {
  id: number;
  cantidadProducto: number = 1;
  producto: Producto;
  factura: Factura;
  importe: number;

  public calcularImporte(): number{
    return this.cantidadProducto * this.producto.precio;
  }

}
