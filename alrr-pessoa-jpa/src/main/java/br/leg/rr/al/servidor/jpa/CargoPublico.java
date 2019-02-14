package br.leg.rr.al.servidor.jpa;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.search.annotations.Indexed;

import br.leg.rr.al.core.jpa.Dominio;
import br.leg.rr.al.servidor.domain.Provimento;
import br.leg.rr.al.servidor.domain.ProvimentoConverter;

/**
 * Classe de persistencia da tabela municipio.
 * 
 * @author Ednil Libanio da Costa Junior
 * @date 25-04-2018
 */
@Indexed
@Entity
@Table(name = "cargo_publico", indexes = {
		@Index(name = "cargo_publico_idx1", columnList = "nome", unique = true) }, uniqueConstraints = {
				@UniqueConstraint(columnNames = { "nome", "provimento" }) })
public class CargoPublico extends Dominio {

	@Column(length = 1, nullable = false)
	@Convert(converter = ProvimentoConverter.class)
	private Provimento provimento;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7675888454455209495L;

	/**
	 * @return the provimento
	 */
	public Provimento getProvimento() {
		return provimento;
	}

	/**
	 * @param provimento
	 *            the provimento to set
	 */
	public void setProvimento(Provimento provimento) {
		this.provimento = provimento;
	}

}
