/*******************************************************************************
 * Copyright (c) 2017, KMDR Consultoria e Serviços, Boa Vista, RR - Brasil.
 * Todos os direitos reservados. Este programa é propriedade da Assembleia Legislativa do Estado de Roraima e não é permitida a distribuição, alteração ou cópia da mesma sem prévia autoriazação.
 ******************************************************************************/
package br.leg.rr.al.pessoa.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.jpa.BaseEntity_;
import br.leg.rr.al.pessoa.domain.PessoaJuridicaNatureza;
import br.leg.rr.al.pessoa.jpa.Pessoa;
import br.leg.rr.al.pessoa.jpa.PessoaJuridica;
import br.leg.rr.al.pessoa.jpa.PessoaJuridica_;
import br.leg.rr.al.pessoa.jpa.Pessoa_;

@Named
@Stateless
public class PessoaJuridicaService extends PessoaService<PessoaJuridica> implements PessoaJuridicaLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6430330389065991073L;

	@Override
	public PessoaJuridica buscarPorCnpj(String cnpj) throws BeanException {
		return buscarEntidade("PessoaJuridica.findByCnpj", "cnpj", cnpj);
	}

	@Override
	public List<PessoaJuridica> buscarPorRazaoSocial(String razaoSocial) throws BeanException {
		return buscarPorNamedQuery("PessoaJuridica.findByRazaoSocial", "razaoSocial",
				razaoSocial.toUpperCase().concat("%"));
	}

	/**
	 * Busca os <i><b>contatos</b></i> da pessoa selecionada pela primeira letra do
	 * nome do contato.Por exemplo, busca todas as pessoas que começam com a letra
	 * <code>'B'</code>.
	 * 
	 * @param params
	 *            <br/>
	 *            param "nome" -> String da primeira letra do nome do contato.<br/>
	 *            param "contato_proprietario -> entidade Pessoa selecionada para
	 *            buscar os seus contatos.
	 * @return lista de contatos encontrados que começam com a letra informada no
	 *         parametro <code>param</code>.
	 */
	@Override
	public List<PessoaJuridica> filtrarContatosPorNome(Map<String, Object> params) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<PessoaJuridica> cq = createCriteriaQuery();
		Root<PessoaJuridica> root = cq.from(PessoaJuridica.class);
		root.fetch(Pessoa_.telefones, JoinType.LEFT);
		cq.select(root);

		String nome = null;
		Pessoa proprietario = null;
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (params.containsKey(PESQUISAR_PARAM_NOME_FANTASIA)) {
			nome = (String) params.get(PESQUISAR_PARAM_NOME_FANTASIA);
			if (StringUtils.isNotBlank(nome)) {
				Predicate cond = cb.like(cb.lower(root.get(PessoaJuridica_.nomeFantasia)), nome.toLowerCase() + "%");
				predicates.add(cond);
			}
		}

		if (params.containsKey("contato_proprietario")) {
			proprietario = (Pessoa) params.get("contato_proprietario");
			if (proprietario != null) {
				Predicate cond = cb.equal(root.get(Pessoa_.contatoProprietario), proprietario);
				predicates.add(cond);
			}
		}

		cq.where(cb.and(predicates.toArray(new Predicate[] {})));

		return getEntityManager().createQuery(cq).getResultList();

	}

	@Override
	public List<PessoaJuridica> pesquisar(Map<String, Object> params) {

		String nome = null;
		String cnpj = null;
		PessoaJuridicaNatureza nat = null;
		Boolean fetchTelefone = false;

		Long contatoProprietarioId = null;
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<PessoaJuridica> cq = createCriteriaQuery();
		Root<PessoaJuridica> root = cq.from(PessoaJuridica.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		cq.select(root).distinct(true);

		if (params.size() > 0) {

			// FILTRO: FETCH TELEFONE
			if (params.containsKey(PESQUISAR_PARAM_FETCH_TELEFONE)) {
				fetchTelefone = (Boolean) params.get(PESQUISAR_PARAM_FETCH_TELEFONE);
				if (fetchTelefone) {
					root.fetch(Pessoa_.telefones, JoinType.LEFT);
				}
			}

			// FILTRO: NOME FANTASIA
			if (params.containsKey(PESQUISAR_PARAM_NOME_FANTASIA)) {
				nome = (String) params.get(PESQUISAR_PARAM_NOME_FANTASIA);
				if (StringUtils.isNotBlank(nome)) {
					Predicate cond = cb.like(cb.lower(root.get(PessoaJuridica_.nomeFantasia)),
							"%" + nome.toLowerCase() + "%");
					predicates.add(cond);
				}
			}

			// FILTRO: CNPJ
			if (params.containsKey(PESQUISAR_PARAM_CNPJ)) {
				cnpj = (String) params.get(PESQUISAR_PARAM_CNPJ);
				if (cnpj != null) {
					Predicate cond = cb.equal(root.get(PessoaJuridica_.cnpj), cnpj);
					predicates.add(cond);
				}
			}

			// FILTRO: NATUREZA DA PESSOA JURIDICA
			if (params.containsKey(PESQUISAR_PARAM_NATUREZA)) {
				nat = (PessoaJuridicaNatureza) params.get(PESQUISAR_PARAM_NATUREZA);
				if (nat != null) {
					Predicate cond = cb.equal(root.get(PessoaJuridica_.natureza), nat);
					predicates.add(cond);
				}
			}

			// FILTRO: ID DO PROPRIETARIO CASO SEJA PARA CONTATO A CONSULTA.
			if (params.containsKey("contato_proprietario_id")) {
				contatoProprietarioId = (Long) params.get("contato_proprietario_id");
				Predicate cond = cb.equal(root.get(Pessoa_.contatoProprietario), contatoProprietarioId);
				predicates.add(cond);
			}

			cq.where(cb.and(predicates.toArray(new Predicate[] {})));
			return getEntityManager().createQuery(cq).getResultList();

		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.leg.rr.al.pessoa.ejb.PessoaService#jaExiste(br.leg.rr.al.pessoa.
	 * entity.Pessoa)
	 */
	@Override
	public Boolean jaExiste(PessoaJuridica entidade) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<PessoaJuridica> cq = createCriteriaQuery();
		Root<PessoaJuridica> root = cq.from(PessoaJuridica.class);
		cq.select(root);
		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate cond = cb.equal(cb.lower(root.get(PessoaJuridica_.cnpj)), entidade.getCnpj());
		predicates.add(cond);

		if (entidade.getId() != null && entidade.getId().intValue() > 0) {
			Predicate id = cb.notEqual(root.get(BaseEntity_.id), entidade.getId());
			predicates.add(id);
		}

		cq.where(cb.and(predicates.toArray(new Predicate[] {})));

		return (!getResultList(cq).isEmpty());
	}

}
