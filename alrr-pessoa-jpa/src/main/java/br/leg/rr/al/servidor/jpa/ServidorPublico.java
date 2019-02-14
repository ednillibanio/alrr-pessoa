package br.leg.rr.al.servidor.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.core.jpa.BaseEntityStatus;
import br.leg.rr.al.pessoa.jpa.PessoaFisica;
import br.leg.rr.al.servidor.domain.SituacaoServidorPublico;
import br.leg.rr.al.servidor.domain.SituacaoServidorPublicoConverter;

@Entity
@Table(name = "servidor_publico", uniqueConstraints = {
		@UniqueConstraint(columnNames = "matricula", name = "matricula_uq") })
public class ServidorPublico extends BaseEntityStatus<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2422972382458802832L;

	@Column(length = 5, nullable = false, unique = true)
	private String matricula;

	// TODO: Incluir o validate para data de nomeacao e exoneracao.
	@Temporal(TemporalType.DATE)
	@Column(name = "data_nomeacao", nullable = true, updatable = false)
	private Date dataNomeacao;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_exoneracao", nullable = true, updatable = false)
	private Date dataExoneracao;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "cargo_publico_id", foreignKey = @ForeignKey(name = "cargo_publico_fk"))
	private CargoPublico cargoPublico;

	@NotNull
	@Column(length = 1, nullable = false, name = "situacao_servidor_publico")
	@Convert(converter = SituacaoServidorPublicoConverter.class)
	private SituacaoServidorPublico situacaoServidorPublico;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "lotacao_id", foreignKey = @ForeignKey(name = "lotacao_fk"))
	private Lotacao lotacao;

	@Valid
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "pessoa_fisica_id", foreignKey = @ForeignKey(name = "pessoa_fisica_fk"), nullable = false)
	private PessoaFisica pessoaFisica;

	@Transient
	public Boolean isExonerado() {
		return (dataExoneracao != null);
	}

	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}

	/**
	 * @param matricula
	 *            the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	/**
	 * @return the cargoPublico
	 */
	public CargoPublico getCargoPublico() {
		return cargoPublico;
	}

	/**
	 * @param cargoPublico
	 *            the cargoPublico to set
	 */
	public void setCargoPublico(CargoPublico cargoPublico) {
		this.cargoPublico = cargoPublico;
	}

	/**
	 * @return the dataNomeacao
	 */
	public Date getDataNomeacao() {
		return dataNomeacao;
	}

	/**
	 * @param dataNomeacao
	 *            the dataNomeacao to set
	 */
	public void setDataNomeacao(Date dataNomeacao) {
		this.dataNomeacao = dataNomeacao;
	}

	/**
	 * @return the dataExoneracao
	 */
	public Date getDataExoneracao() {
		return dataExoneracao;
	}

	/**
	 * @param dataExoneracao
	 *            the dataExoneracao to set
	 */
	public void setDataExoneracao(Date dataExoneracao) {
		this.dataExoneracao = dataExoneracao;
	}

	/**
	 * @return the lotacao
	 */
	public Lotacao getLotacao() {
		return lotacao;
	}

	/**
	 * @param lotacao
	 *            the lotacao to set
	 */
	public void setLotacao(Lotacao lotacao) {
		this.lotacao = lotacao;
	}

	/**
	 * @return the pessoaFisica
	 */
	public PessoaFisica getPessoaFisica() {
		return pessoaFisica;
	}

	/**
	 * @param pessoaFisica
	 *            the pessoaFisica to set
	 */
	public void setPessoaFisica(PessoaFisica pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	/**
	 * @return the situacaoServidorPublico
	 */
	public SituacaoServidorPublico getSituacaoServidorPublico() {
		return situacaoServidorPublico;
	}

	/**
	 * @param situacaoServidorPublico
	 *            the situacaoServidorPublico to set
	 */
	public void setSituacaoServidorPublico(SituacaoServidorPublico situacaoServidorPublico) {
		this.situacaoServidorPublico = situacaoServidorPublico;
	}

}
