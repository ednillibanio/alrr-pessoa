package br.leg.rr.al.pessoa.controller;

import java.util.Set;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;

import br.leg.rr.al.commons.domain.TelefoneType;
import br.leg.rr.al.commons.jpa.Telefone;
import br.leg.rr.al.commons.web.controllers.EnderecoController;
import br.leg.rr.al.core.web.controller.status.CrudViewControllerEntityStatus;
import br.leg.rr.al.localidade.domain.EnderecoType;
import br.leg.rr.al.localidade.ejb.CepLocal;
import br.leg.rr.al.localidade.ejb.EnderecoLocal;
import br.leg.rr.al.pessoa.jpa.Pessoa;
import br.leg.rr.al.pessoa.jpa.PessoaEndereco;
import br.leg.rr.al.pessoa.jpa.PessoaTelefone;

@Named
@ViewScoped
public class PessoaController extends CrudViewControllerEntityStatus<Pessoa, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8778872964198415981L;

	/*-------------------------------------------
	|             V A R I A V E I S             |
	===========================================*/

	private PessoaEndereco endereco = new PessoaEndereco();

	private Boolean semCep = false;

	private PessoaTelefone celular;

	private PessoaTelefone telefoneFixo;

	private PessoaTelefone telefoneComercial;

	private PessoaTelefone telefoneResidencial;

	private PessoaTelefone fax;

	@Inject
	private EnderecoController enderecoController;
	
	@Email
	@Size(max = 250)
	private String email;

	/*----------------------------------
	|             E J B´ S             |
	==================================*/

	@EJB
	private CepLocal cepBean;

	@EJB
	private EnderecoLocal enderecoBean;

	/**
	 * Responsável por preencher as variaveis de telefone a partir do banco de
	 * dados.
	 * 
	 * @param tels lista com os telefones que foram carregados a partir do banco de
	 *             dados e que vão ser usados pra preencher as variaveis de
	 *             telefone.
	 */
	public void preencherTelefones(Set<PessoaTelefone> tels) {
		// preenche os telefones celular e fixo.
		if (tels != null && tels.size() > 0) {
			for (PessoaTelefone tel : tels) {

				if (tel.getTipo() == TelefoneType.CELULAR) {
					setCelular(tel);

				} else if (tel.getTipo() == TelefoneType.FIXO) {
					setTelefoneFixo(tel);

				} else if (tel.getTipo() == TelefoneType.COMERCIAL) {
					setTelefoneComercial(tel);

				} else if (tel.getTipo() == TelefoneType.RESIDENCIAL) {
					setTelefoneResidencial(tel);
				} else if (tel.getTipo() == TelefoneType.FAX) {
					setFax(tel);
				}

			}
		}
	}

	/**
	 * Responsável por preencher a variável Endereco a partir do banco de dados.
	 * 
	 * @param enderecos lista com os endereços que foram carregados a partir do
	 *                  banco de dados e que vai ser usada para carregar a variável
	 *                  <code>enderecoComercial</code>.
	 *//*
	public void preencherEnderecoComercial(Set<PessoaEndereco> enderecos) {

		if (enderecos != null && enderecos.size() > 0) {
			for (PessoaEndereco end : enderecos) {
				if (end.getTipo() == EnderecoType.COMERCIAL) {
					setEndereco(end);
					break;
				}
			}
		}
	}

	*//**
	 * Responsável por preencher a variável Endereco a partir do banco de dados.
	 * 
	 * @param enderecos lista com os endereços que foram carregados a partir do
	 *                  banco de dados e que vai ser usada para carregar a variável
	 *                  <code>enderecoResidencial</code>.
	 *//*
	public void preencherEnderecoResidencial(Set<PessoaEndereco> enderecos) {

		if (enderecos != null && enderecos.size() > 0) {
			for (PessoaEndereco end : enderecos) {
				if (end.getTipo() == EnderecoType.RESIDENCIAL) {
					setEndereco(end);
					break;
				}
			}
		}
	}*/

	/**
	 * Adiciona os telefones informados pelo usuário na tela de cadastro a entidade
	 * {@link Pessoa}.
	 * 
	 * @param pessoa entidade que será atribuida os telefones informados pelo
	 *               usuário.
	 */
	protected void adicionarTelefones(Pessoa pessoa) {
		atualizarTelefones(pessoa);
	}

	/**
	 * Método responsável por adicionar ou remover telefones associados a entidade
	 * {@link Pessoa}.
	 * 
	 * @param pessoa
	 */
	protected void atualizarTelefones(Pessoa pessoa) {

		if (pessoa.getTelefones() != null) {
			Set<PessoaTelefone> tels = pessoa.getTelefones();

			atualizarCelular(tels);
			atualizarFixo(tels);
			atualizarComercial(tels);
			atualizarFax(tels);
		}

	}

	private void atualizarFax(Set<PessoaTelefone> tels) {

		if (fax != null) {

			if (StringUtils.isNotBlank(fax.getNumero())) {

				if (fax.getId() == null) { // verifica se o telefone informado é novo.
					fax.setTipo(TelefoneType.FAX);
					tels.add(fax);

				} else { // se não for, verifica se mudou o númrero e o atualiza.
					atualizarNumeroTelefone(fax, tels);
				}
			}
		} else {
			removerTelefone(TelefoneType.FAX, tels);
		}

	}

	private void atualizarComercial(Set<PessoaTelefone> tels) {

		if (telefoneComercial != null) {

			if (StringUtils.isNotBlank(telefoneComercial.getNumero())) {

				if (telefoneComercial.getId() == null) {// verifica se o telefone informado é novo.
					telefoneComercial.setTipo(TelefoneType.COMERCIAL);
					tels.add(telefoneComercial);
				} else { // se não for, verifica se mudou o númrero e o atualiza
					atualizarNumeroTelefone(telefoneComercial, tels);
				}
			}
		} else {
			removerTelefone(TelefoneType.COMERCIAL, tels);
		}

	}

	private void atualizarFixo(Set<PessoaTelefone> tels) {

		if (telefoneFixo != null) {

			if (StringUtils.isNotBlank(telefoneFixo.getNumero())) {

				if (telefoneFixo.getId() == null) {// verifica se o telefone informado é novo.
					telefoneFixo.setTipo(TelefoneType.FIXO);
					tels.add(telefoneFixo);
				} else {// se não for, verifica se mudou o númrero e o atualiza
					atualizarNumeroTelefone(telefoneFixo, tels);
				}
			}

		} else {
			removerTelefone(TelefoneType.FIXO, tels);
		}
	}

	private void atualizarCelular(Set<PessoaTelefone> tels) {

		if (celular != null) {

			if (StringUtils.isNotBlank(celular.getNumero())) {

				if (celular.getId() == null) { // verifica se o telefone informado é novo.
					celular.setTipo(TelefoneType.CELULAR);
					tels.add(celular);
				} else { // se não for, verifica se mudou o númrero e o atualiza
					atualizarNumeroTelefone(celular, tels);
				}
			}
		} else {
			removerTelefone(TelefoneType.CELULAR, tels);
		}

	}

	/**
	 * 
	 * @param origem
	 * @param tels
	 */
	private void atualizarNumeroTelefone(PessoaTelefone origem, Set<PessoaTelefone> tels) {
		for (PessoaTelefone destino : tels) {
			if (destino.getId() == origem.getId()) {
				if (destino.getDdd() != origem.getDdd()) {
					destino.setDdd(origem.getDdd());
				}
				if (destino.getNumero() != origem.getNumero()) {
					destino.setNumero(origem.getNumero());
				}
			}
		}

	}

	/**
	 * 
	 * @param origem
	 * @param tels
	 */
	private void removerTelefone(TelefoneType tipo, Set<PessoaTelefone> tels) {
		for (PessoaTelefone destino : tels) {
			if (destino.getTipo() == tipo) {
				tels.remove(destino);
			}
		}

	}

	/**
	 * Método que atualiza o endereço associados a uma pessoa. Caso seja novo,
	 * insere. Caso já exista, atualiza os dados.
	 * 
	 * @param pessoa
	 */
	protected void atualizarEndereco(Pessoa pessoa) {

		if (endereco != null) {
			if (endereco.getId() == null && endereco.getLogradouro() != null) {
				pessoa.getEnderecos().add(getEndereco());
			} else if (endereco.getId() != null && endereco.getLogradouro() == null) {
				// remover
			}
		}
	}

	/**
	 * Limpa o campo </code>cep</code> do endereco caso o valor do
	 * <code>semCep</code> seja true. Isso significa que não existe cep para o
	 * endereço informado. Esse procedimento evita que um cep invalido seja salvo.
	 */
	public void limparCep() {
		if (semCep == true) {
			endereco.setCep(null);
		} else {
			endereco.setBairro(null);
			endereco.setMunicipio(null);
			endereco.setComplemento(null);
			endereco.setLogradouro(null);
			endereco.setNumero(null);
		}
	}

	/**
	 * Método que adiciona o endereço comercial informado pelo usuário à entidade
	 * {@link Pessoa}.
	 * 
	 * @param pessoa entidade que será atribuida o endereço comercial informado pelo
	 *               usuário.
	 */
	protected void adicionarEnderecoComercial(Pessoa pessoa) {
		if (endereco != null && pessoa.getEnderecos() != null) {
			endereco.setTipo(EnderecoType.COMERCIAL);
			pessoa.getEnderecos().add(endereco);
		}
	}

	/**
	 * Método que adiciona o endereço residencial informado pelo usuário à entidade
	 * {@link Pessoa}.
	 * 
	 * @param pessoa entidade que será atribuida o endereço residencial informado
	 *               pelo usuário.
	 */
	protected void adicionarEnderecoResidencial(Pessoa pessoa) {
		if (endereco != null && pessoa.getEnderecos() != null) {
			endereco.setTipo(EnderecoType.RESIDENCIAL);
			pessoa.getEnderecos().add(endereco);
		}
	}

	public CepLocal getCepBean() {
		return cepBean;
	}

	public void setCepBean(CepLocal cepBean) {
		this.cepBean = cepBean;
	}

	public Boolean getSemCep() {
		return semCep;
	}

	public void setSemCep(Boolean semCep) {
		this.semCep = semCep;
	}

	public Telefone getCelular() {
		return celular;
	}

	public void setCelular(PessoaTelefone celular) {
		this.celular = celular;
	}

	public Telefone getTelefoneFixo() {
		return telefoneFixo;
	}

	public void setTelefoneFixo(PessoaTelefone telefoneFixo) {
		this.telefoneFixo = telefoneFixo;
	}

	public Telefone getTelefoneComercial() {
		return telefoneComercial;
	}

	public void setTelefoneComercial(PessoaTelefone telefoneComercial) {
		this.telefoneComercial = telefoneComercial;
	}

	public Telefone getTelefoneResidencial() {
		return telefoneResidencial;
	}

	public void setTelefoneResidencial(PessoaTelefone telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
	}

	public Telefone getFax() {
		return fax;
	}

	public void setFax(PessoaTelefone fax) {
		this.fax = fax;
	}

	public PessoaEndereco getEndereco() {
		return endereco;
	}

	public void setEndereco(PessoaEndereco endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EnderecoController getEnderecoController() {
		return enderecoController;
	}

	public void setEnderecoController(EnderecoController enderecoController) {
		this.enderecoController = enderecoController;
	}

}
