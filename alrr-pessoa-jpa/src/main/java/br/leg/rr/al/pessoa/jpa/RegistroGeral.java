/*******************************************************************************
 * Copyright (c) 2017, KMDR Consultoria e Serviços, Boa Vista, RR - Brasil.
 * Todos os direitos reservados. Este programa é propriedade da Assembleia Legislativa do Estado de Roraima e não é permitida a distribuição, alteração ou cópia da mesma sem prévia autoriazação.
 ******************************************************************************/
package br.leg.rr.al.pessoa.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.leg.rr.al.core.domain.OrgaoExpeditor;
import br.leg.rr.al.localidade.domain.UfType;

@Embeddable
public class RegistroGeral implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1297023597793999598L;

	@Column(length = 10, nullable = true)
	private String numero;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_expedicao")
	private Date dataExpedicao;

	@Enumerated(EnumType.STRING)
	@Column(name = "orgao_expeditor", length = 5, nullable = true)
	private OrgaoExpeditor orgaoExpeditor;

	@Enumerated(EnumType.STRING)
	@Column(name = "uf_expeditor", length = 2, nullable = true)
	private UfType ufExpeditor;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Date getDataExpedicao() {
		return dataExpedicao;
	}

	public void setDataExpedicao(Date dataExpedicao) {
		this.dataExpedicao = dataExpedicao;
	}

	public OrgaoExpeditor getOrgaoExpeditor() {
		return orgaoExpeditor;
	}

	public void setOrgaoExpeditor(OrgaoExpeditor orgaoExpeditor) {
		this.orgaoExpeditor = orgaoExpeditor;
	}

	public UfType getUfExpeditor() {
		return ufExpeditor;
	}

	public void setUfExpeditor(UfType ufExpeditor) {
		this.ufExpeditor = ufExpeditor;
	}

}
