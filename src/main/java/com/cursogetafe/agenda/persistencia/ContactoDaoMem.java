package com.cursogetafe.agenda.persistencia;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.cursogetafe.agenda.modelo.Contacto;
import com.cursogetafe.agenda.util.Contactos;

public class ContactoDaoMem implements ContactoDao {
	
	//vamos a guardar los datos en un map
	
	private Map<Integer, Contacto> almacen;
	private int proximoId;
	private int cant;
		
	//hacemos el constructor
	public ContactoDaoMem() {
		almacen = new HashMap<Integer, Contacto>();
		proximoId = 1; //para que se valla incrementando
		cargaInicial();
	}
	
	private void cargaInicial()
	{
		for(Contacto c :Contactos.generaContactos()) {
			insertar(c);
		}
	}

	@Override
	public void insertar(Contacto c) {
		c.setIdContacto(proximoId++);
		almacen.put(c.getIdContacto(), c);
		
	}

	@Override
	public void actualizar(Contacto c) {
			almacen.replace(c.getIdContacto(), c);
		}
		

	@Override
	public boolean eliminar(int idContacto) {  //Es el método que recibe un número (el id del contacto que queremos eliminar) y devuelve true si lo elimina, o false si no lo encuentra.
		return almacen.remove(idContacto) != null; //se remueva directamente por clave 
	}
										//otra forma
											//		if(almacen.containsKey(idContacto)) {  //Verifica si en el Map existe alguna clave con ese idContacto.
											//			almacen.remove(idContacto);		//Si existe, lo elimina. La clave y su correspondiente objeto desaparecen del Map.
											//			return true;			//Devuelve true para indicar que se eliminó correctamente.
											//		}
											//		return false;		//Devuelve false para indicar que no se eliminó porque no estaba en el Map.
											//	}

	@Override
	public boolean eliminar(Contacto c) {
		return eliminar(c.getIdContacto());
	}

	@Override
	public Contacto buscar(int idContacto) {
		return almacen.get(idContacto);
	}

	
	@Override
	public Set<Contacto> buscar(String cadena) {
		cadena = cadena.toLowerCase();
		Set<Contacto> resu = new HashSet<Contacto>();
		for (Contacto contacto : almacen.values()) {
			if(contacto.getApellidos().toLowerCase().contains(cadena)
					||contacto.getApodo().toLowerCase().contains(cadena)
					||contacto.getNombre().toLowerCase().contains(cadena)) {
				resu.add(contacto);
			}	
		}
		return resu;
	}

	
	@Override
	public Set<Contacto> buscarTodos() {
		return new HashSet<Contacto>(almacen.values());
	}

	
}
