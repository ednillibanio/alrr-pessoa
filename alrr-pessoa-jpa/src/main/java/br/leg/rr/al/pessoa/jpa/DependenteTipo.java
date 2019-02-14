/*******************************************************************************
 * Copyright (c) 2017, KMDR Consultoria e Serviços, Boa Vista, RR - Brasil.
 * Todos os direitos reservados. Este programa é propriedade da Assembleia Legislativa do Estado de Roraima e não é permitida a distribuição, alteração ou cópia da mesma sem prévia autoriazação.
 ******************************************************************************/
package br.leg.rr.al.pessoa.jpa;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.leg.rr.al.core.jpa.Dominio;

/**
 *
 * @author heliton
 */
@Entity
@Table(name = "dependente_tipo")
public class DependenteTipo extends Dominio {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5453764614617984322L;

}
