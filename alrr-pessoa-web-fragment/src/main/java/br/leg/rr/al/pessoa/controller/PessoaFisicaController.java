package br.leg.rr.al.pessoa.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.leg.rr.al.commons.jpa.Telefone;
import br.leg.rr.al.core.CoreUtilsValidationMessages;
import br.leg.rr.al.core.utils.MessageUtils;
import br.leg.rr.al.core.utils.StringHelper;
import br.leg.rr.al.core.web.controller.WebCamController;
import br.leg.rr.al.core.web.controller.status.CrudViewControllerEntityStatus;
import br.leg.rr.al.core.web.util.FacesMessageUtils;
import br.leg.rr.al.core.web.util.ImagemHelper;
import br.leg.rr.al.localidade.ejb.CepLocal;
import br.leg.rr.al.localidade.ejb.EnderecoLocal;
import br.leg.rr.al.localidade.jpa.Bairro;
import br.leg.rr.al.localidade.jpa.Pais;
import br.leg.rr.al.pessoa.CadastroValidationMessages;
import br.leg.rr.al.pessoa.domain.EstadoCivilType;
import br.leg.rr.al.pessoa.domain.PessoaType;
import br.leg.rr.al.pessoa.domain.SexoType;
import br.leg.rr.al.pessoa.ejb.PessoaFisicaLocal;
import br.leg.rr.al.pessoa.ejb.TipoDependenteLocal;
import br.leg.rr.al.pessoa.jpa.Pessoa;
import br.leg.rr.al.pessoa.jpa.PessoaFisica;
import br.leg.rr.al.pessoa.utils.CpfUtils;

/**
 * @author Ednil Libanio da Costa Junior
 * @date 17-04-2018
 */
@Named
@ViewScoped
public class PessoaFisicaController extends CrudViewControllerEntityStatus<PessoaFisica, Long> {

	Logger logger = LoggerFactory.getLogger(PessoaFisicaController.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -8415340510820206352L;

	@Resource
	private UserTransaction utx;

	private PessoaType selectedPessoaTipo;

	// ************ FILTROS DE PESQUISA ************//
	private String nome;
	private String cpf;
	private SexoType sexo;
	private EstadoCivilType[] estadosCivisSelecionados;
	// ********************************************//

	/**
	 * Helper que irá tratar a imagem capturada pela webcam ou pelo FileUpload que
	 * carrega a imagem. Os controller que fazem a tarefa são o WebCamController e
	 * ImagemUploadController.
	 */
	@Inject
	private ImagemHelper imagemHelper;

	/**
	 * Usado para ativar a webcam do dialog-capturar-imagem.
	 */
	@Inject
	private WebCamController webCamController;

	private Telefone celular;

	private Telefone fixo;

	@Inject
	private PessoaController pessoaController;

	private Bairro bairro;

	@EJB
	private PessoaFisicaLocal<PessoaFisica> bean;

	@EJB
	private TipoDependenteLocal tipoDependenteBean;

	@EJB
	private CepLocal cepBean;

	@EJB
	private EnderecoLocal enderecoBean;

	private String cep;

	@PostConstruct
	public void init() {
		setEntity(Faces.getFlashAttribute("entity"));
		setBean(bean);
		jaExisteMsg = "Pessoa com o CPF informado já existe.";

		if (isEditar()) {
			carregarEntity();
		} else {
			setEntity(createEntity());
		}

	}

	@Override
	protected void preInserir() {
		super.preInserir();
		PessoaFisica pf = getEntity();

		if (pf != null) {
			byte[] conteudo = getImagemHelper().getImagem();
			if (conteudo != null) {
				pf.setImagem(conteudo);
			}

			pessoaController.adicionarTelefones(getEntity());
			pessoaController.adicionarEnderecoResidencial(getEntity());

		} else {
			FacesMessageUtils.addError(CoreUtilsValidationMessages.ENTIDADE_ESTA_NULL, PessoaFisica.class);
			logger.error(
					MessageUtils.formatMessage(CoreUtilsValidationMessages.ENTIDADE_ESTA_NULL, PessoaFisica.class));
		}
	}

	@Override
	protected void preAtualizar() {
		super.preAtualizar();
		atualizarPessoaFisica();

	}

	/**
	 * Método que faz a parte de atualização dos dados da entidade pessoa física e
	 * pessoa antes de serem inseridos. Esse método foi criado por causa das classes
	 * que herdam de PessoaFisica. Por exemplo, servidor público.
	 * 
	 * @see ServidorPublicoController#preInserir()
	 * @see ServidorPublicoController#preAtualizar()
	 */
	public void atualizarPessoaFisica() {
		PessoaFisica pf = getEntity();

		if (pf != null) {

			byte[] conteudo = getImagemHelper().getImagem();
			if (conteudo != null) {
				pf.setImagem(conteudo);
			}

			pessoaController.atualizarTelefones(getEntity());
			// pessoaController.atualizarEndereco(getEntity());
		} else {
			FacesMessageUtils.addError(CoreUtilsValidationMessages.ENTIDADE_ESTA_NULL, PessoaFisica.class);
			logger.error(
					MessageUtils.formatMessage(CoreUtilsValidationMessages.ENTIDADE_ESTA_NULL, PessoaFisica.class));
		}
	}

	@Override
	public void carregarEntity() {

		try {
			utx.begin();
			PessoaFisica pf = getEntity();

			if (pf != null && pf.getId() != null) {

				pf = getBean().buscar(pf.getId());

				// fetch´s Pessoa Fisica
				if (pf.getNaturalidade() != null) {
					pf.getNaturalidade().getId();
				}

				// transforma a imagem.
				if (pf.getImagem() != null) {
					getImagemHelper().setImagem(pf.getImagem());
				}

				// fetch´s Pessoa
				pf.getEnderecos().size();
				pf.getTelefones().size();

				preencherDadosPessoa(pf);
				setEntity(pf);

			}
		} catch (NotSupportedException | SystemException e) {
			logger.error(fatal, e.getMessage());
			FacesMessageUtils.addFatal(e.getMessage());
		} catch (Exception e) {
			logger.error(fatal, e.getMessage());
			FacesMessageUtils.addFatal(e.getMessage());
		} finally {
			try {
				utx.commit();
			} catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException
					| RollbackException | SystemException e) {
				logger.error(fatal, e.getMessage());
				FacesMessageUtils.addFatal(e.getMessage());
			}
		}

	}

