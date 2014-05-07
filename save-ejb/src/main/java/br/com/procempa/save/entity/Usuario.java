package br.com.procempa.save.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.Name;

import br.com.procempa.arquitetura.seguranca.entidade.User;

@Entity
@NamedQueries(value={
		@NamedQuery( name=Usuario.USUARIOS_ALL, 
			query="SELECT up FROM Usuario up " +
				  "ORDER BY up.user.userName ASC"),
		@NamedQuery( name=Usuario.USUARIOS_FILTRO, 
			query="SELECT up FROM Usuario up " +
				"join up.user u " +
				"WHERE u.name LIKE :nome " +
				"ORDER BY u.name"),
		@NamedQuery( name=Usuario.USUARIOS_FILTRO_COUNT, 
			query="SELECT count(*) FROM Usuario up " +
				"join up.user u " +
				"WHERE u.name LIKE :nome"),
		@NamedQuery( name=Usuario.FIND_BY_USER, 
			query="SELECT up FROM Usuario up " +
				"join up.user u " +
				"WHERE up.user = :user"),
		@NamedQuery(name=Usuario.FIND_BY_DOMAIN_USER, 
			query="SELECT up from Usuario up " +
				"join up.user u " +
				"where u.userName = :userName and u.domain = :domain")
	})
@Name(Usuario.NAME)
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"user_id"})})
public class Usuario extends SaveAbstract {

	private static final long serialVersionUID = 1L;
	public static final String USUARIOS_ALL = "usuario.all";
	public static final String USUARIOS_FILTRO = "usuario.filtro";
	public static final String USUARIOS_FILTRO_COUNT = "usuario.filtrocount";
	public static final String FIND_BY_USER = "usuario.byUser";
	public static final String FIND_BY_DOMAIN_USER = "usuario.byDomainUser";	
	public static final String NAME = "usuario";

	private User user;
	
	/**
	 * Ao criar novo Usuario, atribui nova instancia de User ao atributo usuario
	 */
	public Usuario() {
		super();
		this.setUser(new User());
	}

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@NotNull
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	
	
}
