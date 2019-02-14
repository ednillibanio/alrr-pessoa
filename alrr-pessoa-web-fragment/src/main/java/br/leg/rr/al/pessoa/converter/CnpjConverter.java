/**************************************************************************************
 * Copyright (c) 2017, Assembleia Legislativa do Estado de Roraima, Boa Vista - RR.
 * 
 * Todos os direitos reservados. Este programa é propriedade da Assembleia Legislativa 
 * do Estado de Roraima e não é permitida a distribuição, alteração ou cópia da mesma 
 * sem prévia autoriazação.
 **************************************************************************************/
package br.leg.rr.al.pessoa.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import br.leg.rr.al.pessoa.utils.CnpjUtils;

@Named
public class CnpjConverter implements Converter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6768134191858673601L;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent component, String value) {
		return CnpjUtils.unformat(value);

	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		return CnpjUtils.format(value.toString());
	}

}
