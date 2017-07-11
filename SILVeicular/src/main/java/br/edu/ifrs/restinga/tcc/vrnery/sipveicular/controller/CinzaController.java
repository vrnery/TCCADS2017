package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.controller;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception.CinzaControllerException;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception.FiltroException;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception.PlacaException;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception.ProcessamentoHistogramaException;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.model.Filtro;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.model.Placa;

public class CinzaController implements Runnable {

	ArrayList<Placa> plaCin, plaSrc;
	Mat matBuffer;
	Mat matCin;
	ArrayList<Rect> recCin;
	
	public CinzaController(Mat matBuffer, ArrayList<Placa> placa) {
		super();
		this.matBuffer = matBuffer;
		this.plaSrc = placa;
		this.plaCin = new ArrayList<Placa>();
		//System.out.println("\"8\": "+this.plaCin.getPlacaSegTo());
	}

	@Override
	public void run() {
		matCin = Filtro.filtroAdaptativo(Filtro.filtroCinza(matBuffer));
		// try teste1
		try {
			recCin = Filtro.filtrarRuido(Filtro.localizarContorno(matCin), matBuffer, "Cinza");
			try {
				plaCin.addAll(ProcessamentoHistograma.processamento(recCin, matCin));
			} catch (ProcessamentoHistogramaException e) {
				throw new FiltroException();
			}
		} catch (FiltroException f) {
			// try teste2
			try {
				matCin = Filtro.filtroOtcu(matCin);
				recCin = Filtro.filtrarRuido(Filtro.localizarContorno(matCin), matBuffer, "Cinza");
				try {
					plaCin.addAll(ProcessamentoHistograma.processamento(recCin, matCin));
				} catch (ProcessamentoHistogramaException g) {
					throw new FiltroException();
				}
			} catch (FiltroException h) {
				CinzaControllerException err = new CinzaControllerException("Processamento Cinza não reconheceu placa!");
				Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), err);
			}
		} finally {
			if (!plaCin.isEmpty()) {
				try {
					//System.out.println("Cin: "+plaCin.size());
					for (Placa pcc : plaCin)
						try {
							//if (!pcc.getPlacaSeg().isEmpty())
								//System.out.println("Cin: "+pcc.getPlacaSegTo());
							Filtro.filtroHarris(new Mat(matBuffer, new Rect(pcc.getX(), pcc.getY(), pcc.getWidht(), pcc.getHeight())));
							pcc.confirmar(matBuffer);
						} catch (PlacaException e) {
							// TODO Auto-generated catch block
							System.out.println(e.getMessage());
						}
				} catch (Exception e) {
					// TODO: handle exception
					//System.out.println(plaCin.size());
				}
				this.plaSrc.addAll(plaCin);
			}
		}
	}

}
