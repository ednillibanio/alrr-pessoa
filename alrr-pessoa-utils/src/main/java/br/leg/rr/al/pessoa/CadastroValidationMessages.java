/*******************************************************************************
 * Copyright (c) 2017, KMDR Consultoria e Serviços, Boa Vista, RR - Brasil.
 * Todos os direitos reservados. Este programa é propriedade da Assembleia Legislativa do Estado de Roraima e não é permitida a distribuição, alteração ou cópia da mesma sem prévia autoriazação.
 ******************************************************************************/
package br.leg.rr.al.pessoa;

/**
 * Lista dos resources que encontram no sistema.
 * 
 * @author libanioe
 * 
 */
public final class CadastroValidationMessages {

	/**
	 * Informa que o cpf informado é invalido.
	 * 
	 * @value {0} - CPF;
	 */
	public static final String CPF_INVALIDO = "CPF {0} inválido. Informe outro CPF.";

	/**
	 * Informa que o cpf pesquisado não foi encontrado.
	 * 
	 * @value {0} - CPF;
	 */
	public static final String CPF_NAO_ENCONTRADO = "Nenhuma pessoa física com o CPF {0} foi encontrada.";

	/**
	 * Informa que a matricula do servidor publico não foi encontrada.
	 * 
	 * @value {0} - Matricula;
	 */
	public static final String MATRICULA_NAO_ENCONTRADA = "Nenhum servidor público com a Matrícula {0} foi encontrado.";

	/**
	 * Informa que o cnpj informado é invalido.
	 * 
	 * @value {0} - CNPJ;
	 */
	public static final String CNPJ_INVALIDO = "CNPJ {0} invalido. Informe outro CNPJ.";

	/**
	 * Informa que o telefone informado é invalido.
	 * 
	 */
	public static final String TELEFONE_INVALIDO = "Telefone está fora do padrão. Deve conter o DDD mais 8 ou dígitos do número.";

	/**
	 * Indique o valor dia não está entre 1 e 31.
	 */
	public static final String DIA_INVALIDO = "Dia do mês inválido. Informe outro dia.";

	/**
	 * Informa que o email é inválido.
	 */
	public static final String EMAIL_INVALIDO = "Email inválido. Informe outro email.";

	/**
	 * Informa que a data de expedição do documento é maior que hoje.
	 */
	public static final String DATA_EXPEDICAO_MAIOR_QUE_HOJE = "Data invalida. Data de expedição do documento não pode ser maior que a data de hoje.";

	/**
	 * Informa que Pessoa Jurídica já existe com o CNPJ informado.
	 * 
	 * @value {0} - CNPJ;
	 * 
	 */
	public static final String CNPJ_JA_EXISTE = "Pessoa jurídica com o CNPJ {0} já existe.";

	/**
	 * Informa que Pessoa Física já existe com o CPF informado.
	 * 
	 * @value {0} - CPF;
	 * 
	 */
	public static final String CPF_JA_EXISTE = "Pessoa física com o CPF {0} já existe.";

	/**
	 * Informa que o Servidor Público com o CPF informado já existe..
	 * 
	 * @value {0} - CPF;
	 * 
	 */
	public static final String SERVIDOR_PUBLICO_JA_CADASTRADO = "Servidor público com o CPF {0} já existe.";
}
