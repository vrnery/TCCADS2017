package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.controller;

import br.edu.ifrs.restinga.tcc.vrnery.placasws.definicoes.PlacasWSRequest;
import br.edu.ifrs.restinga.tcc.vrnery.placasws.definicoes.PlacasWSResponse;
import br.edu.ifrs.restinga.tcc.vrnery.placasws.service.ConsultaPlacasWSService;
import br.edu.ifrs.restinga.tcc.vrnery.placasws.service.IConsultaPlacasWS;

public class ClientePlacasWSController {

	private static PlacasWSRequest plRequest;
	private static PlacasWSResponse plResponse;
	private String situacao;
	private String data;
	
	public String getSituacao() {
		return this.situacao;
	}
	public String getData() {
		return this.data;
	}
	
	public PlacasWSResponse getPlResponse() {
		return plResponse;
	}

	public ClientePlacasWSController() {}
	
	public ClientePlacasWSController(String placa) {
		plRequest = new PlacasWSRequest();
		plRequest.setPlaca(placa);
		// Inicia a fabrica dos proxies
		ConsultaPlacasWSService consultarPlacasWSFactory = new ConsultaPlacasWSService();
		// Obtem um proxy
		IConsultaPlacasWS interfaceConsultarPlacasWS = consultarPlacasWSFactory.getConsultaPlacasWSPort();
		// Executar o metodo remoto
		plResponse = interfaceConsultarPlacasWS.consultarPlaca(plRequest);
		this.situacao = plResponse.getSituacao();
		this.data = plResponse.getData();
	}

	/*public static void main(String[] args) {
		// Inicia a fabrica dos proxies
		ConsultaPlacasWSService consultarPlacasWSFactory = new ConsultaPlacasWSService();
		// Obtem um proxy
		IConsultaPlacasWS interfaceConsultarPlacasWS = consultarPlacasWSFactory.getConsultaPlacasWSPort();
		// Executar o metodo remoto
		plResponse = interfaceConsultarPlacasWS.consultarPlaca(plRequest);
	}*/
}
