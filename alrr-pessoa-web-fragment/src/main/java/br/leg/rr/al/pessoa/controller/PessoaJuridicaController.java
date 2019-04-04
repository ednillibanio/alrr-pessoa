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

import br.leg.rr.al.core.CoreUtilsValidationMessages;
import br.leg.rr.al.core.web.controller.WebCamController;
import br.leg.rr.al.core.web.controller.status.CrudViewControllerEntityStatus;
import br.leg.rr.al.core.web.util.FacesMessageUtils;
import br.leg.rr.al.core.web.util.ImagemHelper;
import br.leg.rr.al.pessoa.domain.PessoaJuridicaNatureza;
import br.leg.rr.al.pessoa.ejb.PessoaJuridicaLocal;
import br.leg.rr.al.pessoa.jpa.PessoaJuridica;

@Named
@ViewScoped
public class PessoaJuridicaController extends CrudViewControllerEntityStatus<PessoaJuridica, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2643910745846088125L;

	@Resource
	private UserTransaction utx;

	/*-------------------------------------------------------------
	|             F I L T R O S  DA  P E S Q U I S A             |
	=============================================================*/
	private String nome;
	private String cnpj;
	private PessoaJuridicaNatureza natureza;

	/*-------------------------------------------
	|             V A R I A V E I S             |
	===========================================*/

	/*-----------------------------------------------------------------
	|             C O N T R O L L E R S  E  H E L P E R S             |
	=================================================================*/
	/**
	 * Helper que irá tratar a imagem capturada pela webcam ou pelo FileUpload que
	 * carrega a imagem. Os controller que fazem a tarefa são o WebCamController e
	 * ImagemUploadController.
	 */
	private ImagemHelper imagemHelper;

	/**
	 * Usado para ativar a webcam do dialog-capturar-imagem.
	 */
	@Inject
	private WebCamController webCamController;

	@Inject
	private PessoaController pessoaController;

	/*----------------------------------
	|             E J B´ S             |
	==================================*/
	@EJB
	private PessoaJuridicaLocal bean;

	@PostConstruct
	public void init() {
		setEntity(Faces.getFlashAttribute("entity"));
		setBean(bean);
		jaExisteMsg = "Pessoa com o CNPJ informado já existe.";
		if (isEditar()) {
			carregarEntity();
		} else {
			setEntity(createEntity());
		}

	}

	@Override
	protected void preAtualizar() {
		super.preAtualizar();
		PessoaJuridica pj = getEntity();

		if (pj != null) {

			byte[] conteudo = getImagemHelper().getImagem();
			if (conteudo != null) {
				pj.setImagem(conteudo);
			}

			pessoaController.atualizarTelefones(getEntity());
			pessoaController.atualizarEndereco(getEntity());
			pj.setEmail(pessoaController.getEmail());
		} else {
			FacesMessageUtils.addError("Entidade PessoaJuridica está null.");
		}

	}

	@Override
	protected void preInserir() {
		super.preInserir();
		PessoaJuridica pj = getEntity();

		if (pj != null) {
			byte[] conteudo = getImagemHelper().getImagem();
			if (conteudo != null) {
				pj.setImagem(conteudo);
			}

			pessoaController.adicionarTelefones(getEntity());
			pessoaController.adicionarEnderecoComercial(getEntity());
			pj.setEmail(pessoaController.getEmail());

		} else {
			FacesMessageUtils.addError("Entidade PessoaJuridica está null.");
		}
	}

	@Override
	public void carregarEntity() {

		try {
			utx.begin();
			PessoaJuridica pj = getEntity();
			if (pj != null && pj.getId() != null) {
				pj = getBean().buscar(pj.getId());

				// fetch´s
				pj.getEnderecos().size();
				pj.getTelefones().size();

				// transforma a imagem.
				if (pj.getImagem() != null) {
					getImagemHelper().setImagem(pj.getImagem());
				}

				pessoaController.preencherEnderecoComercial(pj.getEnderecos());
				pessoaController.preencherTelefones(pj.getTelefones());
				pessoaController.setEmail(pj.getEmail());
				setEntity(pj);

			}

		} catch (NotSupportedException | SystemException e) {
			FacesMessageUtils.addFatal(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				utx.commit();
			} catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException
					| RollbackException | SystemException e) {
				FacesMessageUtils.addFatal(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.leg.al.rr.core.web.controller.CrudController#pesquisar()
	 */
	@Override
	public String pesquisar() {
		Map<String, Object> filtros = new HashMap<String, Object>();
		filtros.put(PessoaJuridicaLocal.PESQUISAR_PARAM_NOME_FANTASIA, nome);
		filtros.put(PessoaJuridicaLocal.PESQUISAR_PARAM_CNPJ, cnpj);
		filtros.put(PessoaJuridicaLocal.PESQUISAR_PARAM_NATUREZA, natureza);
		filtros.put(PessoaJuridicaLocal.PESQUISAR_PARAM_FETCH_TELEFONE, true);

		setEntities(getBean().pesquisar(filtros));
		if (getEntities().size() > 0) {
			createDataModel(getEntities());
		} else {
			FacesMessageUtils.addError(CoreUtilsValidationMessages.REGISTRO_NAO_ENCONTRADO);
		}

		return null;

	}

	public void ativarWebCam() {
		getWebCamController().ativarWebCam();
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public PessoaJuridicaNatureza getNatureza() {
		return natureza;
	}

	public void setNatureza(PessoaJuridicaNatureza natureza) {
		this.natureza = natureza;
	}

}
