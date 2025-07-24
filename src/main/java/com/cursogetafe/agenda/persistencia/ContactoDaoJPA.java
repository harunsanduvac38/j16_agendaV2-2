package com.cursogetafe.agenda.persistencia;

import java.util.HashSet;
import java.util.Set;

import com.cursogetafe.agenda.config.Config;
import com.cursogetafe.agenda.modelo.Contacto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

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
		Contacto eliminar = em.find(Contacto.class, idContacto);
		if(eliminar != null) {	
			try {
				em.getTransaction().begin();
				em.remove(eliminar);
				em.getTransaction().commit();
				return true;
			}catch (Exception e) {
				e.printStackTrace(); //hacer un log
				em.getTransaction().rollback();
				return false;
			}finally {
				em.close();
			}
		}else
			return false;
	}

	@Override
	public boolean eliminar(Contacto c) {
		
		return eliminar(c.getIdContacto());
	}

	//Debe retornar el contacto con sus telefonos y correos
	@Override
	public Contacto buscar(int idContacto) {
		em = emf.createEntityManager();	
		String jpql = "select c from Contacto c left join fetch c.telefonos and left join fetch c.correos where c.idContacto = :id";
		TypedQuery<Contacto> q = em.createNamedQuery(jpql, Contacto.class);
		q.setParameter("id", idContacto);
		Contacto buscado = q.getSingleResultOrNull();
		return buscado;
	}
	
	//Debe retornar los contactos sin telefonos ni correos
	@Override
	public Set<Contacto> buscar(String cadena) {
		Set<Contacto> result = new HashSet<Contacto>();
		
		String jpql = "select c from Contacto where c.nombre like :cad or c.apellidos like :cad or c.apodo like :cad";
		TypedQuery<Contacto> q = em.createNamedQuery(jpql, Contacto.class);
		q.setParameter("cad", cadena);
		
		result.addAll(q.getResultList());
		return result;
	}

	@Override
	public Set<Contacto> buscarTodos() {
		Set<Contacto> result = new HashSet<Contacto>();
		String jpql = "select c from Contacto";
		TypedQuery<Contacto> q = em.createNamedQuery(jpql, Contacto.class);
		result.addAll(q.getResultList());
		return result;
	}

}
