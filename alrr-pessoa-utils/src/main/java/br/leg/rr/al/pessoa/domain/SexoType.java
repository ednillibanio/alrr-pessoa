/**************************************************************************************
 * Copyright (c) 2017, Assembleia Legislativa do Estado de Roraima, Boa Vista - RR.
 * 
 * Todos os direitos reservados. Este programa é propriedade da Assembleia Legislativa 
 * do Estado de Roraima e não é permitida a distribuição, alteração ou cópia da mesma 
 * sem prévia autoriazação.
 **************************************************************************************/
package br.leg.rr.al.pessoa.domain;

import java.util.EnumMap;

import br.leg.rr.al.core.jpa.BasicEnum;

/**
 * Enum que contém valores fixos do Sexo.
 * 
 * @author Ednil Libanio da Costa Junior
 * @date 02-04-2018
 */
public enum SexoType implements BasicEnum<SexoType> {

	MASCULINO("Masculino", "M"), FEMININO("Feminino", "F");

	private SexoType(String label, String sigla) {
		this.label = label;
		this.sigla = sigla;
	}

	private SexoType(String label) {
		this.label = label;
	}

	private String label;
	private String sigla;

	@Override
	/**
	 * Label do sexo. Por exemplo, Masculino.
	 * 
	 * @return label do sexo escolhido (Masculino ou Feminino).
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sigla do sexo. Por exemplo, F.
	 * 
	 * @return sigla do sexo escolhido (M para Masculino ou F para Feminino).
	 */
	public String getSigla() {
		return sigla;
	}

	@Override
	/**
	 * Retorna o label do sexo escolhido.
	 */
	public String toString() {
		return label;
	}

	/**
	 * Contém os valores das chaves que serão armazenados no banco de dados.
	 * 
	 * @return Retorna uma lista com todos os SexoType.
	 */
	@Override
	public EnumMap<SexoType, String> getEnumMap() {
		EnumMap<SexoType, String> map = new EnumMap<SexoType, String>(SexoType.class);
		map.put(SexoType.MASCULINO, "M");
		map.put(SexoType.FEMININO, "F");
		return map;
	}

}
