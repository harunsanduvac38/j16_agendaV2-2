package com.cursogetafe.agenda.vista;


import java.util.Set;

import com.cursogetafe.agenda.modelo.Contacto;
import com.cursogetafe.agenda.negocio.Agenda;

public class ConsultarTodos {
	Agenda agenda;
	
	public ConsultarTodos(Agenda agenda) {
		this.agenda = agenda;
		init();
	}
	
	private void init() {
		Set<Contacto> todos = agenda.buscarTodos();
		
		System.out.println("\nTODOS LOS CONTACTOS");
		for (Contacto contacto : todos) {
			System.out.println(contacto.getIdContacto() + "\t" + contacto.getNombre() + "\t" + contacto.getApellidos() + 
					"\t" + contacto.getApodo());
		}
	}
	
}
