package com.cursogetafe.agenda.modelo;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "contactos")
public class Contacto implements Comparable<Contacto>,Cloneable ,Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idContacto;
	private String nombre;
	private String apellidos;
	private String apodo;
	@Embedded
	@AttributeOverride(name = "tipoVia", column = @Column(name = "tipo_via"))
	@AttributeOverride(name= "codigoPostal", column = @Column(name = "codigo_postal"))
	private Domicilio dom;
	
	@ElementCollection
	@CollectionTable(name = "telefonos", joinColumns = {@JoinColumn(name= "fk_contacto")})
	@Column(name ="telefono")
	private Set<String> telefonos;
	
	@ElementCollection
	@CollectionTable(name = "correos", joinColumns = {@JoinColumn(name= "fk_contacto")})
	@Column(name ="correo")
	private Set<String> correos;
	
	public Contacto() {
		telefonos = new LinkedHashSet<String>(); //para que se guarde el orden de carga y que el primero sea el principal
		correos = new LinkedHashSet<String>();// para que se guarde todos los correos por orden de carga 
		dom = new Domicilio();
	}
	
	public Contacto (int idContacto,String nombre,String apellidos,String apodo,Domicilio dom){
		this();//este hace un llamada al construtor por defecto que es el de arriba de los tel y los correos
		this.idContacto = idContacto;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.apodo = apodo;
		this.dom = dom;
			
	}
	
	
	
	public int getIdContacto() {
		return idContacto;
	}
	
	
	public void setIdContacto(int idContacto) {
		this.idContacto = idContacto;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getApodo() {
		return apodo;
	}
	public void setApodo(String apodo) {
		this.apodo = apodo;
	}
	public Domicilio getDom() {
		return dom;
	}
	public void setDom(Domicilio dom) {
		this.dom = dom;
	}
	public Set<String> getTelefonos() {
		return telefonos;
	}
	public void setTelefonos(Set<String> telefonos) {
		this.telefonos = telefonos;
	}
	
	public void addTelefonos(String... telefonos) { //vamos hacer una array de telefonos para simplificar 
		for (String telefono : telefonos) {
			this.telefonos.add(telefono);
		}
	}
	
	
	public Set<String> getCorreos() {
		return correos;
	}
	public void setCorreos(Set<String> correos) {
		this.correos = correos;
	}
	
	public void addCorreos(String... correos) { //vamos hacer un array de correos para simplificar 
		for (String correo : correos) {
			this.correos.add(correo);
			
		}
	}
	@Override
	public int hashCode() {
		return idContacto;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contacto other = (Contacto) obj;
		return idContacto == other.idContacto;
	}

	@Override
	public String toString() {
		return "Contacto [" + idContacto + ", " + nombre + ", " + apellidos + ", "
				+ apodo + ", " + dom + ", " + telefonos + ", " + correos + "]";
	}

	@Override  //orden natural
	public int compareTo(Contacto o) {
		return this.idContacto - o.idContacto;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException { //metodo clon devuelve una copia identica de el 
		return new Contacto(idContacto,nombre,apellidos,apodo,dom);
	}
	
	
	
}
