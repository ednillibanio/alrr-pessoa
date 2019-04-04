package br.leg.rr.al.pessoa.jpa;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import br.leg.rr.al.core.utils.DataUtils;
import br.leg.rr.al.localidade.jpa.Municipio;
import br.leg.rr.al.localidade.jpa.Pais;
import br.leg.rr.al.pessoa.domain.EstadoCivilConverter;
import br.leg.rr.al.pessoa.domain.EstadoCivilType;
import br.leg.rr.al.pessoa.domain.SexoConverter;
import br.leg.rr.al.pessoa.domain.SexoType;
import br.leg.rr.al.pessoa.validator.Cpf;

/**
 * The persistent class for the pessoa_fisica database table.
 * 
 * @author Ednil Libanio da Costa Junior
 * @date 03-04-2018
 */
//FIXME Corrigir os indices que estão aqui e passar pro script sql.
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "pessoa_fisica", indexes = { @Index(name = "pessoa_fisica_idx1", columnList = "cpf", unique = true),
		@Index(name = "pessoa_fisica_idx2", columnList = "nome") })
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class PessoaFisica extends Pessoa {

	private static final long serialVersionUID = -5405609863779531626L;

	@Cpf
	@Column(length = 11, unique = true, nullable = true)
	private String cpf;

	@Column(nullable = false, length = 250)
	@Size(min = 3, max = 250)
	@NotNull
	private String nome;

	@Convert(converter = SexoConverter.class)
	@Column(name = "sexo", length = 1, nullable = false)
	@NotNull
	private SexoType sexo;

	@Past
	@Temporal(TemporalType.DATE)
	@Column(name = "data_nascimento", nullable = true)
	private Date dataNascimento;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "naturalidade_id", referencedColumnName = "id", nullable = true, foreignKey = @ForeignKey(name = "naturalidade_fk"))
	private Municipio naturalidade;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "nacionalidade_id", referencedColumnName = "id", nullable = true, foreignKey = @ForeignKey(name = "nacionalidade_fk"))
	private Pais nacionalidade;

	@Column(name = "nome_mae", nullable = true, length = 250)
	@Size(max = 250)
	private String nomeMae;

	@Column(name = "nome_pai", nullable = true, length = 250)
	@Size(max = 250)
	private String nomePai;

	@Embedded
	private RegistroGeral rg;

	@Convert(converter = EstadoCivilConverter.class)
	@Column(name = "estado_civil", length = 2, nullable = true)
	private EstadoCivilType estadoCivil;

	@OneToMany(mappedBy = "pessoaFisica", fetch = FetchType.LAZY)
	private Set<Dependente> dependentes = new HashSet<Dependente>();

	/*
	 * @OneToOne(optional = true, fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(unique = true, name = "usuario_id") private Usuario usuario;
	 */

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Transient
	public Integer getDiaAniversario() {
		return DataUtils.getDia(dataNascimento);
	}

	@Transient
	public String getDiaSemanaAniversarioAsString() {
		return DataUtils.getDiaSemanaAsString(dataNascimento);
	}

	@Transient
	public Integer getMesAniversario() {
		return DataUtils.getMes(dataNascimento);
	}

	@Transient
	public String getMesAniversarioAsString() {
		return DataUtils.getMesAsString(dataNascimento);
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Municipio getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(Municipio naturalidade) {
		this.naturalidade = naturalidade;
	}

	@Override
	public String getNomeFantasia() {
		return getNome();
	}

	@Override
	public void setNomeFantasia(String nome) {
		this.setNome(nome);
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public SexoType getSexo() {
		return sexo;
	}

	public void setSexo(SexoType sexo) {
		this.sexo = sexo;
	}

	public EstadoCivilType getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivilType estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	@Override
	public String getDocumento() {
		return getCpf();
	}

	@Override
	public void setDocumento(String documento) {
		setCpf(documento);
	}

	public RegistroGeral getRG() {
		return rg;
	}

	public void setRG(RegistroGeral rg) {
		this.rg = rg;
	}

	public Set<Dependente> getDependentes() {
		return dependentes;
	}

	public void setDependentes(Set<Dependente> dependentes) {
		this.dependentes = dependentes;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Pais getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(Pais nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	/*
	 * public Usuario getUsuario() { return usuario; }
	 * 
	 * public void setUsuario(Usuario usuario) { this.usuario = usuario; }
	 */

}
