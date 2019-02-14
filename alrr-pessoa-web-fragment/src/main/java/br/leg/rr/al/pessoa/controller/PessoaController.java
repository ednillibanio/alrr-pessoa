package br.leg.rr.al.pessoa.controller;

import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.leg.rr.al.commons.domain.EnderecoType;
import br.leg.rr.al.commons.domain.TelefoneType;
import br.leg.rr.al.commons.ejb.BairroLocal;
import br.leg.rr.al.commons.ejb.CepLocal;
import br.leg.rr.al.commons.ejb.EnderecoLocal;
import br.leg.rr.al.commons.ejb.MunicipioLocal;
import br.leg.rr.al.commons.jpa.Bairro;
import br.leg.rr.al.commons.jpa.Cep;
import br.leg.rr.al.commons.jpa.Endereco;
import br.leg.rr.al.commons.jpa.Municipio;
import br.leg.rr.al.commons.jpa.Telefone;
import br.leg.rr.al.commons.utils.CepUtils;
import br.leg.rr.al.core.dao.BeanException;
import br.leg.rr.al.core.web.controller.EntityStatusBaseController;
import br.leg.rr.al.core.web.util.FacesMessageUtils;
import br.leg.rr.al.core.web.util.FacesUtils;
import br.leg.rr.al.pessoa.jpa.Pessoa;

