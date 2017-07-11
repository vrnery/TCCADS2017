package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.controller;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception.EqualizadoControllerException;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception.FiltroException;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception.PlacaException;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception.ProcessamentoHistogramaException;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.model.Filtro;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.model.Placa;

public class EqualizadoController implements Runnable {

	ArrayList<Placa> plaEqu, plaSrc;
	Mat matBuffer;
	Mat matEqu;
	ArrayList<Rect> recEqu;
	
	public EqualizadoController(Mat matBuffer, ArrayList<Placa> placa) {
		super();
		this.matBuffer = matBuffer;
		this.plaSrc = placa;
		this.plaEqu = new ArrayList<Placa>();
	}

	@Override
	public void run() {
		matEqu = Filtro.filtroAdaptativo(Filtro.filtroEqualizar(Filtro.filtroCinza(matBuffer)));
		// try teste1
		try {
			recEqu = Filtro.filtrarRuido(Filtro.localizarContorno(matEqu), matBuffer, "Equalizado");
			try {
				plaEqu.addAll(ProcessamentoHistograma.processamento(recEqu, matEqu));
			} catch (ProcessamentoHistogramaException e) {
				throw new FiltroException();
			}
		}catch (FiltroException f) {
			// try teste2
			try {
				matEqu = Filtro.filtroOtcu(matEqu);
				recEqu = Filtro.filtrarRuido(Filtro.localizarContorno(matEqu), matBuffer, "Equalizado");
				try {
					plaEqu.addAll(ProcessamentoHistograma.processamento(recEqu, matEqu));
				} catch (ProcessamentoHistogramaException g) {
					throw new FiltroException();
				}
			} catch (FiltroException h) {
				EqualizadoControllerException err = new EqualizadoControllerException("Processamento Equalizado não reconheceu placa!");
				Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), err);
			}
		} finally {
			if (!plaEqu.isEmpty()) {
				try {
					//System.out.println("Equ: "+plaEqu.size());
					for (Placa pce : plaEqu)
						try {
							//if (!pce.getPlacaSeg().isEmpty())
								//System.out.println("Equ: "+pce.getPlacaSegTo());
							Filtro.filtroHarris(new Mat(matBuffer, new Rect(pce.getX(), pce.getY(), pce.getWidht(), pce.getHeight())));
							//Filtro.filtroTransformatic(new Mat(matBuffer, new Rect(pce.getX(), pce.getY(), pce.getWidht(), pce.getHeight())));
							pce.confirmar(matBuffer);
						} catch (PlacaException e) {
							// TODO Auto-generated catch block
							//System.out.println(e.getMessage());
						}
				} catch (Exception e) {
					// TODO: handle exception
					//System.out.println(plaEqu.size());
				}
				this.plaSrc.addAll(plaEqu);
			}
		}
	}

}
