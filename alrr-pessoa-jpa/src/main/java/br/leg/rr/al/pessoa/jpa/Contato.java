package br.leg.rr.al.pessoa.jpa;

import javax.persistence.Entity;

@Entity
public class Contato extends Pessoa {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6804789195377973576L;

	

	private Boolean privado;

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
}
