package br.com.procempa.save.entity;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.jboss.seam.annotations.Name;

@Entity
@Name("animaltematendimento")
public class AnimalTemAtendimento extends SaveAbstract {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5977808543175685490L;
	private Idade idade;
	private Animal animal;
	private Agenda agenda;
	private MotivoAlta motivoalta;
	private CondicaoAlta condicaoalta;
	private Integer profissionalInternacao;
	private Integer profissionalAlta;
	private Integer profissionalObito;
	private Calendar dataEntrada;
	private Calendar dataInternacao;
	private Calendar dataAltaClinica;
	private Calendar dataAlta;
	private Calendar dataObito;
	private String obsAlta;
	public Idade getIdade() {
		return idade;
	}
	public void setIdade(Idade idade) {
		this.idade = idade;
	}
	public Animal getAnimal() {
		return animal;
	}
	public void setAnimal(Animal animal) {
		this.animal = animal;
	}
	public Agenda getAgenda() {
		return agenda;
	}
	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}
	public MotivoAlta getMotivoalta() {
		return motivoalta;
	}
	public void setMotivoalta(MotivoAlta motivoalta) {
		this.motivoalta = motivoalta;
	}
	public CondicaoAlta getCondicaoalta() {
		return condicaoalta;
	}
	public void setCondicaoalta(CondicaoAlta condicaoalta) {
		this.condicaoalta = condicaoalta;
	}
	public Integer getProfissionalInternacao() {
		return profissionalInternacao;
	}
	public void setProfissionalInternacao(Integer profissionalInternacao) {
		this.profissionalInternacao = profissionalInternacao;
	}
	public Integer getProfissionalAlta() {
		return profissionalAlta;
	}
	public void setProfissionalAlta(Integer profissionalAlta) {
		this.profissionalAlta = profissionalAlta;
	}
	public Integer getProfissionalObito() {
		return profissionalObito;
	}
	public void setProfissionalObito(Integer profissionalObito) {
		this.profissionalObito = profissionalObito;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataEntrada() {
		return dataEntrada;
	}
	public void setDataEntrada(Calendar dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataInternacao() {
		return dataInternacao;
	}
	public void setDataInternacao(Calendar dataInternacao) {
		this.dataInternacao = dataInternacao;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataAltaClinica() {
		return dataAltaClinica;
	}
	public void setDataAltaClinica(Calendar dataAltaClinica) {
		this.dataAltaClinica = dataAltaClinica;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataAlta() {
		return dataAlta;
	}
	public void setDataAlta(Calendar dataAlta) {
		this.dataAlta = dataAlta;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataObito() {
		return dataObito;
	}
	public void setDataObito(Calendar dataObito) {
		this.dataObito = dataObito;
	}
	public String getObsAlta() {
		return obsAlta;
	}
	public void setObsAlta(String obsAlta) {
		this.obsAlta = obsAlta;
	}
				
}
