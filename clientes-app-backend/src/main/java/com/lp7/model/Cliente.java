package com.lp7.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "clientes")
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty
	@Size(min = 3, max = 50, message = "debe tener entre 3 y 50 caracteres")
	@Column(nullable = false, length = 50)
	private String nombre;
	
	@NotEmpty
	@Column(nullable = false, length = 50)
	@Size(min = 3, max = 50, message = "debe tener entre 3 y 50 caracteres")
	private String apellido;
	
	@NotEmpty
	@Email
	@Column(nullable = false, unique = false, length = 50)
	private String email;
	
	@NotNull
	@Column(nullable = false)
	private LocalDateTime createAt;
	
	private String foto;
	
	@PrePersist
	public void prePersist() {
		setCreateAt(LocalDateTime.now());
	}
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDateTime getCreateAt() {
		return createAt;
	}
	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

}