@Named
@ViewScoped
public class PessoaController extends EntityStatusBaseController<Pessoa, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8778872964198415981L;

	private Logger logger = LoggerFactory.getLogger(PessoaController.class);

	/*-------------------------------------------
	|             V A R I A V E I S             |
	===========================================*/

	private Endereco endereco = new Endereco();

	private Boolean semCep = false;

	private Telefone celular;

	private Telefone telefoneFixo;

	private Telefone telefoneComercial;

	private Telefone telefoneResidencial;

	private Telefone fax;

	@Email
	@Size(max = 250)
	private String email;

	/*----------------------------------
	|             E J B´ S             |
	==================================*/

	@EJB
	private MunicipioLocal localidadeBean;

	@EJB
	private BairroLocal bairroBean;

	@EJB
	private CepLocal cepBean;

	@EJB
	private EnderecoLocal enderecoBean;

	/**
	 * Método usado para buscar Localidades. A busca é realizada por parte do nome
	 * informado.
	 * 
	 * @param nome
	 *            atributo nome da entidade municipio
	 * @return lista de localidades. <code>null </code> se nenhum encontrado.
	 */
	public List<Municipio> completeLocalidadePorNome(String nome) {
		if (StringUtils.isNotBlank(nome) && (!nome.equals(" - "))) {
			try {
				return localidadeBean.buscarPorNome(nome);
			} catch (BeanException e) {
				FacesMessageUtils.addFatal(e.getMessage());
				e.printStackTrace();
			}

		}

		return null;
	}

	/**
	 * Método usado para buscar bairros na entidade Bairro. A busca é realizada por
	 * parte do nome do bairro informado.
	 * 
	 * @param nome
	 *            nome do bairro.
	 * @return lista de bairros. <code>null </code> se nenhum encontrado.
	 */
	public List<Bairro> completeBairroPorNome(String nome) {
		if (StringUtils.isNotBlank(nome) && (!nome.equals(" - "))) {
			try {
				return getBairroBean().buscarPorNome(nome);
			} catch (BeanException e) {
				FacesMessageUtils.addFatal(e.getMessage());
				e.printStackTrace();
			}

		}

		return null;
	}

	/**
	 * Busca o Endereço pelo Cep informado.
	 * 
	 */
	public void buscarEnderecoPorCep() {

		Cep cep = getCepBean().buscarCep(getEndereco().getCep());
		Endereco end = enderecoBean.preencherEnderecoPorCep(cep);
		if (end != null) {
			setEndereco(end);
		} else {
			String arg0 = CepUtils.format(getEndereco().getCep());
			UIComponent comp = FacesUtils.findComponent("cep");
			FacesMessageUtils.addError(comp, "Nenhum endereço encontrado para o cep: ".concat(arg0));
		}
	}

	/**
	 * Preenche a Municipio do Endereço a partir do Bairro selecionado.
	 * 
	 */
	public void preencherLocalidadePeloBairro(SelectEvent event) {
		Bairro bairro = endereco.getBairro();
		if (bairro != null && bairro.getLocalidade() != null) {
			endereco.setLocalidade(bairro.getLocalidade());
		}

	}

	/**
	 * Responsável por preencher as variaveis de telefone a partir do banco de
	 * dados.
	 * 
	 * @param tels
	 *            lista com os telefones que foram carregados a partir do banco de
	 *            dados e que vão ser usados pra preencher as variaveis de telefone.
	 */
	public void preencherTelefones(Set<Telefone> tels) {
		// preenche os telefones celular e fixo.
		if (tels != null && tels.size() > 0) {
			for (Telefone tel : tels) {

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
	 * @param enderecos
	 *            lista com os endereços que foram carregados a partir do banco de
	 *            dados e que vai ser usada para carregar a variável
	 *            <code>enderecoComercial</code>.
	 */
	public void preencherEnderecoComercial(Set<Endereco> enderecos) {

		if (enderecos != null && enderecos.size() > 0) {
			for (Endereco end : enderecos) {
				if (end.getTipo() == EnderecoType.COMERCIAL) {
					setEndereco(end);
					break;
				}
			}
		}
	}

	/**
	 * Responsável por preencher a variável Endereco a partir do banco de dados.
	 * 
	 * @param enderecos
	 *            lista com os endereços que foram carregados a partir do banco de
	 *            dados e que vai ser usada para carregar a variável
	 *            <code>enderecoResidencial</code>.
	 */
	public void preencherEnderecoResidencial(Set<Endereco> enderecos) {

		if (enderecos != null && enderecos.size() > 0) {
			for (Endereco end : enderecos) {
				if (end.getTipo() == EnderecoType.RESIDENCIAL) {
					setEndereco(end);
					break;
				}
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
			endereco.setLocalidade(null);
			endereco.setComplemento(null);
			endereco.setLogradouro(null);
			endereco.setNumero(null);
		}
	}

	/**
	 * Método responsável por adicoinar ou remover telefones associados a entidade
	 * {@link Pessoa}.
	 * 
	 * @param entidade
	 */
	public void atualizarTelefones(Pessoa entidade) {

		Set<Telefone> tels = entidade.getTelefones();

		atualizarCelular(tels);
		atualizarFixo(tels);
		atualizarComercial(tels);
		atualizarFax(tels);

	}

	private void atualizarFax(Set<Telefone> tels) {

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

	private void atualizarComercial(Set<Telefone> tels) {

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

	private void atualizarFixo(Set<Telefone> tels) {

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

	private void atualizarCelular(Set<Telefone> tels) {

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
	private void atualizarNumeroTelefone(Telefone origem, Set<Telefone> tels) {
		for (Telefone destino : tels) {
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
	private void removerTelefone(TelefoneType tipo, Set<Telefone> tels) {
		for (Telefone destino : tels) {
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
	public void atualizarEndereco(Pessoa pessoa) {

		if (endereco != null) {
			if (endereco.getId() == null) {
				pessoa.getEnderecos().add(getEndereco());
			} else {
				// TODO implementar.
			}
		}
	}

	/**
	 * Adiciona os telefones informados pelo usuário na tela de cadastro a entidade
	 * {@link Pessoa}. * @param pessoa entidade que será atribuida os telefones
	 * informados pelo usuário.
	 */
	public void adicionarTelefones(Pessoa pessoa) {
		atualizarTelefones(pessoa);
	}

	/**
	 * Método que adiciona o endereço comercial informado pelo usuário à entidade
	 * {@link Pessoa}.
	 * 
	 * @param pessoa
	 *            entidade que será atribuida o endereço comercial informado pelo
	 *            usuário.
	 */
	public void adicionarEnderecoComercial(Pessoa pessoa) {
		if (endereco != null && pessoa.getEnderecos() != null) {
			endereco.setTipo(EnderecoType.COMERCIAL);
			pessoa.getEnderecos().add(endereco);
		}
	}

	/**
	 * Método que adiciona o endereço residencial informado pelo usuário à entidade
	 * {@link Pessoa}.
	 * 
	 * @param pessoa
	 *            entidade que será atribuida o endereço residencial informado pelo
	 *            usuário.
	 */
	public void adicionarEnderecoResidencial(Pessoa pessoa) {
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

	public BairroLocal getBairroBean() {
		return bairroBean;
	}

	public void setBairroBean(BairroLocal bairroBean) {
		this.bairroBean = bairroBean;
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

	public void setCelular(Telefone celular) {
		this.celular = celular;
	}

	public Telefone getTelefoneFixo() {
		return telefoneFixo;
	}

	public void setTelefoneFixo(Telefone telefoneFixo) {
		this.telefoneFixo = telefoneFixo;
	}

	public Telefone getTelefoneComercial() {
		return telefoneComercial;
	}

	public void setTelefoneComercial(Telefone telefoneComercial) {
		this.telefoneComercial = telefoneComercial;
	}

	public Telefone getTelefoneResidencial() {
		return telefoneResidencial;
	}

	public void setTelefoneResidencial(Telefone telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
	}

	public Telefone getFax() {
		return fax;
	}

	public void setFax(Telefone fax) {
		this.fax = fax;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
