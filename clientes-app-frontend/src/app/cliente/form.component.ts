import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Cliente } from './cliente';
import { ClienteService } from './cliente.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html'
})
export class FormComponent implements OnInit {

  public titulo: string = "Crear Cliente";
  public cliente: Cliente = new Cliente();
  public errores: string[];

  constructor(private clienteService: ClienteService,
    private router: Router,
    private activateRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.cargarCliente();
  }

  create(): void{
    this.clienteService.create(this.cliente).subscribe(
      response => {
        this.router.navigate(['/clientes'])
        swal.fire('Nuevo cliente', `Cliente ${this.cliente.nombre} creado con exito`, 'success')
      },
      err => {
        this.errores = err.error.errors as string[];
        console.error(this.errores);
      }
      );
  }

  cargarCliente(): void{
    this.activateRoute.params.subscribe(params =>{
      let id = params['id']
      if (id){
        this.clienteService.getCliente(id).subscribe(
          cliente => this.cliente = cliente)
      }
    });
  }

  update(): void{
    this.clienteService.update(this.cliente).subscribe(cliente =>{
      this.router.navigate(['/clientes'])
      swal.fire('Cliente Actualizado', `Cliente ${this.cliente.nombre} actualizado con exito`, 'success')
    },
    err => {
        this.errores = err.error.errors as string[];
      }
    );
  }

}
