/*******************************************************************************
 * Copyright (c) 2017, KMDR Consultoria e Serviços, Boa Vista, RR - Brasil.
 * Todos os direitos reservados. Este programa é propriedade da Assembleia Legislativa do Estado de Roraima e não é permitida a distribuição, alteração ou cópia da mesma sem prévia autoriazação.
 ******************************************************************************/
package br.leg.rr.al.pessoa.ejb;

import java.util.List;

import javax.ejb.Local;

import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.pessoa.jpa.PessoaJuridica;

@Local
public interface PessoaJuridicaLocal extends PessoaLocal<PessoaJuridica> {

	/**
	 * Indica se deve carregar os telefones da entidade {@code Telefone} que estão
	 * associados a {@code Pessoa}. <br>
	 * 
	 * @value {@literal Boolean}. Se {@code true}, carrega telefones;
	 * 
	 */
	public static final String PESQUISAR_PARAM_FETCH_TELEFONE = "fetch-telefone";

	/**
	 * Busca pelo campo "nomeFantasia" da entidade {@code PessoaJuridica}.<br>
	 * 
	 * @value {@literal String};
	 */
	public static final String PESQUISAR_PARAM_NOME_FANTASIA = "nome-fantasia";

	/**
	 * Busca pelo campo "cnpj" da entidade {@code PessoaJuridica}.<br>
	 * 
	 * @value {@literal String};
	 */
	public static final String PESQUISAR_PARAM_CNPJ = "cnpj";

	/**
	 * Busca pelo atributo "natureza" da entidade {@code PessoaJuridica}.<br>
	 * 
	 * @value {@literal PessoaJuridicaNatureza};
	 */
	public static final String PESQUISAR_PARAM_NATUREZA = "nat";

	/**
	 * Busca pessoa juridica pelo cnpj.
	 * 
	 * @param cnpj
	 *            cnpj da pessoa juridica.
	 * @return retorna a entidade pessoaJuridica ou nulo.
	 * @throws ControllerException
	 */
	public PessoaJuridica buscarPorCnpj(String cnpj) throws BeanException;

	/**
	 * Busca todas as pessoas juridicas que possuem o nome informado no parametro.
	 * 
	 * @param razaoSocial
	 *            Razao social inteiro ou parte.
	 * @return retorna uma lista de pessoas juridicas ou nulo.
	 * @throws ControllerException
	 */
	public List<PessoaJuridica> buscarPorRazaoSocial(String razaoSocial) throws BeanException;

}
