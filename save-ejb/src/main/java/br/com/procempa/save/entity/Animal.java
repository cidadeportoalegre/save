package br.com.procempa.save.entity;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;
import org.jboss.seam.annotations.Name;

@Entity
@Name("animal")
public class Animal extends SaveAbstract {

	private static final long serialVersionUID = 1L;
	
	private Comportamento comportamento;
	private Porte porte;
	private Idade idade;
	private Pelagem pelagem;
	private Raca raca;
	private Especie especie;
	private FornecedorChip fornecedorChip;
	private ViaAdocao viaAdocao;
	private String nome;
	private byte[] foto;
	//private Enum genero;
	private Boolean castrado;
	private Calendar dataCastracao;
	private Calendar dataChipagem;
	private String numeroChip;
	private Boolean paraAdocao;
	private Calendar dataDisponibilizacaoAdocao;
	private Calendar dataAdocao;
	private Calendar dataObito;	
	//private Integer causaObito;
	private Boolean necessidadesEspeciais;
	private String descricaoNecessidadesEspeciais;

	@ManyToOne(fetch=FetchType.LAZY)
	public Comportamento getComportamento() {
		return comportamento;
	}

	public void setComportamento(Comportamento comportamento) {
		this.comportamento = comportamento;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public Porte getPorte() {
		return porte;
	}

	public void setPorte(Porte porte) {
		this.porte = porte;
	}

	@Length(max=18)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public Boolean getCastrado() {
		return castrado;
	}

	public void setCastrado(Boolean castrado) {
		this.castrado = castrado;
	}

	
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataCastracao() {
		return dataCastracao;
	}

	public void setDataCastracao(Calendar dataCastracao) {
		this.dataCastracao = dataCastracao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataChipagem() {
		return dataChipagem;
	}

	public void setDataChipagem(Calendar dataChipagem) {
		this.dataChipagem = dataChipagem;
	}

	@Length(max=20)
	public String getNumeroChip() {
		return numeroChip;
	}

	public void setNumeroChip(String numeroChip) {
		this.numeroChip = numeroChip;
	}

	public Boolean getParaAdocao() {
		return paraAdocao;
	}

	public void setParaAdocao(Boolean paraAdocao) {
		this.paraAdocao = paraAdocao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataDisponibilizacaoAdocao() {
		return dataDisponibilizacaoAdocao;
	}

	public void setDataDisponibilizacaoAdocao(Calendar dataDisponibilizacaoAdocao) {
		this.dataDisponibilizacaoAdocao = dataDisponibilizacaoAdocao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataAdocao() {
		return dataAdocao;
	}

	public void setDataAdocao(Calendar dataAdocao) {
		this.dataAdocao = dataAdocao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDataObito() {
		return dataObito;
	}

	public void setDataObito(Calendar dataObito) {
		this.dataObito = dataObito;
	}

	public Boolean getNecessidadesEspeciais() {
		return necessidadesEspeciais;
	}

	public void setNecessidadesEspeciais(Boolean necessidadesEspeciais) {
		this.necessidadesEspeciais = necessidadesEspeciais;
	}

	@Length(max=50)
	public String getDescricaoNecessidadesEspeciais() {
		return descricaoNecessidadesEspeciais;
	}

	public void setDescricaoNecessidadesEspeciais(
			String descricaoNecessidadesEspeciais) {
		this.descricaoNecessidadesEspeciais = descricaoNecessidadesEspeciais;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public Idade getIdade() {
		return idade;
	}

	public void setIdade(Idade idade) {
		this.idade = idade;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public Pelagem getPelagem() {
		return pelagem;
	}

	public void setPelagem(Pelagem pelagem) {
		this.pelagem = pelagem;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public Raca getRaca() {
		return raca;
	}

	public void setRaca(Raca raca) {
		this.raca = raca;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public Especie getEspecie() {
		return especie;
	}

	public void setEspecie(Especie especie) {
		this.especie = especie;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public FornecedorChip getFornecedorChip() {
		return fornecedorChip;
	}

	public void setFornecedorChip(FornecedorChip fornecedorChip) {
		this.fornecedorChip = fornecedorChip;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	public ViaAdocao getViaAdocao() {
		return viaAdocao;
	}

	public void setViaAdocao(ViaAdocao viaAdocao) {
		this.viaAdocao = viaAdocao;
	}
}
