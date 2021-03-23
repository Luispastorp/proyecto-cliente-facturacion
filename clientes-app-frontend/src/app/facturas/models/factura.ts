import { Cliente } from "src/app/cliente/cliente";
import { ItemFactura } from "./item-factura";

export class Factura {
  id: number;
  ruc: string;
  createAt: string;
  total: number;
  cliente: Cliente;
  items: ItemFactura[];

  calcularTotal(): number{
    this.total = 0
    this.items.forEach((item: ItemFactura) =>{
      this.total += item.calcularImporte();
    });
    return this.total;
  }

}
