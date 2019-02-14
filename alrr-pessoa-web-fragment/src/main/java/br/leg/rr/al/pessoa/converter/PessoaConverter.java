/*******************************************************************************
 * Copyright (c) 2017, KMDR Consultoria e Serviços, Boa Vista, RR - Brasil.
 * Todos os direitos reservados. Este programa é propriedade da Assembleia Legislativa do Estado de Roraima e não é permitida a distribuição, alteração ou cópia da mesma sem prévia autoriazação.
 ******************************************************************************/
package br.leg.rr.al.pessoa.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Named;

import br.leg.rr.al.core.jpa.BaseEntityStatus;
import br.leg.rr.al.pessoa.jpa.Pessoa;
import br.leg.rr.al.pessoa.jpa.PessoaFisica;
import br.leg.rr.al.pessoa.utils.CnpjUtils;
import br.leg.rr.al.pessoa.utils.CpfUtils;

/**
 * Converte objeto para pessoa fisica ou juridica.
 * 
 * @author ednil
 */
@Named
public class PessoaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component,
			String value) {

		if (value != null) {
			Pessoa entity = (Pessoa) this.getAttributesFrom(component).get(
					value);
			if (entity instanceof PessoaFisica) {
				entity.setDocumento(CpfUtils.unformat(entity
						.getDocumento()));
			} else {
				entity.setDocumento(CnpjUtils.unformat(entity.getDocumento()));
			}
			return this.getAttributesFrom(component).get(value);
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent component,
			Object value) {

		if (value != null && !"".equals(value) && value instanceof Pessoa) {

			Pessoa entity = (Pessoa) value;
			if (entity instanceof PessoaFisica) {
				entity.setDocumento(CpfUtils.format(entity
						.getDocumento()));
			} else {
				entity.setDocumento(CnpjUtils.format(entity.getDocumento()));
			}

			// adiciona item como atributo do componente
			this.addAttribute(component, entity);

			Long codigo = entity.getId();
			if (codigo != null) {
				return String.valueOf(codigo);
			}
		}

		return (String) value;
	}

	protected void addAttribute(UIComponent component, BaseEntityStatus<Long> o) {
		// Se for nulo, da erro na aplicação.
		if (o.getId() == null) {
			o.setId((long) 0);
		}
		// Id é a chave.
		String key = o.getId().toString();
		this.getAttributesFrom(component).put(key, o);
	}

	protected Map<String, Object> getAttributesFrom(UIComponent component) {
		return component.getAttributes();
	}

}
