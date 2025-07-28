package com.cursogetafe.agenda.tests;

import com.cursogetafe.agenda.persistencia.ContactoDao;
import com.cursogetafe.agenda.persistencia.ContactoDaoJPA;

public class Test02Dao {

	public static void main(String[] args) {
//		ContactoDao dao2 = new ContactoDaoMem();
		ContactoDao dao = new ContactoDaoJPA();
		
		System.out.println(dao.buscar(90));
		
//		for (Contacto c : dao.buscarTodos()) {
//			System.out.println(c);
//		}
		
		
//		dao.buscar("al").forEach(System.out::println);
//		System.out.println();

//		System.out.println(dao.buscar(44));
//		System.out.println();
//		
//		Contacto buscado = dao.buscar(9);
//		buscado.setApellidos("Nuevo Apellido");
//		buscado.setApodo("Cabezon");
//		dao.actualizar(buscado);
//		
//		Set<Contacto> buscado = dao.buscar("abdu");
//		buscado.forEach(System.out::println);
//		System.out.println(buscado);
//		
//		dao.eliminar(122);
//		buscado = dao.buscar(88);
//		System.out.println(buscado);
	}

}
