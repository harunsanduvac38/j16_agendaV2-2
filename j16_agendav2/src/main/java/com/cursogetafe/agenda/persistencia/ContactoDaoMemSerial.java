package com.cursogetafe.agenda.persistencia;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.cursogetafe.agenda.modelo.Contacto;
import com.cursogetafe.agenda.util.Contactos;

public class ContactoDaoMemSerial implements ContactoDao {
	
	//vamos a guardar los datos en un map
	
	private Map<Integer, Contacto> almacen;
	private int proximoId;
	
	
	private final String FICH_ALM = "almacen.dat";
	private final String FICH_ID = "id.dat";
		
	//hacemos el constructor
	public ContactoDaoMemSerial() {
//		almacen = new HashMap<Integer, Contacto>();
//		proximoId = 1; //para que se valla incrementando
//		cargaInicial();
		leerFicheros();
	}
	
	private void cargaInicial()
	{
		for(Contacto c :Contactos.generaContactos()) {
			insertar(c);
		}
		grabar();
	}

	@SuppressWarnings("unchecked")
	private void leerFicheros() {
		try(FileInputStream fisAlm= new FileInputStream(FICH_ALM);
				FileInputStream fisId= new FileInputStream(FICH_ID)){
			ObjectInputStream oisAlm = new ObjectInputStream(fisAlm);
			ObjectInputStream oisId = new ObjectInputStream(fisId);
			
			almacen =(Map<Integer, Contacto>) oisAlm.readObject();//readObject este lo va a deserializar y meterlo en el mapa que es par anosotros en el almacen 
			proximoId =(Integer) oisId.readObject(); //readInt ya nos devuelve un entero
			
		}catch (FileNotFoundException e) { //este va  saltar cuando los fiecheros no existan si existe se cargan con los datos guardados en leerficheros
			almacen = new HashMap<Integer, Contacto>();
			proximoId = 1; //para que se valla incrementando
			cargaInicial();
		}
		catch (Exception e) {
			e.printStackTrace();
		throw new RuntimeException();
		}
	}
	
	private void grabar() {
		try(FileOutputStream fosAlm= new FileOutputStream(FICH_ALM);
			FileOutputStream fosId = new FileOutputStream(FICH_ID)){
			
			//creamos dos oupenSrin difernetes
			ObjectOutputStream oosAlm = new ObjectOutputStream(fosAlm);
			ObjectOutputStream oosId = new ObjectOutputStream(fosId);
			
			oosAlm.writeObject(almacen); //grabamos dos objetos de la memoria 
			oosId.writeObject(proximoId);
		
		}catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException();
		
		}
		
	}

	@Override
	public void insertar(Contacto c) {
		c.setIdContacto(proximoId++);
		almacen.put(c.getIdContacto(), c);
		grabar();
		
	}

	@Override
	public void actualizar(Contacto c) {
			almacen.replace(c.getIdContacto(), c);
			grabar();
		}
		

	@Override
	public boolean eliminar(int idContacto) {  //Es el método que recibe un número (el id del contacto que queremos eliminar) y devuelve true si lo elimina, o false si no lo encuentra.
		Contacto eliminado=almacen.remove(idContacto); //hay que hacerlo asi porqie aunqtes solo 
		grabar();
		return eliminado != null; 
		//return almacen.remove(idContacto) != null; //se remueva directamente por clave 
	
	}

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
