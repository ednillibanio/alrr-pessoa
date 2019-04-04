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

public enum EstadoCivilType implements BasicEnum<EstadoCivilType> {

	SOLTEIRO("Solteiro(a)"), CASADO("Casado(a)"), DIVORCIADO("Divorciado(a)"), SEPARADO("Separado(a)"), VIUVO(
			"Viúvo(a)");

	private EstadoCivilType(String label) {
		this.label = label;

	}

	private String label;

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return label;
	}

	/**
	 * Contém os valores das chaves que serão armazenados no banco de dados.
	 * 
	 * @return Retorna uma lista com todos os EstadoCivilType.
	 */
	@Override
	public EnumMap<EstadoCivilType, String> getEnumMap() {
		EnumMap<EstadoCivilType, String> map = new EnumMap<EstadoCivilType, String>(EstadoCivilType.class);
		map.put(EstadoCivilType.SOLTEIRO, "1");
		map.put(EstadoCivilType.CASADO, "2");
		map.put(EstadoCivilType.DIVORCIADO, "3");
		map.put(EstadoCivilType.SEPARADO, "4");
		map.put(EstadoCivilType.VIUVO, "5");

		return map;
	}

}
