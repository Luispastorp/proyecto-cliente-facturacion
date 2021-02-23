import { Component, Input, OnInit } from '@angular/core';
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
    public modalService: ModalService) { }

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

}