	/**
	 * Método que preenche o formulário com os dados da entidade Pessoa. São
	 * preenchidos os dados de endereço, telefones e email.
	 * 
	 * @param p entidade que contém os dados a serem preenchidos.
	 */
	public void preencherDadosPessoa(Pessoa p) {

		if (p.getImagem() != null) {
			getImagemHelper().setImagem(p.getImagem());
		}

		pessoaController.setEntity(p);
		pessoaController.preencherEnderecoResidencial(p.getEnderecos());
		pessoaController.preencherTelefones(p.getTelefones());
		pessoaController.setEmail(p.getEmail());
	}

	public void limparDadosPessoa() {

		getImagemHelper().setImagem(null);
		pessoaController.setEntity(null);
		pessoaController.setEndereco(null);
		pessoaController.setEmail(null);
		pessoaController.setCelular(null);
		pessoaController.setTelefoneComercial(null);
		pessoaController.setFax(null);
		pessoaController.setTelefoneFixo(null);
		pessoaController.setTelefoneResidencial(null);
	}

	/**
	 * Transformar primeira letra de cada palavra em maiúscula.
	 */
	public void capitalizeNome() {
		String nome = StringHelper.capitalizeFully(getEntity().getNome());
		getEntity().setNome(nome);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.leg.al.rr.core.web.controller.CrudController#pesquisar()
	 */
	@Override
	public String pesquisar() {

		Map<String, Object> filtros = new HashMap<String, Object>();

		filtros.put(PessoaFisicaLocal.PESQUISAR_PARAM_NOME, getNome());
		filtros.put(PessoaFisicaLocal.PESQUISAR_PARAM_CPF, getCpf());
		filtros.put(PessoaFisicaLocal.PESQUISAR_PARAM_SEXO, getSexo());
		filtros.put(PessoaFisicaLocal.PESQUISAR_PARAM_ESTADOS_CIVIS, getEstadosCivisSelecionados());
		filtros.put(PessoaFisicaLocal.PESQUISAR_PARAM_FETCH_TELEFONE, true);

		setEntities(getBean().pesquisar(filtros));
		if (getEntities().size() > 0) {
			createDataModel(getEntities());
		} else {
			FacesMessageUtils.addError(CoreUtilsValidationMessages.REGISTRO_NAO_ENCONTRADO);
		}

		return null;

	}

	/**
	 * Método utilizado para verificar se pessoa física já está cadastrada. Se
	 * existir, lança uma exceção e recria a instação de PessoaFisica.
	 * 
	 * @param cpf número do cpf que será verificado na base de dados.
	 */
	public void verificaSeExiste() {
		String cpf = getEntity().getCpf();

		if (bean.verificaSeJaExistePorCPF(cpf)) {
			FacesMessageUtils.addError(CadastroValidationMessages.CPF_JA_EXISTE, (Object) CpfUtils.format(cpf));
			limparDadosPessoa();
		}
	}

	public void completarPorCpf() {

		PessoaFisica pf = bean.buscarPorCpf(getEntity().getCpf());
		if (pf != null) {
			setEntity(pf);
			carregarEntity();
		}
	}

	public void ativarWebCam() {
		getWebCamController().ativarWebCam();
	}

	public PessoaType[] getPessoasTipo() {
		return PessoaType.values();
	}

	public Telefone getCelular() {
		return celular;
	}

	public void setCelular(Telefone celular) {
		this.celular = celular;
	}

	public Telefone getFixo() {
		return fixo;
	}

	public void setFixo(Telefone fixo) {
		this.fixo = fixo;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public PessoaType getSelectedPessoaTipo() {
		return selectedPessoaTipo;
	}

	public void setSelectedPessoaTipo(PessoaType selectedPessoaTipo) {
		this.selectedPessoaTipo = selectedPessoaTipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public SexoType getSexo() {
		return sexo;
	}

	public void setSexo(SexoType sexo) {
		this.sexo = sexo;
	}

	public ImagemHelper getImagemHelper() {
		return imagemHelper;
	}

	public void setImagemHelper(ImagemHelper imagemHelper) {
		this.imagemHelper = imagemHelper;
	}

	public WebCamController getWebCamController() {
		return webCamController;
	}

	public void setWebCamController(WebCamController webCamController) {
		this.webCamController = webCamController;
	}

	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return the estadosCivisSelecionados
	 */
	public EstadoCivilType[] getEstadosCivisSelecionados() {
		return estadosCivisSelecionados;
	}

	/**
	 * @param estadosCivisSelecionados the estadosCivisSelecionados to set
	 */
	public void setEstadosCivisSelecionados(EstadoCivilType[] estadosCivisSelecionados) {
		this.estadosCivisSelecionados = estadosCivisSelecionados;
	}

}
