package com.cursogetafe.agenda.config;

import com.cursogetafe.agenda.negocio.Agenda;
import com.cursogetafe.agenda.negocio.AgendaImpl;
import com.cursogetafe.agenda.persistencia.ContactoDao;
import com.cursogetafe.agenda.persistencia.ContactoDaoJDBC;
import com.cursogetafe.agenda.persistencia.ContactoDaoJPA;
import com.cursogetafe.agenda.persistencia.ContactoDaoMem;
import com.cursogetafe.agenda.persistencia.ContactoDaoMemSerial;

public class BeanFactory {
	
	public static ContactoDao getContactoDao() {
		
		String tipoDao = Config.getProp().getProperty("dao");
		return switch (tipoDao ) {
			case "mem" -> new ContactoDaoMem();
			case "serial" -> new ContactoDaoMemSerial();
			case "jdbc" -> new ContactoDaoJDBC();
			default -> new ContactoDaoJPA();
			};
	}
	
	public static Agenda getAgenda() {
		
		String tipoAgenda = Config.getProp().getProperty("negocio");
		
		return switch (tipoAgenda) {
			default -> new AgendaImpl();
		};
	}

}
