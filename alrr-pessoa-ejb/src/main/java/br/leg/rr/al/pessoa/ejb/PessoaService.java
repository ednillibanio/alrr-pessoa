package br.leg.rr.al.pessoa.ejb;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Named;

import br.leg.rr.al.commons.domain.TelefoneType;
import br.leg.rr.al.commons.jpa.Telefone;
import br.leg.rr.al.core.dao.BaseJPADaoStatus;
import br.leg.rr.al.localidade.domain.EnderecoType;
import br.leg.rr.al.localidade.jpa.Endereco;
import br.leg.rr.al.pessoa.jpa.Pessoa;
import br.leg.rr.al.pessoa.jpa.PessoaEndereco;
import br.leg.rr.al.pessoa.jpa.PessoaTelefone;

@Named
@Stateless
public class PessoaService<T extends Pessoa> extends BaseJPADaoStatus<T, Long> implements PessoaLocal<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7621828058878834821L;

	@Override
	public Telefone getCelular(Pessoa pessoa) {
		return getTelefone(pessoa, TelefoneType.CELULAR);
	}

	@Override
	public Telefone getTelefoneResidencial(Pessoa pessoa) {
		return getTelefone(pessoa, TelefoneType.RESIDENCIAL);
	}

	@Override
	public Telefone getTelefoneComercial(Pessoa pessoa) {
		return getTelefone(pessoa, TelefoneType.COMERCIAL);
	}

	@Override
	public Telefone getTelefone(Pessoa pessoa, TelefoneType tipo) {
		for (Telefone tel : pessoa.getTelefones()) {
			if (tel.getTipo() == tipo) {
				return tel;
			}
		}

		return null;
	}

	@Override
	public void removerTelefone(Pessoa pessoa, TelefoneType tipo) {

		Iterator<PessoaTelefone> iter = pessoa.getTelefones().iterator();
		while (iter.hasNext()) {
			Telefone tel = iter.next();
			if (tel.getTipo() == tipo) {
				iter.remove();
			}
		}
	}

	@Override
	public Endereco getEnderecoResidencial(Pessoa pessoa) {
		return getEndereco(pessoa, EnderecoType.RESIDENCIAL);
	}

	@Override
	public Endereco getEndereco(Pessoa pessoa, EnderecoType tipo) {
		for (Endereco endereco : pessoa.getEnderecos()) {
			if (endereco.getTipo() == tipo) {
				return endereco;
			}
		}

		return null;
	}

	@Override
	public void removerEndereco(Pessoa pessoa, EnderecoType tipo) {

		Iterator<PessoaEndereco> iter = pessoa.getEnderecos().iterator();
		while (iter.hasNext()) {
			Endereco endereco = iter.next();
			if (endereco.getTipo() == tipo) {
				iter.remove();
			}
		}
	}

	@Override
	public Telefone getTelefoneComercial(List<Telefone> telefones) {
		// TODO Auto-generated method stub
		return null;
	}

	// FIXME ver como vai fazer para a implementação desse método ou se vai
	// passar para pessoa fisica e juridica.
	@Override
	public List<T> filtrarContatosPorNome(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean jaExiste(T entidade) {
		return false;
	}

}
