/**
 * 
 */
package br.leg.rr.al.servidor.jpa;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.search.annotations.Indexed;

import br.leg.rr.al.commons.jpa.Telefone;
import br.leg.rr.al.core.jpa.Dominio;
import br.leg.rr.al.servidor.domain.LotacaoLocalConverter;
import br.leg.rr.al.servidor.domain.LotacaoLocalidade;

/**
 * Classe de persistencia da tabela lotacao.
 * 
 * @author Ednil Libanio da Costa Junior
 * @date 19-04-2018
 */
@Indexed
@Entity
@Table(indexes = {
		@Index(name = "lotacao_idx_1", columnList = "nome, local, lotacao_superior_id", unique = true) }, uniqueConstraints = {
				@UniqueConstraint(name = "lotacao_uq_1", columnNames = { "nome", "local", "lotacao_superior_id" }) })
public class Lotacao extends Dominio {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4613637099752815392L;
	
	
	@Convert(converter = LotacaoLocalConverter.class)
	@Column(length = 1, nullable = false)
	private LotacaoLocalidade local;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "lotacao_superior_id", foreignKey = @ForeignKey(name = "lotacao_superior_fk"))
	private Lotacao lotacao;

	@OneToMany
	private List<Telefone> telefones;

	/**
	 * @return the local
	 */
	public LotacaoLocalidade getLocal() {
		return local;
	}

	/**
	 * @param local
	 *            the local to set
	 */
	public void setLocal(LotacaoLocalidade local) {
		this.local = local;
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
}
