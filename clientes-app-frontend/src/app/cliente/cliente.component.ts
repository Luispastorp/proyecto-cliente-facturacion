import { Component, OnInit } from '@angular/core';
import {Cliente} from './cliente';
import { ClienteService } from './cliente.service';
import { ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';
import { ModalService } from './detalle/modal.service';
import { AuthService } from '../usuarios/auth.service';

@Component({
  selector: 'app-cliente',
  templateUrl: './cliente.component.html'
})
export class ClienteComponent implements OnInit {

  clientes: Cliente[];
  paginador: any;
  clienteSeleccionado: Cliente;

  constructor(private clienteService: ClienteService,
    private activatedRoute: ActivatedRoute,
    public authService: AuthService,
    private modalService: ModalService) { }

  ngOnInit(): void {

    this.activatedRoute.paramMap.subscribe( params => {
      let page:number = +params.get('page');

      if(!page){
        page=0;
      }
    this.clienteService.getClientes(page).subscribe(
      response => {
        this.clientes = response.content as Cliente[]
        this.paginador = response;
      });

    }
    );

  }

  delete(cliente: Cliente): void{
    Swal.fire({
      title: 'Estas seguro?',
      text: `seguro que desea eliminar al cliente ${cliente.nombre} ${cliente.apellido}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!'
    }).then((result) => {
      if (result.isConfirmed) {

        this.clienteService.delete(cliente.id).subscribe( response =>{
          this.clientes = this.clientes.filter(cli => cli != cliente)
          Swal.fire(
            'Cliente Eliminado!',
            `Cliente ${cliente.nombre} elimnado con exito.`,
            'success'
          )
        })

      }
    })
  }

  abrirModal(cliente: Cliente){
    this.clienteSeleccionado = cliente;
    this.modalService.abrirModal();
  }

}
