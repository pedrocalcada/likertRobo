package br.com.itau.robo.likert.api.robolikertapi.viewmodel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class PastasViewModel {

	protected String nome;
	protected String id;
	@JsonInclude(Include.NON_NULL)
	protected List<SubPastaViewModel> subPastas;

	public String getId() {
		return id;
	}

	public void setId(String string) {
		this.id = string;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<SubPastaViewModel> getSubPastas() {
		return subPastas;
	}

	public void setSubPastas(List<SubPastaViewModel> subPastas) {
		this.subPastas = subPastas;
	}

}
