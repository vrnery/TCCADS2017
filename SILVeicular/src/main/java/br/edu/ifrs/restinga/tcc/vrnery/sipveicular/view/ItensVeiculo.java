package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.view;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.beans.property.SimpleStringProperty;

public class ItensVeiculo {

	private SimpleStringProperty data;
	private SimpleStringProperty placa;
	private SimpleStringProperty situacao;
	private SimpleStringProperty dtSituacao;
	
	public String getData() {
		return data.get();
	}
	public void setData(Calendar data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		this.data.set(sdf.format(data.getTime()));
	}
	public String getPlaca() {
		return placa.get();
	}
	public void setPlaca(String placa) {
		this.placa.set(placa);
	}
	public String getSituacao() {
		return situacao.get();
	}
	public void setSituacao(String situacao) {
		this.situacao.set(situacao);
	}
	public String getDtSituacao() {
		return dtSituacao.get();
	}
	public void setDtSituacao(String dtSituacao) {
		this.dtSituacao.set(dtSituacao);
	}
	public ItensVeiculo(Calendar data, String placa, String situacao,
			String dtSituacao) {
		super();
		this.data = new SimpleStringProperty();
		this.setData(data);
		this.placa = new SimpleStringProperty(placa);
		this.situacao = new SimpleStringProperty(situacao);
		this.dtSituacao = new SimpleStringProperty(dtSituacao);
	}
	
}
