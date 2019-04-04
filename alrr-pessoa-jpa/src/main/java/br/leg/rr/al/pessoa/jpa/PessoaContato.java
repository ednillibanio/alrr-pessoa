package br.leg.rr.al.pessoa.jpa;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pessoa_contato")
public class PessoaContato extends Pessoa {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6804789195377973576L;

	private Boolean privado;

	/**
	 * Toda vez que definir um contato, é necessário informar esse campo com o dono
	 * do contato, ou seja, de quem pertence o contato.
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "proprietario_id", referencedColumnName = "id", nullable = true, foreignKey = @ForeignKey(name = "proprietario_fk"))
	private Pessoa proprietario;

	public Boolean getPrivado() {
		return privado;
	}

	public void setPrivado(Boolean privado) {
		this.privado = privado;
	}

	@Override
	public String getNomeFantasia() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNomeFantasia(String nome) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDocumento() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDocumento(String documento) {
		// TODO Auto-generated method stub

	}

	public Pessoa getProprietario() {
		return proprietario;
	}

	public void setProprietario(Pessoa proprietario) {
		this.proprietario = proprietario;
	}
}
