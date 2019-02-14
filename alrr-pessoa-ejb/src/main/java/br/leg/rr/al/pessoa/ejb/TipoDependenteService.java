package br.leg.rr.al.pessoa.ejb;

import javax.ejb.Stateless;
import javax.inject.Named;

import br.leg.rr.al.core.dao.BaseJPADaoStatus;
import br.leg.rr.al.pessoa.jpa.DependenteTipo;

@Named
@Stateless
public class TipoDependenteService extends BaseJPADaoStatus<DependenteTipo, Integer> implements TipoDependenteLocal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6006155766035299129L;

	@Override
	public Boolean jaExiste(DependenteTipo entidade) {
		return false;
	}

}
