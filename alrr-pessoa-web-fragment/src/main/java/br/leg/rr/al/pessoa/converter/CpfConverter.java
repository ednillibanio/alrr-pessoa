package br.leg.rr.al.pessoa.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import br.leg.rr.al.pessoa.utils.CpfUtils;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 03-04-2018
 */
@Named
public class CpfConverter implements Converter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1403935515422759561L;

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent component, String value) {
		return CpfUtils.unformat(value);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		return CpfUtils.format((String) value);
	}

}
