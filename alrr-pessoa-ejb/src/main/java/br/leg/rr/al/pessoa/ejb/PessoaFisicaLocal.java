package br.leg.rr.al.pessoa.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import br.leg.rr.al.pessoa.jpa.PessoaFisica;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 06-07-2012
 * 
 */
@Local
public interface PessoaFisicaLocal<T extends PessoaFisica> extends PessoaLocal<T> {

	/**
	 * Busca pelo campo "cpf" da entidade {@code PessoaFisica}.<br>
	 * 
	 * @value {@literal String};
	 */
	public static final String PESQUISAR_PARAM_CPF = "cpf";

	/**
	 * Busca pelo campo "sexo" da entidade {@code PessoaFisica}.<br>
	 * 
	 * @value {@literal SexoType};
	 */
	public static final String PESQUISAR_PARAM_SEXO = "sexo";

	/**
	 * Busca pelo campo "estadoCivil" da entidade {@code PessoaFisica}.<br>
	 * 
	 * @value {@literal List<EstadoCivilType>};
	 */
	public static final String PESQUISAR_PARAM_ESTADOS_CIVIS = "estado-civil";

	/**
	 * Indica se deve carregar os telefones da entidade {@code Telefone} que estão
	 * associados a {@code Pessoa}. <br>
	 * 
	 * @value {@literal Boolean}. Se {@code true}, carrega telefones;
	 * 
	 */
	public static final String PESQUISAR_PARAM_FETCH_TELEFONE = "fetch-telefone";

	/**
	 * Busca pessoa pelo Cpf.
	 * 
	 * @param cpf cpf da pessoa fisica. O argumento pode ser ou não formatado. Será
	 *            tratado dentro do método.
	 * @return retorna a entidade pessoaFisica ou nulo.
	 */
	public T buscarPorCpf(@NotNull(message = "Argumento cpf não pode ser nulo.") String cpf);

	/**
	 * Busca todas as pessoas que possuem o nome informado no parametro.
	 * 
	 * @param nome nome inteiro ou parte do nome.
	 * @return retorna a entidade pessoaFisica ou nulo.
	 *//*
		 * public List<PessoaFisica> buscarPorNome(@NotNull(message =
		 * "Argumento nome não pode ser nulo.") String nome);
		 */

	/**
	 * Busca todos os contatos de um proprietário, por data de nascimento. Método
	 * util para buscar aniversariantes do mês.
	 * 
	 * @param data         Data que fará a busca no campo data do aniversário.
	 * @param proprietario Define de quem deve ser buscado os contatos.
	 * @return Lista de contatos que fazem aniversário na data informada conforme os
	 *         parametros <code>proprietario</code> e <code>data</code>. Retorna
	 *         <code>null</code> caso nenhum valor seja encontrado.
	 */
	public List<T> buscarContatosPorDataNascimento(Date data, PessoaFisica proprietario);

	/**
	 * 
	 * @param dataInicio
	 * @param dataFim
	 * @param proprietario
	 * @return
	 */
	public List<T> buscarContatosPorDataNascimento(Date dataInicio, Date dataFim, PessoaFisica proprietario);

	/**
	 * @param cpf
	 * @return
	 */
	Boolean verificaSeJaExistePorCPF(String cpf);

	/**
	 * @param cpf
	 * @return
	 */
	T carregarEntidadePorCPF(String cpf);
}
