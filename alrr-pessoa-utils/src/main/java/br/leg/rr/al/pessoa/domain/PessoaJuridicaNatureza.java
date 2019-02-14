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
 * @author Ednil Libanio da Costa Junior
 * @date 03-04-2018
 */
public enum PessoaJuridicaNatureza implements BasicEnum<PessoaJuridicaNatureza> {

	PUBLICA("Pública"), PRIVADA("Privada");

	private PessoaJuridicaNatureza(String label) {
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
	 * @return Retorna uma lista com todos os <code>PessoaJuridicaType</code>.
	 */
	@Override
	public EnumMap<PessoaJuridicaNatureza, String> getEnumMap() {
		EnumMap<PessoaJuridicaNatureza, String> map = new EnumMap<PessoaJuridicaNatureza, String>(PessoaJuridicaNatureza.class);
		map.put(PessoaJuridicaNatureza.PUBLICA, "1");
		map.put(PessoaJuridicaNatureza.PRIVADA, "2");
		return map;
	}

}
