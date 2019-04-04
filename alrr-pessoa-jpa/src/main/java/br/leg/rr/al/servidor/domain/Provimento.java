/*******************************************************************************
 * ALE-RR Licença
 * Copyright (C) 2018, ALE-RR
 * Boa Vista, RR - Brasil
 * Todos os direitos reservados.
 * 
 * Este programa é propriedade da Assembleia Legislativa do Estado de Roraima e 
 * não é permitida a distribuição, alteração ou cópia da mesma sem prévia autoriazação.
 ******************************************************************************/
package br.leg.rr.al.servidor.domain;

import java.util.EnumMap;

import br.leg.rr.al.core.jpa.BasicEnum;

/**
 * Dominio do Provimento do Servidor Público.
 * 
 * @author Ednil Libanio da Costa Junior
 * @date 18-04-2018
 */
public enum Provimento implements BasicEnum<Provimento> {

	EFETIVO("Efetivo"), EM_COMISSAO("Comissionado");

	private Provimento(String label) {
		this.label = label;

	}

	private String label;

	@Override
	/**
	 * Label do provimento. Por exemplo, Efetivo.
	 * 
	 * @return label do provimento escolhido (Masculino ou Feminino).
	 */
	public String getLabel() {
		return label;
	}

	@Override
	/**
	 * Retorna o label do provimento escolhido.
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
	public EnumMap<Provimento, String> getEnumMap() {
		EnumMap<Provimento, String> map = new EnumMap<Provimento, String>(Provimento.class);
		map.put(Provimento.EFETIVO, "1");
		map.put(Provimento.EM_COMISSAO, "2");
		return map;
	}

}
