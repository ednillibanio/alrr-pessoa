package br.leg.rr.al.pessoa.domain;

public enum AniversarioPeriodo {

	HOJE("Hoje"), AMANHA("Amanhã"), SEMANA("Semana"), MES("Mês");

	private AniversarioPeriodo(String label) {
		this.label = label;

	}

	private String label;

	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return label;
	}

}
