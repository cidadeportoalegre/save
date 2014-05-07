package br.com.procempa.save.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.Email;
import org.hibernate.validator.Length;
import org.jboss.seam.annotations.Name;

@Entity
@NamedQueries(value={
		@NamedQuery( name=Solicitante.FIND_ALL, 
				query="SELECT t FROM Solicitante t " +
				"ORDER BY t.nomeSolicitante ASC"),
				@NamedQuery( name=Solicitante.SOLICITANTES_FILTRO, 
					query="SELECT t FROM Solicitante t " +
					"WHERE (t.nomeSolicitante LIKE :nome) " +
					"AND (t.cpf = :documento or t.rg = :documento or t.cnpj = :documento or t.nis = :documento or null = :documento) "+
					"ORDER BY t.nomeSolicitante"),
				@NamedQuery( name=Solicitante.SOLICITANTES_FILTRO_COUNT, 
					query="SELECT count(t) FROM Solicitante t " +
					"WHERE (t.nomeSolicitante LIKE :nome) " +
					"AND (t.cpf = :documento or t.rg = :documento or t.cnpj = :documento or t.nis = :documento or null = :documento) "),
				@NamedQuery( name=Solicitante.SOLICITANTES_FIND_BY_CPF, 
					query="SELECT t FROM Solicitante t " +
					"WHERE t.cpf LIKE :cpf"),
				@NamedQuery( name=Solicitante.FIND_BY_CODIGO_FALAPOA, 
					query="SELECT t FROM Solicitante t " +
					"WHERE (t.codigoFalaPoa = :codigoFalaPoa) ") 	
})
@Name(Solicitante.NAME)
public class Solicitante extends SaveAbstract {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "solicitante";
	public static final String FIND_ALL = "solicitante.findAll";	
	public static final String SOLICITANTES_FILTRO = "solicitante.filtro";
	public static final String SOLICITANTES_FILTRO_COUNT = "solicitante.filtrocount";
	public static final String SOLICITANTES_FIND_BY_CPF = "solicitante.findByCPF";
	public static final String FIND_BY_CODIGO_FALAPOA = "solicitante.findByCodigoFalaPoa";
	
	private String nomeSolicitante;
	private Boolean bolsaFamilia;
	private String cpf;
	private String rg;
	private String cnpj;
	private String nis;
	private Endereco endereco;	
	private String telefoneCel;
	private String telefoneCom;
	private String telefoneRes;
	private String email;
	private String observacao;
	
	private Integer codigoFalaPoa;
	
	private List<Solicitacao> solicitacaoList = new ArrayList<Solicitacao>();	
	
	public Solicitante(){
	}
	
	public Boolean getBolsaFamilia() {
		return bolsaFamilia;
	}

	public void setBolsaFamilia(Boolean bolsaFamilia) {
		this.bolsaFamilia = bolsaFamilia;
	}

	@Length(max=14)
	public String getcpf() {
		return cpf;
	}

	public void setcpf(String cpf) {
		this.cpf = cpf;
	}

	@Length(max=15)
	public String getrg() {
		return rg;
	}

	public void setrg(String rG) {
		rg = rG;
	}

	@Length(max=18)
	public String getcnpj() {
		return cnpj;
	}

	public void setcnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Length(max=20)
	public String getnis() {
		return nis;
	}

	public void setnis(String nis) {
		this.nis = nis;
	}
	
	@Length(max=100)
	public String getNomeSolicitante() {
		return nomeSolicitante;
	}

	public void setNomeSolicitante(String nomeSolicitante) {
		this.nomeSolicitante = nomeSolicitante;
	}


	@Length(max=15)
	public String getTelefoneCel() {
		return telefoneCel;
	}

	public void setTelefoneCel(String telefoneCel) {
		this.telefoneCel = telefoneCel;
	}

	@Length(max=15)
	public String getTelefoneCom() {
		return telefoneCom;
	}

	public void setTelefoneCom(String telefoneCom) {
		this.telefoneCom = telefoneCom;
	}

	@Length(max=15)
	public String getTelefoneRes() {
		return telefoneRes;
	}

	public void setTelefoneRes(String telefoneRes) {
		this.telefoneRes = telefoneRes;
	}

	@Embedded
	public Endereco getEndereco() {
		if (null == this.endereco) {
			this.endereco = new Endereco();
		}
		return this.endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@OneToMany(mappedBy="solicitante", fetch=FetchType.LAZY)
	@OrderBy(value=" dataSolicitacao desc " )
	public List<Solicitacao> getSolicitacaoList() {
		return solicitacaoList;
	}

	public void setSolicitacaoList(List<Solicitacao> solicitacaoList) {
		this.solicitacaoList = solicitacaoList;
	}

	@Length(max=40)
	@Email
	public String getEmail() {
		if (StringUtils.isNotBlank(email)){
			this.email=email.trim();
		}
		return this.email;
	}

	public void setEmail(String ctrEndEmail) {
		this.email = ctrEndEmail;
	}

	@Length(max=256)
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getCodigoFalaPoa() {
		return codigoFalaPoa;
	}

	public void setCodigoFalaPoa(Integer codigoFalaPoa) {
		this.codigoFalaPoa = codigoFalaPoa;
	}
}
