package com.cursogetafe.agenda.persistencia;

import java.util.Set;

import com.cursogetafe.agenda.config.Config;
import com.cursogetafe.agenda.modelo.Contacto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class ContactoDaoJPA  implements ContactoDao {

	private EntityManagerFactory emf;
	private EntityManager em;
	
	public ContactoDaoJPA() {
		emf = Config.getEmf();
	}
	
	@Override
	public void insertar(Contacto c) {
		em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(c);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		
		em.close();
	}

	@Override
	public void actualizar(Contacto c) {
		em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(c);
			em.getTransaction().commit();
		}catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		
		em.close();
		
	}

	@Override
	public boolean eliminar(int idContacto) {
		em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			
			Contacto c = em.find(Contacto.class, idContacto);
				if(c != null) {
					em.remove(c);
				}
				em.getTransaction().commit();
				return true;
			} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			return false;
		}
		finally {
			em.close();
		}
	}

	@Override
	public boolean eliminar(Contacto c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Contacto buscar(int idContacto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Contacto> buscar(String cadena) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Contacto> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
