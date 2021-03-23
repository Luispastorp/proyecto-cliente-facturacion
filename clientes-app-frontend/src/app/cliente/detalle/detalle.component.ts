import { Component, Input, OnInit } from '@angular/core';
import { Factura } from 'src/app/facturas/models/factura';
import { FacturaService } from 'src/app/facturas/services/factura.service';
import { AuthService } from 'src/app/usuarios/auth.service';
import swal from 'sweetalert2';
import { Cliente } from '../cliente';
import { ClienteService } from '../cliente.service'
import { ModalService } from './modal.service';

@Component({
  selector: 'detalle-cliente',
  templateUrl: './detalle.component.html',
  styleUrls: ['./detalle.component.css']
})
export class DetalleComponent implements OnInit {

  @Input() cliente: Cliente;
  titulo: string = "Detalle del cliente";
  public fotoSeleccionada: File;


  constructor(private clienteService: ClienteService,
    public authService: AuthService,
    public modalService: ModalService,
    private facturaService: FacturaService) { }

  ngOnInit(): void {
  }

  seleccionarFoto(event){
    this.fotoSeleccionada = event.target.files[0];
    if(this.fotoSeleccionada.type.indexOf('image') < 0){
      swal.fire('Error al seleccionar imagen', 'El archivo debe ser del tipo imagen', 'error');
      this.fotoSeleccionada = null;
    }
  }

  subirFoto(){
    if(!this.fotoSeleccionada){
      swal.fire('Error Upload', 'Debe seleccionar una foto', 'error');
    }else{
      this.clienteService.subirfoto(this.fotoSeleccionada, this.cliente.id)
      .subscribe( cliente => {
        this.cliente = cliente;
        swal.fire('La foto se ah subido completamente', `Lafoto se ah subido con exito: ${this.cliente.foto}`, 'success');
        }
      )
    }
  }

  cerrarModal(){
    this.modalService.cerrarModal();
    this.fotoSeleccionada = null;
  }

  delete(factura: Factura): void{
    swal.fire({
      title: 'Estas seguro?',
      text: `seguro que desea eliminar la factura ${factura.ruc}`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!'
    }).then((result) => {
      if (result.isConfirmed) {

        this.facturaService.delete(factura.id).subscribe( response =>{
          this.cliente.facturas = this.cliente.facturas.filter(fact => fact != factura)
          swal.fire(
            'Factura Eliminada!',
            `Factura ${factura.ruc} elimnada con exito.`,
            'success'
          )
        })

      }
    })
  }


}
