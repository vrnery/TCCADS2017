package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.controller;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.imgcodecs.Imgcodecs;

import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception.FiltroControllerException;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception.ProcessamentoControllerException;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.model.Placa;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.model.Veiculo;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.view.ItensVeiculo;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.view.TelaPrincipal;

public class ProcessamentoController {

	private BufferedImage buffer;
	private Veiculo bufVeiculo;
	private ClientePlacasWSController webservice;
	
	public void processarBufferedImage(BufferedImage bufferedImage) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		//System.load(System.getProperty("opencv-320.jar"));
		buffer = bufferedImage;
		bufVeiculo = new Veiculo(buffer);
		
		// controller de processamento para a geração da placa
		try {
			ArrayList<Placa> placas = processarPlaca(buffer);
			for (Placa forPlacas : placas) {
				if (forPlacas.getPlacaSegTo().length() == 7) {
					bufVeiculo.setPlaca(forPlacas);
					salvarImgPlaca(forPlacas);
					boolean registra = true;
					for (ItensVeiculo itens : TelaPrincipal.getListItens())
						if (bufVeiculo.getPlaca().getPlacaSegTo().equals(itens.getPlaca())) {
							// Colocar a verificação de um dia
							itens.setData(bufVeiculo.getData());
							TelaPrincipal.getListItens().remove(itens);
							registra = false;
							TelaPrincipal.getListItens().add(0, itens);
							break;
						}
					
					if (registra) {
						try {
							webservice = new ClientePlacasWSController(bufVeiculo.getPlaca().getPlacaSegTo());
							bufVeiculo.setSituacao(webservice.getSituacao());
							bufVeiculo.setDtSituacao(webservice.getData());
						} catch (Exception e) {
							// TODO: handle exception
						}
						ItensVeiculo item = new ItensVeiculo(bufVeiculo.getData(), bufVeiculo.getPlaca().getPlacaSegTo(), bufVeiculo.getSituacao(), bufVeiculo.getDtSituacao());
						TelaPrincipal.getListItens().add(0, item);
					}
				}
			}
			//System.out.println("Concluido!");
			
			/*bufVeiculo.setPlaca(processarPlaca(buffer));
			if (bufVeiculo.getPlaca().getPlacaSegTo().length() == 7) {
				webservice = new ClientePlacasWSController(bufVeiculo.getPlaca().getPlacaSegTo());
				bufVeiculo.setSituacao(webservice.getSituacao());
				bufVeiculo.setDtSituacao(webservice.getData());
			}
			TelaPrincipal.getListItens().add(new ItensVeiculo(bufVeiculo.getData(), bufVeiculo.getPlaca().getPlacaSegTo(), bufVeiculo.getSituacao(), bufVeiculo.getDtSituacao()));
			TelaPrincipal.setPlacaFoto(bufVeiculo.getPlaca());*/
		} catch (ProcessamentoControllerException e) {
			// TODO Auto-generated catch block
		}
		
		
		// Grava em algum lugar para mostrar na tela
	}
	
	public ArrayList<Placa> processarPlaca(BufferedImage bufferedImage) throws ProcessamentoControllerException {
		ArrayList<Placa> placas = new ArrayList<Placa>();
		FiltroController filtro = new FiltroController();
		try {
			placas = filtro.iniciarProcessamento(bufferedImage);
			return placas;
		} catch (FiltroControllerException e) {
			throw new ProcessamentoControllerException("Sem identificação de placa!");
		}
	}
	
	public void salvarImgPlaca(Placa imgPlaca) {
		Imgcodecs.imwrite("placa.jpg", imgPlaca.getImgPlaca());
		Imgcodecs.imwrite("s1.jpg", imgPlaca.getPlacaSeg().get(0).getImgSeg());
		Imgcodecs.imwrite("s2.jpg", imgPlaca.getPlacaSeg().get(1).getImgSeg());
		Imgcodecs.imwrite("s3.jpg", imgPlaca.getPlacaSeg().get(2).getImgSeg());
		Imgcodecs.imwrite("s4.jpg", imgPlaca.getPlacaSeg().get(3).getImgSeg());
		Imgcodecs.imwrite("s5.jpg", imgPlaca.getPlacaSeg().get(4).getImgSeg());
		Imgcodecs.imwrite("s6.jpg", imgPlaca.getPlacaSeg().get(5).getImgSeg());
		Imgcodecs.imwrite("s7.jpg", imgPlaca.getPlacaSeg().get(6).getImgSeg());
	}
}
