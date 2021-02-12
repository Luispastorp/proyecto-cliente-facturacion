import { Component, OnInit } from '@angular/core';
import {Cliente} from './cliente';
import { ClienteService } from './cliente.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cliente',
  templateUrl: './cliente.component.html',
  styleUrls: ['./cliente.component.css']
})
export class ClienteComponent implements OnInit {

  clientes: Cliente[];

  constructor(private clienteService: ClienteService) { }

  ngOnInit(): void {

    this.clienteService.getClientes().subscribe(
      clientes => this.clientes = clientes
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

}