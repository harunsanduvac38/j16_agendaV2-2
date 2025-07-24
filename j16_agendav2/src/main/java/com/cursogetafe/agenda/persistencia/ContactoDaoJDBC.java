package com.cursogetafe.agenda.persistencia;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import com.cursogetafe.agenda.config.Config;
import com.cursogetafe.agenda.modelo.Contacto;
import com.cursogetafe.agenda.modelo.Domicilio;


public class ContactoDaoJDBC implements ContactoDao {

	//le vamos a poner un atributo que es un datasouse
	private DataSource ds;
	public ContactoDaoJDBC() {
		ds = Config.getDataSource();
	}
	
	
	@Override
	public void insertar(Contacto c) {
		//String sql= "insert into contactos values(" + c.getIdContacto() + "," + c.getNombre() + "','"
		//String sql = "insert into contactos values(?,?,?,?,?,?,?,?)"; //ya pone los valores que necesitan 
		String sql = "insert into contactos " + "(nombre, apellidos, apodo, tipo_via, via, numero,"
				+ " piso, puerta, codigo_postal, ciudad, provincia) values(?,?,?,?,?,?,?,?,?,?,?)";
		try(Connection con = ds.getConnection()){
			con.setAutoCommit(false); //toda en esta conexion es autocomit
			PreparedStatement ps= con.prepareStatement(sql);
			ps.setString(1, c.getNombre());
			ps.setString(2, c.getApellidos());
			ps.setString(3, c.getApodo());
			ps.setString(4, c.getDom().getTipoVia());
			ps.setString(5, c.getDom().getVia());
			ps.setInt(6, c.getDom().getNumero());
			ps.setInt(7, c.getDom().getPiso());
			ps.setString(8, c.getDom().getPuerta());
			ps.setString(9, c.getDom().getCodigoPostal());
			ps.setString(10, c.getDom().getCiudad());
			ps.setString(11, c.getDom().getProvincia());
			
			int filas = ps.executeUpdate(); //executeUpdate ejecuta el contacto osea inserto ya 
			if(filas ==1) { //si la cantidas de filas es 1 
				//guardar los correos y guardar los telefonos 
				//consultar id generado 
				PreparedStatement psId = con.prepareStatement("select LAST_INSERT_ID()"); //ESTO ES PARA MYSQL  la pregunta para que me de el id del contacto que ha generado 
				ResultSet rsId = psId.executeQuery();
				rsId.next();
				int id = rsId.getInt(1);
				
				//telefonos:
				sql = "insert into telefonos (fk_contacto ,telefono) values(?,?)"; //PreparedStatement con dos parametros
				PreparedStatement psTel = con.prepareStatement(sql); //la PreparedStatement
				int cantTel = 0; //inicializamos contador cantidad de telefonos se insertron correctamente 
				for(String tel : c.getTelefonos()) {
					psTel.setInt(1, id);
					psTel.setString(2, tel);
					cantTel += psTel.executeUpdate(); 
			
				}
				//Correos:
				sql = "insert into correos (fk_contacto ,correo) values(?,?)"; //PreparedStatement con dos parametros
				PreparedStatement psCorreo = con.prepareStatement(sql); //la PreparedStatement
				int cantCorreos = 0; //inicializamos contador cantidad de corero se insertron correctamente 
				for(String correo : c.getTelefonos()) {
					psCorreo.setInt(1, id);
					psCorreo.setString(2, correo);
					cantCorreos += psCorreo.executeUpdate(); 
				}
				
				//hacemos el control
				if(cantTel == c.getTelefonos().size() && cantCorreos == c.getCorreos().size()); //especifico la cantidad de tel que se han insertado es = a la cantidad de tel y correos de ese contacto 
					con.commit();	//si todo sale bien hacemos un commit 
			}else { //si no  es solo una, hay un error no se puede insertar bien 
				con.rollback();
				//lanzar una exception propia por ejemplo 
				throw new RuntimeException();
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
			
		}
	}

	@Override
	public void actualizar(Contacto c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean eliminar(int idContacto) {
		// TODO Auto-generated method stub
		return false;
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
		Set<Contacto> busqueda = new HashSet<Contacto>();
		try(Connection con = ds.getConnection())
		{
			String sql = "select idcontactos, nombre,apellidos,apodo,tipo_via,"
					+ "via ,numero,piso, puerta,codigo_postal,ciudad,provincia "
					+ "from contactos "
					+ "where nombre like ? or apellidos like ? or apodo like ?";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1,"%" + cadena + "%" );
		ps.setString(2,"%" + cadena + "%" );
		ps.setString(3,"%" + cadena + "%" );
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			Contacto c = new Contacto();
		c.setIdContacto(rs.getInt("idcontactos"));
		c.setNombre(rs.getString("nombre"));
		c.setApellidos(rs.getString("apellidos"));
		c.setApodo(rs.getString("apodo"));
		Domicilio dom = new Domicilio();
		dom.setTipoVia(rs.getString("tipo_via"));
		dom.setVia(rs.getString("via"));
		dom.setNumero(rs.getInt("numero"));
		dom.setPiso(rs.getInt("piso"));
		dom.setCodigoPostal(rs.getString("codigo_postal"));
		dom.setCiudad(rs.getString("ciudad"));
		dom.setProvincia(rs.getString("provincia"));
		c.setDom(dom);
		sql="select telefono from telefonos where fk_contacto = ?";
		PreparedStatement psTelefonos =con.prepareStatement(sql);
		psTelefonos.setInt(1, c.getIdContacto());
		ResultSet rsTelefonos = psTelefonos.executeQuery();
		while(rsTelefonos.next()) {
			c.addTelefonos(rsTelefonos.getString("telefono"));
		}
		
		sql="select correo from correos where fk_contacto = ?";
		PreparedStatement psCorreos =con.prepareStatement(sql);
		psCorreos.setInt(1, c.getIdContacto());
		ResultSet rsCorreos = psCorreos.executeQuery();
		while(rsCorreos.next()) {
			c.addCorreos(rsCorreos.getString("correo"));
		}
		busqueda.add(c);
			
		}
		
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return busqueda;
	}

	@Override
	public Set<Contacto> buscarTodos() {
		Set<Contacto> resu = new HashSet<Contacto>();
		try(Connection con= ds.getConnection()){
			
			String sql = "select idcontactos,nombre,apellidos,apodo,tipo_via,via,"
					+ "numero,piso,puerta,codigo_postal,ciudad,provincia "
					+ "from contactos";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Contacto c = new Contacto();
				c.setIdContacto(rs.getInt("idcontactos"));
				c.setNombre(rs.getString("nombre"));
				c.setApellidos(rs.getString("apellidos"));
				c.setApodo(rs.getString("apodo"));
				Domicilio dom = new Domicilio();
				dom.setTipoVia(rs.getString("tipo_via"));
				dom.setVia(rs.getString("via"));
				dom.setNumero(rs.getInt("numero"));
				dom.setPiso(rs.getInt("piso"));
				dom.setPuerta(rs.getString("puerta"));
				dom.setCodigoPostal(rs.getString("codigo_postal"));
				dom.setCiudad(rs.getString("ciudad"));
				dom.setProvincia(rs.getString("provincia"));
				c.setDom(dom);
				//faltan correos y telefonos
				//Statement st =con.createStatement();
				//st.execute("select telefono from telefonos where fk_contacto =" + c.getIdContacto());
				sql="select telefono from telefonos where fk_contacto = ?";
				PreparedStatement psTelefonos =con.prepareStatement(sql);
				psTelefonos.setInt(1, c.getIdContacto());
				ResultSet rsTelefonos = psTelefonos.executeQuery();
				while(rsTelefonos.next()) {
					c.addTelefonos(rsTelefonos.getString("telefono"));
				}
				
				sql="select correo from correos where fk_contacto = ?";
				PreparedStatement psCorreos =con.prepareStatement(sql);
				psCorreos.setInt(1, c.getIdContacto());
				ResultSet rsCorreos = psCorreos.executeQuery();
				while(rsCorreos.next()) {
					c.addCorreos(rsCorreos.getString("correo"));
				}
				
				resu.add(c);
				
				
				
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return resu;
	}

}
