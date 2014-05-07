package br.com.procempa.save.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.Name;

import br.com.procempa.arquitetura.seguranca.entidade.Role;

@Entity
@NamedQueries(value={
		@NamedQuery( name=Perfil.PERFIS_ALL, 
			query="SELECT up FROM Perfil up " +
				  "ORDER BY up.role.name ASC"),
		@NamedQuery( name=Perfil.PERFIL_BY_ROLE, 
			query="SELECT up FROM Perfil up " +
				"WHERE up.role = :role ")
	})
@Name(Perfil.NAME)
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"role_id"})})
public class Perfil extends SaveAbstract {

	private static final long serialVersionUID = 1L;
	public static final String PERFIS_ALL = "perfil.all";
	public static final String PERFIL_BY_ROLE = "perfil.perfilByRole";
	public static final String NAME = "perfil";

	private Role role;
	private Secretaria secretaria;

	
	/**
	 * Ao criar novo Perfil, atribui nova instancia de Role ao atributo perfil
	 */
	public Perfil() {
		super();
		this.setRole(new Role());
	}

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@NotNull
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public Secretaria getSecretaria() {
		return secretaria;
	}

	public void setSecretaria(Secretaria secretaria) {
		this.secretaria = secretaria;
	}	
	
}
