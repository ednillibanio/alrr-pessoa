/*******************************************************************************
 * Copyright (c) 2017, KMDR Consultoria e Serviços, Boa Vista, RR - Brasil.
 * Todos os direitos reservados. Este programa é propriedade da Assembleia Legislativa do Estado de Roraima e não é permitida a distribuição, alteração ou cópia da mesma sem prévia autoriazação.
 ******************************************************************************/
package br.leg.rr.al.pessoa.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.leg.rr.al.commons.domain.TelefoneType;
import br.leg.rr.al.commons.jpa.Telefone;
import br.leg.rr.al.core.dao.JPADaoStatus;
import br.leg.rr.al.localidade.domain.EnderecoType;
import br.leg.rr.al.localidade.jpa.Endereco;
import br.leg.rr.al.pessoa.jpa.Pessoa;

@Local
public interface PessoaLocal<T extends Pessoa> extends JPADaoStatus<T, Long> {

	/**
	 * Obtém o telefone comercial da pessoa.
	 * 
	 * @param pessoa
	 *            que contém os telefones.
	 * @return retorna o telefone comercial ou <code>null</code> caso não
	 *         exista.
	 */
	Telefone getTelefoneComercial(Pessoa pessoa);

	/**
	 * Obtém o telefone comercial da pessoa.
	 * 
	 * @param telefones
	 *            lista de telefones que será pesquisado o telefone comercial.
	 * @return retorna o telefone comercial ou <code>null</code> caso não
	 *         exista.
	 */
	Telefone getTelefoneComercial(List<Telefone> telefones);

	/**
	 * Obtém o telefone residencial da pessoa.
	 * 
	 * @param pessoa
	 *            pessoa que contém os telefones.
	 * @return retorna o residencial ou <code>null</code> caso não exista.
	 */
	Telefone getTelefoneResidencial(Pessoa pessoa);

	/**
	 * Obtém o telefone celular da pessoa.
	 * 
	 * @param pessoa
	 *            pessoa que contém os telefones.
	 * @return retorna o celular ou <code>null</code> caso não exista.
	 */
	Telefone getCelular(Pessoa pessoa);

	/**
	 * Obtém o telefone da pessoa conforme o tipo.
	 * 
	 * @param pessoa
	 * @param tipo
	 *            Tipo do telefone. Residencial, Comercial, Celular, etc.
	 * @return retorna o telefone conforme o tipo, ou <code>null</code> caso não
	 *         exista.
	 */
	Telefone getTelefone(Pessoa pessoa, TelefoneType tipo);

	/**
	 * 
	 * @param pessoa
	 * @param tel
	 */
	public void removerTelefone(Pessoa pessoa, TelefoneType tipo);

	/**
	 * 
	 * @param pessoa
	 * @param tipo
	 * @return
	 */
	Endereco getEndereco(Pessoa pessoa, EnderecoType tipo);

	/**
	 * 
	 * @param pessoa
	 * @return
	 */
	Endereco getEnderecoResidencial(Pessoa pessoa);

	/**
	 * 
	 * @param pessoaFisica
	 * @param tipo
	 */
	public void removerEndereco(Pessoa pessoa, EnderecoType tipo);

	/**
	 * Busca os <i><b>contatos</b></i> da pessoa selecionada pela primeira letra
	 * do nome do contato.Por exemplo, busca todas as pessoas que começam com a
	 * letra <code>'B'</code>.
	 * 
	 * @param params
	 *            lista de parametros informados por cada método que
	 *            implementar.
	 * @return lista de contatos encontrados de acordo com os parametros
	 *         informados.
	 */
	List<T> filtrarContatosPorNome(Map<String, Object> params);
}
