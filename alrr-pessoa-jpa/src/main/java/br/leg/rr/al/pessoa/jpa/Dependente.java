package br.leg.rr.al.pessoa.jpa;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 02-04-2018
 */
@Entity
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
@Table(name = "pessoa_fisica_dependente")
public class Dependente extends PessoaFisica {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7623158686791751308L;

	@ManyToOne
	@JoinColumn(name = "dependente_tipo_id", foreignKey = @ForeignKey(name = "dependente_tipo_fk"))
	private DependenteTipo tipo;

	@ManyToOne
	@JoinColumn(name = "dependente_proprietario_id", foreignKey = @ForeignKey(name = "dependente_proprietario_fk"))
	private PessoaFisica pessoaFisica;

	public DependenteTipo getTipo() {
		return tipo;
	}

	public void setTipo(DependenteTipo tipo) {
		this.tipo = tipo;
	}
}
