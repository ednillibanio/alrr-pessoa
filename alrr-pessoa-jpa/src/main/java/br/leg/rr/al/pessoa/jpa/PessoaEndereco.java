/**
 * 
 */
package br.leg.rr.al.pessoa.jpa;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.leg.rr.al.localidade.jpa.Endereco;

/**
 * Classe persistente que representa a tabela "endereco".
 * 
 * @author <a href="mailto:ednil.libanio@gmail.com"> Ednil Libanio da Costa
 *         Junior</a>
 * @since 1.0.0
 */
@Entity
@Table(name="pessoa_endereco")
public class PessoaEndereco extends Endereco {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4039706391125879526L;

}
