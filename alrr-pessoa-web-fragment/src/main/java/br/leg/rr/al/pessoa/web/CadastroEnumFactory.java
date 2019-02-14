/*******************************************************************************
 * Copyright (c) 2017, KMDR Consultoria e Serviços, Boa Vista, RR - Brasil.
 * Todos os direitos reservados. Este programa é propriedade da Assembleia Legislativa do Estado de Roraima e não é permitida a distribuição, alteração ou cópia da mesma sem prévia autoriazação.
 ******************************************************************************/
package br.leg.rr.al.pessoa.web;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import br.leg.rr.al.commons.domain.TelefoneType;
import br.leg.rr.al.core.domain.OrgaoExpeditor;
import br.leg.rr.al.pessoa.domain.EstadoCivilType;
import br.leg.rr.al.pessoa.domain.PessoaJuridicaNatureza;
import br.leg.rr.al.pessoa.domain.SexoType;

/**
 * Classe de escopo de aplicação, responsável por fabricar Enums que são
 * considerados uteis. Os métodos retornam um arranjo de enums que podem ser
 * preenchidos em componentes primefaces. <br>
 * 
 * @author Ednil Libanio da Costa Junior
 * @date 19-04-2018
 */
@ApplicationScoped
@Named
public class CadastroEnumFactory {

	public SexoType[] getSexos() {
		return SexoType.values();
	}

	public PessoaJuridicaNatureza[] getPessoaJuridicaNaturezas() {
		return PessoaJuridicaNatureza.values();
	}

	public TelefoneType[] getTiposTelefones() {
		return TelefoneType.values();
	}

	public OrgaoExpeditor[] getOrgaosExpeditores() {
		return OrgaoExpeditor.values();
	}

	/**
	 * 
	 * 
	 * @return estados civis de Pessoa Física.
	 */
	public EstadoCivilType[] getEstadosCivis() {
		return EstadoCivilType.values();
	}

}
