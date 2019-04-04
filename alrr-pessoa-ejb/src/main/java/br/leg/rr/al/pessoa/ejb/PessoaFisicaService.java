package br.leg.rr.al.pessoa.ejb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.jpa.BaseEntity_;
import br.leg.rr.al.core.utils.DataUtils;
import br.leg.rr.al.pessoa.domain.EstadoCivilType;
import br.leg.rr.al.pessoa.domain.SexoType;
import br.leg.rr.al.pessoa.jpa.Pessoa;
import br.leg.rr.al.pessoa.jpa.PessoaFisica;
import br.leg.rr.al.pessoa.jpa.PessoaFisica_;
import br.leg.rr.al.pessoa.jpa.Pessoa_;
import br.leg.rr.al.pessoa.utils.CpfUtils;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 06-07-2012
 * 
 */
/**
 * @author <a href="mailto:ednil.libanio@gmail.com"> Ednil Libanio da Costa
 *         Junior</a><br/>
 *         Data Criação: 27-08-2012<br/>
 * @since 1.0.0
 * @param <T>
 */
@Stateless
@Named
public class PessoaFisicaService<T extends PessoaFisica> extends PessoaService<T> implements PessoaFisicaLocal<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7281367420800945439L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.leg.rr.al.pessoa.ejb.PessoaFisicaLocal#buscarPorCpf(java.lang.
	 * String)
	 */
	@Override
	public T buscarPorCpf(String cpf) throws BeanException {
		String valor = CpfUtils.unformat(cpf);
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<T> cq = createCriteriaQuery();
		Root<T> root = cq.from(entityClass);
		cq.select(root);

		Predicate cond = cb.equal(root.get("cpf"), valor);
		cq.where(cond);
		return getSingleResult(cq);

	}

	@Override
	public List<T> pesquisar(Map<String, Object> params) {

		String nome = null;
		String cpf = null;
		SexoType sexo = null;
		EstadoCivilType[] estadosCivis = null;

		Boolean fetchTelefone = false;
		Long contatoProprietarioId = null;
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<T> cq = createCriteriaQuery();
		Root<T> root = cq.from(entityClass);
		List<Predicate> predicates = new ArrayList<Predicate>();
		cq.select(root).distinct(true);

		if (params.size() > 0) {

			// FILTRO: FETCH TELEFONE
			if (params.containsKey(PESQUISAR_PARAM_FETCH_TELEFONE)) {
				fetchTelefone = (Boolean) params.get(PESQUISAR_PARAM_FETCH_TELEFONE);
				if (fetchTelefone) {
					root.fetch("telefones", JoinType.LEFT);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_NOME)) {
				nome = (String) params.get(PESQUISAR_PARAM_NOME);
				if (StringUtils.isNotBlank(nome)) {
					Predicate condition = cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
					predicates.add(condition);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_CPF)) {
				cpf = (String) params.get(PESQUISAR_PARAM_CPF);
				if (StringUtils.isNotBlank(cpf)) {
					Predicate condition = cb.like(cb.lower(root.get("cpf")), "%" + cpf + "%");
					predicates.add(condition);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_SEXO)) {
				sexo = (SexoType) params.get(PESQUISAR_PARAM_SEXO);
				if (sexo != null) {
					Predicate condition = cb.equal(root.get("sexo"), sexo);
					predicates.add(condition);
				}
			}

			if (params.containsKey(PESQUISAR_PARAM_ESTADOS_CIVIS)) {
				estadosCivis = (EstadoCivilType[]) params.get(PESQUISAR_PARAM_ESTADOS_CIVIS);
				if (estadosCivis != null && estadosCivis.length > 0) {
					Expression<EstadoCivilType> exp = root.get("estadoCivil");
					Predicate condition = exp.in(Arrays.asList(estadosCivis));
					predicates.add(condition);
				}
			}

			if (params.containsKey("contato_proprietario_id")) {
				contatoProprietarioId = (Long) params.get("contato_proprietario_id");
				Predicate condition = cb.equal(root.get("contatoProprietario"), contatoProprietarioId);
				predicates.add(condition);
			}

			cq.where(cb.and(predicates.toArray(new Predicate[] {})));
			return getResultList(cq);

		}

		return null;
	}

	/**
	 * Busca os <i><b>contatos</b></i> da pessoa selecionada pela primeira letra do
	 * nome do contato.Por exemplo, busca todas as pessoas que começam com a letra
	 * <code>'B'</code>.
	 * 
	 * @param params <br/>
	 *               param "nome" -> String da primeira letra do nome do
	 *               contato.<br/>
	 *               param "contato_proprietario -> entidade Pessoa selecionada para
	 *               buscar os seus contatos.
	 * @return lista de contatos encontrados que começam com a letra informada no
	 *         parametro <code>param</code>.
	 */
	@Override
	public List<T> filtrarContatosPorNome(Map<String, Object> params) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<T> cq = createCriteriaQuery();
		Root<T> root = cq.from(entityClass);
		root.fetch(Pessoa_.telefones, JoinType.LEFT);
		cq.select(root);

		String nome = null;
		Pessoa proprietario = null;
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (params.containsKey("nome")) {
			nome = (String) params.get("nome");
			if (StringUtils.isNotBlank(nome)) {
				Predicate cond = cb.like(cb.lower(root.get(PessoaFisica_.nome)), nome.toLowerCase() + "%");
				predicates.add(cond);
			}
		}

		/*
		 * if (params.containsKey("contato_proprietario")) { proprietario = (Pessoa)
		 * params.get("contato_proprietario"); if (proprietario != null) { Predicate
		 * cond = cb.equal(root.get(Pessoa_.contatoProprietario), proprietario);
		 * predicates.add(cond); } }
		 */

		cq.where(cb.and(predicates.toArray(new Predicate[] {})));

		return getEntityManager().createQuery(cq).getResultList();

	}

	@Override
	public List<T> buscarContatosPorDataNascimento(Date data, PessoaFisica proprietario) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<T> cq = createCriteriaQuery();
		Root<T> root = cq.from(entityClass);
		cq.select(root);

		Predicate diaInicio = cb.equal(cb.function("day", Integer.class, root.get(PessoaFisica_.dataNascimento)),
				DataUtils.getDia(data));
		Predicate mesInicio = cb.and(cb.equal(
				cb.function("month", Integer.class, root.get(PessoaFisica_.dataNascimento)), DataUtils.getMes(data)));

		// Predicate cond = cb.equal(diaInicio, mesInicio);
		cq.where(diaInicio, mesInicio);
		return getResultList(cq);
	}

	@Override
	public List<T> buscarContatosPorDataNascimento(Date dataInicio, Date dataFim, PessoaFisica proprietario) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<T> cq = createCriteriaQuery();
		Root<T> root = cq.from(entityClass);
		cq.select(root);

		List<Predicate> predicates = new ArrayList<Predicate>();

		Predicate diaInicio = cb.greaterThanOrEqualTo(
				cb.function("day", Integer.class, root.get(PessoaFisica_.dataNascimento)),
				DataUtils.getDia(dataInicio));
		predicates.add(diaInicio);
		Predicate mesInicio = cb.and(
				cb.greaterThanOrEqualTo(cb.function("month", Integer.class, root.get(PessoaFisica_.dataNascimento)),
						DataUtils.getMes(dataInicio)));
		predicates.add(mesInicio);

		Predicate diaFim = cb.lessThanOrEqualTo(
				cb.function("day", Integer.class, root.get(PessoaFisica_.dataNascimento)), DataUtils.getDia(dataFim));
		predicates.add(diaFim);
		Predicate mesFim = cb
				.and(cb.lessThanOrEqualTo(cb.function("month", Integer.class, root.get(PessoaFisica_.dataNascimento)),
						DataUtils.getMes(dataFim)));
		predicates.add(mesFim);

		cq.where(cb.and(predicates.toArray(new Predicate[] {})));
		return getResultList(cq);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.leg.rr.al.pessoa.ejb.PessoaService#jaExiste(br.leg.rr.al.pessoa.
	 * entity.Pessoa)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Boolean jaExiste(PessoaFisica entidade) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<PessoaFisica> cq = (CriteriaQuery<PessoaFisica>) createCriteriaQuery();
		Root<PessoaFisica> root = cq.from(PessoaFisica.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		cq.select(root);
		Predicate cond = cb.equal(root.get(PessoaFisica_.cpf), entidade.getCpf());
		predicates.add(cond);

		if (entidade.getId() != null && entidade.getId().intValue() > 0) {
			Predicate id = cb.notEqual(root.get(BaseEntity_.id), entidade.getId());
			predicates.add(id);
		}

		cq.where(cb.and(predicates.toArray(new Predicate[] {})));

		return (!getResultList((CriteriaQuery<T>) cq).isEmpty());
	}

	@Override
	public Boolean verificaSeJaExistePorCPF(String cpf) {
		String valor = CpfUtils.unformat(cpf);

		if (StringUtils.isNotBlank(valor)) {
			T entidade = buscarPorCpf(valor);
			return (entidade != null);
		}

		return true;
	}

	@Override
	public T carregar(T entidade) {

		if (entidade != null && entidade.getId() != null) {
			T entity = buscar(entidade);
			if (entity != null) {

				entity.getNaturalidade();
				entity.getEnderecos().size();
				entity.getTelefones().size();
			}
			return entity;
		}

		return null;
	}

	@Override
	public T carregarEntidadePorCPF(String cpf) {
		T entidade = buscarPorCpf(cpf);
		return carregar(entidade);

	}

}
