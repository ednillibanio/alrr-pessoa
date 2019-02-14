/*******************************************************************************
 * Copyright (c) 2017, KMDR Consultoria e Serviços, Boa Vista, RR - Brasil.
 * Todos os direitos reservados. Este programa é propriedade da Assembleia Legislativa do Estado de Roraima e não é permitida a distribuição, alteração ou cópia da mesma sem prévia autoriazação.
 ******************************************************************************/
package br.leg.rr.al.pessoa.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.web.util.FacesMessageUtils;
import br.leg.rr.al.pessoa.ejb.PessoaFisicaLocal;
import br.leg.rr.al.pessoa.jpa.PessoaFisica;

@Named
@RequestScoped
public class PessoaFisicaCompleteController {

	@EJB
	private PessoaFisicaLocal<PessoaFisica> bean;

	/**
	 * Método usado para buscar pessoa física na entidade PessoaFisica. A busca é
	 * realizada por parte do nome da pessoa informada.
	 * 
	 * @param nome
	 *            nome da pessoa física.
	 * @return lista de pessoas físicas. <code>null </code> se nenhuma encontrada.
	 */
	public List<PessoaFisica> completeByNome(String nome) {
		if (StringUtils.isNotBlank(nome) && (!nome.equals(" - "))) {
			try {
				return bean.buscarPorNome(nome);
			} catch (BeanException e) {
				FacesMessageUtils.addFatal(e.getMessage());
				e.printStackTrace();
			}

		}

		return null;
	}

}
