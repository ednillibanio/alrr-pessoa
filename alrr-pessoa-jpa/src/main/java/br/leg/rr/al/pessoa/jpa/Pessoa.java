package br.leg.rr.al.pessoa.jpa;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import br.leg.rr.al.commons.domain.TelefoneType;
import br.leg.rr.al.commons.jpa.Telefone;
import br.leg.rr.al.core.jpa.BaseEntityStatus;
import br.leg.rr.al.core.util.validator.Email;

/**
 * The persistent class for the pessoa database table.
 */
@Entity
@Table(name = "pessoa")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa extends BaseEntityStatus<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3748564215427593708L;

	@Valid
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "pessoa_id", referencedColumnName = "id", nullable = true, foreignKey = @ForeignKey(name = "pessoa_fk"))
	private Set<PessoaEndereco> enderecos = new HashSet<PessoaEndereco>();

	@Valid
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "pessoa_id", referencedColumnName = "id", nullable = true, foreignKey = @ForeignKey(name = "pessoa_fk"))
	private Set<PessoaTelefone> telefones = new HashSet<PessoaTelefone>();

	// ****************************************************************************
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "proprietario", orphanRemoval = true)
	// @JoinColumn(name = "pessoa_id", referencedColumnName = "id", nullable = true,
	// foreignKey = @ForeignKey(name = "pessoa_fk"))
	private Set<PessoaContato> contatos = new HashSet<PessoaContato>();

	// ****************************************************************************

	@Column(nullable = true, length = 250)
	@Email
	@Size(max = 250)
	private String email;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_cadastro", nullable = false, updatable = false)
	private Date dataCadastro;

	/**
	 * Representa uma foto de uma pessoa física ou uma logo de uma pessoa jurídica.
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY, optional = true)
	@Type(type = "org.hibernate.type.BinaryType")
	private byte[] imagem;

	public Set<PessoaEndereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Set<PessoaEndereco> pessoaEnderecos) {
		this.enderecos = pessoaEnderecos;
	}

	public Set<PessoaTelefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<PessoaTelefone> telefones) {
		this.telefones = telefones;
	}

	/**
	 * Varre a lista de telefones e retorna o primeiro telefone encontra na lista.
	 * Se houver mais de um telefone será ignorado.
	 * 
	 * @return celular encontrado.
	 */
	@Transient
	public Telefone getCelular() {
		if (telefones != null) {
			for (Telefone tel : telefones) {
				if (tel.getTipo().equals(TelefoneType.CELULAR)) {
					return tel;
				}
			}
		}
		return null;
	}

	/**
	 * Varre a lista de telefones e retorna o primeiro telefone encontra na lista.
	 * Se houver mais de um telefone será ignorado.
	 * 
	 * @return telefone fixo encontrado.
	 */
	@Transient
	public Telefone getFixo() {
		if (telefones != null) {
			for (Telefone tel : telefones) {
				if (tel.getTipo().equals(TelefoneType.FIXO)) {
					return tel;
				}
			}
		}
		return null;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public abstract String getNomeFantasia();

	public abstract void setNomeFantasia(String nome);

	public abstract String getDocumento();

	public abstract void setDocumento(String documento);

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public Set<PessoaContato> getContatos() {
		return contatos;
	}

	public void setContatos(Set<PessoaContato> pessoaContatos) {
		this.contatos = pessoaContatos;
	}

}
