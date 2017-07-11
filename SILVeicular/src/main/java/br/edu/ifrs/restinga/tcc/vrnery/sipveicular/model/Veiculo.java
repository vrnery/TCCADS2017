package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.model;

import java.awt.image.BufferedImage;
import java.util.Calendar;

public class Veiculo {

	private BufferedImage veiculoImg;
	private Placa placa;
	private Calendar data;
	private String situacao;
	private String dtSituacao;
	
	public BufferedImage getVeiculoImg() {
		return veiculoImg;
	}
	public void setVeiculoImg(BufferedImage veiculoImg) {
		this.veiculoImg = veiculoImg;
	}
	public Placa getPlaca() {
		return placa;
	}
	public void setPlaca(Placa placa) {
		//System.out.println("\"3\": "+this.placa.getPlacaSegTo());
		this.placa = placa;
		//System.out.println("\"4\": "+placa.getPlacaSegTo());
	}
	public Calendar getData() {
		return data;
	}
	public void setData(Calendar data) {
		this.data = data;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getDtSituacao() {
		return dtSituacao;
	}
	public void setDtSituacao(String dtSituacao) {
		this.dtSituacao = dtSituacao;
	}
	
	public Veiculo() {}
	
	public Veiculo(BufferedImage veiculoImg) {
		this.veiculoImg = veiculoImg;
		this.data = Calendar.getInstance();
		this.placa = new Placa();
	}
}
