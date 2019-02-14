/**************************************************************************************
 * Copyright (c) 2017, Assembleia Legislativa do Estado de Roraima, Boa Vista - RR.
 * 
 * Todos os direitos reservados. Este programa é propriedade da Assembleia Legislativa 
 * do Estado de Roraima e não é permitida a distribuição, alteração ou cópia da mesma 
 * sem prévia autoriazação.
 **************************************************************************************/
package br.leg.rr.al.pessoa.utils;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public final class CnpjUtils implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 665385696398800660L;

	private static final String CNPJ_PATTERN = "(\\d\\d)(\\d\\d\\d)(\\d\\d\\d)(\\d\\d\\d\\d)(\\d\\d)";

	private CnpjUtils() {
	}

	/**
	 * Remove formatação: 99.999.999.1000/99- 99999999100099
	 * 
	 * @param cnpjFormatted
	 *            formatado.
	 * @return cnpj não formatado.
	 */
	public static String unformat(String cnpjFormatted) {

		if (StringUtils.isNotBlank(cnpjFormatted)) {
			String cnpj = cnpjFormatted.trim();
			return cnpj.replaceAll("\\.", "").replace("/", "").replace("-", "").trim();
		}
		return cnpjFormatted;
	}

	@SuppressWarnings("null")
	/**
	 * Formata cnpj: 99999999100099 - 99.999.999.1000/99
	 * 
	 * @param cnpj
	 *            sem formatação.
	 * @return retorna cnpj formatado.
	 */
	public static String format(Long cnpj) {
		if (cnpj == null) {
			throw new IllegalArgumentException("Argumento CNPJ não pode ser nulo");

		}
		return format(cnpj.toString());
	}

	/**
	 * Formata cnpj: 99999999100099 - 99.999.999.1000/99
	 * 
	 * @param cnpjUnformatted
	 *            sem formatação.
	 * @return retorna cnpj formatado.
	 */
	public static String format(String cnpjUnformatted) {

		if (StringUtils.isNotBlank(cnpjUnformatted)) {

			String cnpj = unformat(cnpjUnformatted);
			cnpj = preencherZeroAEsquerda(cnpj);
			Matcher m = Pattern.compile(CNPJ_PATTERN).matcher(cnpj);
			return m.replaceAll("$1.$2.$3/$4-$5");
		}
		return cnpjUnformatted;
	}

	@SuppressWarnings("null")
	/**
	 * 
	 * @param cnpj
	 * @return
	 */
	public static boolean isValid(Long cnpj) {
		if (cnpj == null) {
			throw new IllegalArgumentException("Argumento CNPJ não pode ser nulo");
		}
		return isValid(cnpj.toString());
	}

	/**
	 * 
	 * @param cnpj
	 * @return
	 */
	public static boolean isValid(String cnpj) {

		if (cnpj == null) {
			throw new IllegalArgumentException("Argumento CNPJ não pode ser nulo");
		}

		String cnpjUnformatted = preencherZeroAEsquerda(unformat(cnpj));

		if (cnpjUnformatted.length() != 14) {
			return false;
		}

		int resto = 0;
		int dig1 = -1;
		int dig2 = -1;
		int soma = 0;
		int[] c = new int[] { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

		// encontra o primeiro digito
		for (int i = 0; i < cnpjUnformatted.length() - 2; i++) {
			soma += Character.getNumericValue(cnpjUnformatted.toCharArray()[i]) * c[i + 1];
		}

		if ((resto = soma % 11) < 2) {
			dig1 = 0;
		} else {
			dig1 = 11 - resto; // armazena o digito
		}

		soma = 0;
		resto = 0;

		// encontra o segundo digito
		for (int y = 0; y < cnpjUnformatted.length() - 1; y++) {
			soma += (Character.getNumericValue(cnpjUnformatted.toCharArray()[y]) * c[y]);
		}

		if ((resto = soma % 11) < 2) {
			dig2 = 0;
		} else {
			dig2 = 11 - resto;
		}

		return (Character.getNumericValue(cnpjUnformatted.toCharArray()[cnpjUnformatted.length() - 2]) == dig1
				&& Character.getNumericValue(cnpjUnformatted.toCharArray()[cnpjUnformatted.length() - 1]) == dig2);
	}

	/**
	 * Compara se os cnpj's são iguais.
	 * 
	 * @param cnpjSource
	 * @param cnpjTarget
	 * @return
	 */
	public static boolean compare(String cnpjSource, String cnpjTarget) {
		return unformat(cnpjSource).compareTo(unformat(cnpjTarget)) == 0;
	}

	/**
	 * 
	 * @param cnpjUnformatted
	 *            cnpj não formatado.
	 * @return retorna o digita verificador.
	 */
	public static String getDigitoVerificador(String cnpjUnformatted) {
		String cnpj = unformat(cnpjUnformatted);
		return (Character.toString(cnpj.toCharArray()[cnpj.length() - 2])
				+ Character.toString(cnpj.toCharArray()[cnpj.length() - 1]));
	}

	/**
	 * Preenche zero a esquerda até preencher as 14 posições.
	 * 
	 * @param cnpj
	 *            não formatado.
	 * @return retorna cnpj com 14 posições preenchidos com zero a esquerda.
	 */
	private static String preencherZeroAEsquerda(String cnpj) {
		return StringUtils.leftPad(cnpj, 14, "0");
	}
}
