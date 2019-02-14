package br.leg.rr.al.pessoa.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

//TODO: Comentar Classe e métodos
public final class CpfUtils {

	// TODO: Ver o @CPF do hibernate. Ver como podemos usa-lo ou fazer um parecido.
	/*
	 * @Pattern.List({
	 * 
	 * @Pattern(regexp = "([0-9]{3}[.]?[0-9]{3}[.]?[0-9]{3}-[0-9]{2})|([0-9]{11})"),
	 * // XXX.XXX.XXX-XX where X is always the same digit are not a valid CPFs, but
	 * all of them passes the mod check. Needs to be singled out each one via regexp
	 * 
	 * @Pattern(regexp = "^(?:(?!000\\.?000\\.?000-?00).)*$"),
	 * 
	 * @Pattern(regexp = "^(?:(?!111\\.?111\\.?111-?11).)*$"),
	 * 
	 * @Pattern(regexp = "^(?:(?!222\\.?222\\.?222-?22).)*$"),
	 * 
	 * @Pattern(regexp = "^(?:(?!333\\.?333\\.?333-?33).)*$"),
	 * 
	 * @Pattern(regexp = "^(?:(?!444\\.?444\\.?444-?44).)*$"),
	 * 
	 * @Pattern(regexp = "^(?:(?!555\\.?555\\.?555-?55).)*$"),
	 * 
	 * @Pattern(regexp = "^(?:(?!666\\.?666\\.?666-?66).)*$"),
	 * 
	 * @Pattern(regexp = "^(?:(?!777\\.?777\\.?777-?77).)*$"),
	 * 
	 * @Pattern(regexp = "^(?:(?!888\\.?888\\.?888-?88).)*$"),
	 * 
	 * @Pattern(regexp = "^(?:(?!999\\.?999\\.?999-?99).)*$")
	 */

	/**
	 * Retorna uma String com cpf formatado 999.999.999-99
	 */
	public static String format(Long obj) {
		if (obj != null) {
			return format(obj.toString());
		} else {
			return "";
		}
	}

	/**
	 * Retorna uma String com cpf formatado 999.999.999-99
	 * 
	 */
	public static String format(String cpf) {

		if (StringUtils.isNotBlank(cpf) && StringUtils.isNumeric(cpf)) {
			cpf = unformat(cpf);
			cpf = fill(cpf);
			Pattern p = Pattern.compile("(\\d\\d\\d)(\\d\\d\\d)(\\d\\d\\d)(\\d\\d)");
			Matcher m = p.matcher(cpf);
			return m.replaceAll("$1.$2.$3-$4");
		}
		return cpf;
	}

	/**
	 * Valida o cpf informado. Retorna false se for Invalido. Valor nulo será
	 * tratado como True.
	 */
	public static boolean isValid(Long obj) {
		if (obj != null) {
			return isValid(obj.toString());
		} else {
			return true;
		}
	}

	/**
	 * Valida o cpf informado. Retorna false se for Invalido. Valor nulo será
	 * tratado como True.
	 */
	public static boolean isValid(String obj) {

		if (obj == null)
			return true;

		String cpf = unformat(obj);
		if (StringUtils.isBlank(cpf)) {
			return true;
		}

		cpf = fill(cpf);

		if (digitosIguais(cpf)) {
			return false;
		}

		int primeiroDigitoCpfInformado = Integer.valueOf(cpf.substring(9, 10));
		int segundoDigitoCpfInformado = Integer.valueOf(cpf.substring(10));

		// calcula primeiro dígito

		int primeiroDigito = calcularDigitoVerificador(cpf, 10);

		if (primeiroDigito != primeiroDigitoCpfInformado) {
			return false;
		}

		// calcula segundo dígito
		int segundoDigito = calcularDigitoVerificador(cpf, 11);

		// compara os dígitos
		return segundoDigito == segundoDigitoCpfInformado;

	}

	/**
	 * Calcula o digito verificador do CPF.
	 * 
	 * @author <a href="mailto:carlos.manoel@pdcase.com.br">Carlos Manoel</a>.
	 * @param cpf
	 * @return
	 */
	private static int calcularDigitoVerificador(String cpf, int valorMaxCalculo) {
		int somatorio = 0;
		int tamanho = valorMaxCalculo - 1;
		for (int i = 0; i < tamanho; i++) {
			somatorio += valorMaxCalculo * Integer.parseInt(cpf.substring(i, i + 1));
			valorMaxCalculo--;
		}

		int resto = somatorio % 11;

		if (resto >= 2) {
			return 11 - resto;
		} else {
			return 0;
		}
	}

	private static boolean digitosIguais(String cpf) {
		for (int i = 0; i < 10; i++) {
			if (StringUtils.containsOnly(cpf, i + "")) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Remove e retorna o cpf sem formatação. Ex: 99999999999.
	 *
	 * @param obj
	 *            cpf formatado. Ex: 999.999.999-99.
	 * @return retorna o cpf sem formatação. Ex: 99999999999.
	 */
	public static String unformat(String obj) {
		String cpf = obj;
		if (StringUtils.isNotBlank(cpf)) {
			return cpf.replaceAll("\\.", "").replace("-", "").trim();
		}
		return cpf;
	}

	/**
	 * Preenche o número informado com zeros a esquerda para ficar com 11 digitos do
	 * cpf
	 * 
	 */
	public static String fill(Long cpf) {

		return fill(cpf.toString());
	}

	public static String fill(String cpf) {
		return StringUtils.leftPad(cpf, 11, "0");
	}
}
