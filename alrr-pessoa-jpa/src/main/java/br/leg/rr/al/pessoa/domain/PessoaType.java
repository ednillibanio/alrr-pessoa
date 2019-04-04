/*******************************************************************************
 * Copyright (c) 2017, KMDR Consultoria e Serviços, Boa Vista, RR - Brasil.
 * Todos os direitos reservados. Este programa é propriedade da Assembleia Legislativa do Estado de Roraima e não é permitida a distribuição, alteração ou cópia da mesma sem prévia autoriazação.
 ******************************************************************************/
package br.leg.rr.al.pessoa.domain;

import java.util.EnumMap;

import br.leg.rr.al.core.jpa.BasicEnum;

public enum PessoaType implements BasicEnum<PessoaType> {

	FISICA("Pessoa Física"), JURIDICA("Pessoa Jurídica");

	private PessoaType(String label) {
		this.label = label;
	}

	private String label;

	@Override
	public String toString() {
		return label;
	}

	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * Contém os valores das chaves que serão armazenados no banco de dados.
	 * 
	 * @return Retorna uma lista com todos os PessoaType.
	 */
	@Override
	public EnumMap<PessoaType, String> getEnumMap() {
		EnumMap<PessoaType, String> map = new EnumMap<PessoaType, String>(PessoaType.class);
		map.put(PessoaType.FISICA, "1");
		map.put(PessoaType.JURIDICA, "2");
		return map;
	}

}
