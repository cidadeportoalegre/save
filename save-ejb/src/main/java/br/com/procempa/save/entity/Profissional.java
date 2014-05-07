package br.com.procempa.save.entity;

import javax.persistence.Entity;

import org.jboss.seam.annotations.Name;

@Entity
@Name("profissional")
public class Profissional extends SaveAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FuncaoTrabalhador funcaotrabalhador;
	private String  nome;
	private Integer matricula;
	private Integer numeroConselho;
	private String Cpf;
	private Boolean situacao;
	public FuncaoTrabalhador getFuncaotrabalhador() {
		return funcaotrabalhador;
	}
	public void setFuncaotrabalhador(FuncaoTrabalhador funcaotrabalhador) {
		this.funcaotrabalhador = funcaotrabalhador;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getMatricula() {
		return matricula;
	}
	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}
	public Integer getNumeroConselho() {
		return numeroConselho;
	}
	public void setNumeroConselho(Integer numeroConselho) {
		this.numeroConselho = numeroConselho;
	}
	public String getCpf() {
		return Cpf;
	}
	public void setCpf(String cpf) {
		Cpf = cpf;
	}
	public Boolean getSituacao() {
		return situacao;
	}
	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}
	
}
