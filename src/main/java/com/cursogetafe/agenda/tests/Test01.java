package com.cursogetafe.agenda.tests;

import com.cursogetafe.agenda.modelo.Contacto;
import com.cursogetafe.agenda.util.Contactos;

public class Test01 {

	public static void main(String[] args) {
		
	for(Contacto c : Contactos.generaContactos()) {
		System.out.println(c);
	}
	}

}
