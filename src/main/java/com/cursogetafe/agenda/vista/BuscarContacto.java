package com.cursogetafe.agenda.vista;


import com.cursogetafe.agenda.modelo.Contacto;
import com.cursogetafe.agenda.negocio.Agenda;
import com.cursogetafe.agenda.util.Util;

public class BuscarContacto {
	Agenda agenda;
	
	public BuscarContacto(Agenda agenda) {
		this.agenda = agenda;
		init();
	}
	
	private void init() {
		int idBuscado = Util.leerInt("Ingresa id del contacto: ");
		Contacto buscado = agenda.buscar(idBuscado);
		
		if (buscado!= null) {
			System.out.println(buscado.getNombre() + "\t" + buscado.getApellidos() + 
					"\t" + buscado.getApodo() + "\t" + buscado.getTelefonos() + "\t" + buscado.getCorreos());			
		} else
			System.out.println("El contacto " + idBuscado + " no existe");
	}
	
}
