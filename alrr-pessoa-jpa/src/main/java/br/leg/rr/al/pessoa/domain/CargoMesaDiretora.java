package br.leg.rr.al.pessoa.domain;

import java.util.EnumMap;

import br.leg.rr.al.core.jpa.BasicEnum;

public enum CargoMesaDiretora implements BasicEnum<CargoMesaDiretora> {

	PRESIDENTE("Presidente"), PRIMEIRO_VICE("1ª Vice-presidente");

	private CargoMesaDiretora(String label) {
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
	public EnumMap<CargoMesaDiretora, String> getEnumMap() {
		EnumMap<CargoMesaDiretora, String> map = new EnumMap<CargoMesaDiretora, String>(CargoMesaDiretora.class);
		map.put(CargoMesaDiretora.PRESIDENTE, "1");
		map.put(CargoMesaDiretora.PRIMEIRO_VICE, "2");

		return map;
	}

}
