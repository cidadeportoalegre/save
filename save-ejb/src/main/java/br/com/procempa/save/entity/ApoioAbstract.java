package br.com.procempa.save.entity;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForceDiscriminator;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="discriminator", discriminatorType=DiscriminatorType.STRING)
@ForceDiscriminator
@Table(name="Apoio", uniqueConstraints={@UniqueConstraint(columnNames={"descricao", "discriminator", "especie_id"})})
public abstract class ApoioAbstract extends SaveAbstract {

	private static final long serialVersionUID = 1L;

	private String descricao;
	
	private Boolean ativo = Boolean.TRUE;
	
	@Length(max=100)
	@NotNull
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@NotNull
	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}



