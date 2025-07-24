package com.cursogetafe.agenda.tests;

import com.cursogetafe.agenda.modelo.Contacto;
import com.cursogetafe.agenda.persistencia.ContactoDao;
import com.cursogetafe.agenda.persistencia.ContactoDaoMem;

public class Test02Dao {

	public static void main(String[] args) {
		ContactoDao dao = new ContactoDaoMem();
		
//		for (Contacto c : dao.buscarTodos()) {
//			System.out.println(c);
//		}
		dao.buscarTodos().forEach(System.out::println);
		System.out.println();
		
		dao.buscar("al").forEach(System.out::println);
		System.out.println();

		System.out.println(dao.buscar(44));
		System.out.println();
		
		Contacto buscado = dao.buscar(88);
		buscado.setApellidos("Nuevo Apellido");
		buscado.setApodo("Cabezon");
		dao.actualizar(buscado);
		
		buscado = dao.buscar(88);
		System.out.println(buscado);
		
		dao.eliminar(88);
		buscado = dao.buscar(88);
		System.out.println(buscado);
	}

}
