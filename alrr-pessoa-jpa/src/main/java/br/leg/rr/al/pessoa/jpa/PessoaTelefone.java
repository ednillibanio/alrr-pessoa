/**
 * 
 */
package br.leg.rr.al.pessoa.jpa;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.leg.rr.al.commons.jpa.Telefone;

/**
 * Classe persistente que representa a tabela "pessoa_telefone".
 * 
 * @author <a href="mailto:ednil.libanio@gmail.com"> Ednil Libanio da Costa
 *         Junior</a> @
 */
@Entity
@Table(name = "pessoa_telefone")
public class PessoaTelefone extends Telefone {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5011720148214059571L;

}
