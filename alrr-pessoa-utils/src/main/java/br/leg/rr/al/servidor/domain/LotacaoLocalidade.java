/**************************************************************************************
 * Copyright (c) 2017, Assembleia Legislativa do Estado de Roraima, Boa Vista - RR.
 * 
 * Todos os direitos reservados. Este programa é propriedade da Assembleia Legislativa 
 * do Estado de Roraima e não é permitida a distribuição, alteração ou cópia da mesma 
 * sem prévia autoriazação.
 **************************************************************************************/
package br.leg.rr.al.servidor.domain;

import java.util.EnumMap;

import br.leg.rr.al.core.jpa.BasicEnum;

/**
 * Enum que contém valores fixos do Sexo.
 * 
 * @author Ednil Libanio da Costa Junior
 * @date 19-04-2018
 */
public enum LotacaoLocalidade implements BasicEnum<LotacaoLocalidade> {

	CASA("Casa"), GABINETE("Gabinente"), ESCOLEGIS("Escolegis"), PROCON("Procon"), CHAME("Chame");

	private LotacaoLocalidade(String label) {
		this.label = label;
	}

	private String label;

	@Override
	/**
	 * Label do tipo de lotação do departamento que o servidor público está lotado.
	 * Por exemplo, Casa.
	 * 
	 * @return label do tipo escolhido.
	 */
	public String getLabel() {
		return label;
	}

	@Override
	/**
	 * Retorna o label do tipo de lotação do departamento.
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
	public EnumMap<LotacaoLocalidade, String> getEnumMap() {
		EnumMap<LotacaoLocalidade, String> map = new EnumMap<LotacaoLocalidade, String>(LotacaoLocalidade.class);
		map.put(LotacaoLocalidade.CASA, "1");
		map.put(LotacaoLocalidade.GABINETE, "2");
		map.put(LotacaoLocalidade.ESCOLEGIS, "3");
		map.put(LotacaoLocalidade.PROCON, "4");
		map.put(LotacaoLocalidade.CHAME, "5");
		return map;
	}

}
