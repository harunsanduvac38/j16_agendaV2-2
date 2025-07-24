package com.cursogetafe.agenda.negocio;

import java.io.IOException;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import com.cursogetafe.agenda.modelo.Contacto;
import com.cursogetafe.agenda.persistencia.ContactoDao;
import com.cursogetafe.agenda.persistencia.ContactoDaoJDBC;






public class AgendaImpl implements Agenda {
	
	private ContactoDao cDao;
	
	public AgendaImpl() {
		//cDao = new ContactoDaoMem();
		//cDao= new ContactoDaoMemSerial();
		cDao =new ContactoDaoJDBC();
	}

	@Override
	public void insertarContacto(Contacto c) {
		cDao.insertar(c);
		
	}

	@Override
	public Contacto eliminar(int idContacto) {
		Contacto cEliminado = cDao.buscar(idContacto);
		cDao.eliminar(idContacto);
		return cEliminado;
	}

	@Override
	public boolean eliminar(Contacto c) {
		return cDao.eliminar(c);
	}

	@Override
	public void modificar(Contacto c) {
		cDao.actualizar(c);
		
	}

	@Override
	public Set<Contacto> buscarTodos() {
		Set<Contacto> bTodoContactos = new TreeSet<Contacto>(getComparatorApodo());
		bTodoContactos.addAll(cDao.buscarTodos());
		return bTodoContactos;
	}

	@Override
	public Set<Contacto> buscarContactoPorNombre(String buscado) {
		Set<Contacto> bTodoNom = new TreeSet<Contacto>(getComparatorApodo());
		bTodoNom.addAll(cDao.buscar(buscado));
		return bTodoNom;
	}

	@Override
	public int importarCSV(String fichero) throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Contacto buscar(int idContacto) {
		return cDao.buscar(idContacto);
	}

	//un metodo que devuelva un compareitor
	//lo creamos aqui porque creo que solo lo vamos a utilizar aqui
	private Comparator<Contacto> getComparatorApodo(){
		//lo hacemos con una expresion lamdam
		return (c1, c2) -> {
			Collator col = Collator.getInstance(new Locale("es"));
			return col.compare(c1.getApodo(), c2.getApodo());
		};
	}
}
