package br.com.procempa.save.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.Length;

import br.com.procempa.save.enumerator.Uf;
import br.com.procempa.save.utils.Util;

/**
 * Classe embeddable para armazenar endereço
 * @author bridi
 */
@Embeddable
public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final Integer ENDERECO_FORA = Integer.valueOf("9999996");
	
	private Integer codigoCdl;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;
	private String municipio;
	private Uf uf;
	private String fone;
	private String latitude;
	private String longitude;


	/**
	 * Verifica se o endereço de Porto Alegre
	 * @return 	true - se for de Porto Alegre
	 * 			false - se for fora de Porto Alegre
	 */
	@Transient
	public boolean isPortoAlegre(){
		return (null != this.codigoCdl && 
				! this.codigoCdl.equals(Endereco.ENDERECO_FORA));		
	}
	
	@Transient
	public String getCepFormatado() {
		if (null != this.cep) {
			return Util.formata(StringUtils.leftPad(this.cep, 8, "0"), "#####-###");
		}
		return null;		
	}
	
	public void setCepFormatado(String formatado){
		setCep(StringUtils.remove(formatado, "-"));
	}
	

	@Length(max=50)
	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String ctrEndRua) {
		this.logradouro = ctrEndRua;
	}


	@Length(max=5)
	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String ctrEndNum) {
		this.numero = ctrEndNum;
	}


	@Length(max=100)
	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String ctrEndCompl) {
		this.complemento = ctrEndCompl;
	}


	@Length(max=20)
	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String ctrEndBairro) {
		this.bairro = ctrEndBairro;
	}


	@Length(max=8)
	public String getCep() {
		return this.cep;
	}

	public void setCep(String ctrEndCep) {
		this.cep = ctrEndCep;
	}


	@Length(max=30)
	public String getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(String ctrEndMun) {
		this.municipio = ctrEndMun;
	}

	@Enumerated(EnumType.STRING)
	public Uf getUf() {
		return this.uf;
	}

	public void setUf(Uf ctrEndUf) {
		this.uf = ctrEndUf;
	}


	@Length(max=15)
	public String getFone() {
		return this.fone;
	}

	public void setFone(String ctrEndFone) {
		this.fone = ctrEndFone;
	}

	public Integer getCodigoCdl() {
		return codigoCdl;
	}

	public void setCodigoCdl(Integer codigoCdl) {
		this.codigoCdl = codigoCdl;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	@Transient
	public String getEnderecoResumido() {
		return 
			this.logradouro + 
			(StringUtils.isNotBlank(this.numero)?", "+this.numero:" ") + 
			(StringUtils.isNotBlank(this.complemento)?" - "+ this.complemento:"");
	}
	
}
