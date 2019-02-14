package br.leg.rr.al.pessoa.jpa;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.leg.rr.al.pessoa.domain.PessoaJuridicaNatureza;
import br.leg.rr.al.pessoa.domain.PessoaJuridicaNaturezaConverter;
import br.leg.rr.al.pessoa.validator.Cnpj;

/**
 * The persistent class for the pessoa_juridica database table.
 * 
 * @author Ednil Libanio da Costa Junior
 * @date 03-04-2018
 */
@Entity
@Table(name = "pessoa_juridica")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class PessoaJuridica extends Pessoa {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3238717853172548244L;

	@Cnpj
	@Column(nullable = true, length = 14, unique = true, updatable = false, insertable = true)
	private String cnpj;

	@Column(nullable = true, length = 13, name = "inscricao_estadual")
	private String inscricaoEstadual;

	@NotNull
	@Size(max = 250)
	@Column(name = "nome_fantasia", nullable = false, length = 250)
	private String nomeFantasia;

	@Size(max = 250)
	@Column(name = "razao_social", nullable = true, length = 250)
	private String razaoSocial;

	@NotNull
	@Convert(converter = PessoaJuridicaNaturezaConverter.class)
	@Column(nullable = false, name = "pessoa_juridica_natureza", length = 1)
	private PessoaJuridicaNatureza natureza;

	// TODO: Ver se vamos usar pessoa para buscar esses dados ou se vamos criar
	// uma tabela ou colocar os campos nome, telefone e email.
	/*
	 * @OneToOne private PessoaFisica contato;
	 */
	@Column(length = 150, nullable = true)
	private String observacao;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public String getDocumento() {
		return getCnpj();
	}

	@Override
	public void setDocumento(String documento) {
		setCnpj(documento);

	}

	@Override
	public String getNomeFantasia() {
		return nomeFantasia;
	}

	@Override
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public PessoaJuridicaNatureza getNatureza() {
		return natureza;
	}

	public void setNatureza(PessoaJuridicaNatureza natureza) {
		this.natureza = natureza;
	}

}
