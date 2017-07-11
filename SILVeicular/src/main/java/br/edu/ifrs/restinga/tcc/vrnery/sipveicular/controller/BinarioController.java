package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.controller;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.Rect;

import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception.BinarioControllerException;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception.FiltroException;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception.PlacaException;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception.ProcessamentoHistogramaException;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.model.Filtro;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.model.Placa;

public class BinarioController implements Runnable {

	ArrayList<Placa> plaBin, plaSrc;
	Mat matBuffer;
	Mat matBin;
	ArrayList<Rect> recBin;
	
	public BinarioController(Mat matBuffer, ArrayList<Placa> placa) {
		super();
		this.matBuffer = matBuffer;
		this.plaSrc = placa;
		this.plaBin = new ArrayList<Placa>();
	}

	@Override
	public void run() {
		// try teste1
		try {
			matBin = Filtro.filtroErosao(Filtro.filtroBinario(Filtro.filtroCinza(matBuffer), 102));
			recBin = Filtro.filtrarRuido(Filtro.localizarContorno(matBin), matBuffer, "Binario");
			try {
				plaBin.addAll(ProcessamentoHistograma.processamento(recBin, matBin));
			} catch (ProcessamentoHistogramaException e) {
				throw new FiltroException();
			}
		} catch (FiltroException f) {
			// try teste2
			try {
				matBin = Filtro.filtroErosao(Filtro.filtroBinario(Filtro.filtroCinza(matBuffer), 153));
				recBin = Filtro.filtrarRuido(Filtro.localizarContorno(matBin), matBuffer, "Binario");
				try {
					plaBin.addAll(ProcessamentoHistograma.processamento(recBin, matBin));
				} catch (ProcessamentoHistogramaException g) {
					throw new FiltroException();
				}
			} catch (FiltroException h) {
				// try teste3
				try {
					matBin = Filtro.filtroErosao(Filtro.filtroBinario(Filtro.filtroCinza(matBuffer), 204));
					recBin = Filtro.filtrarRuido(Filtro.localizarContorno(matBin), matBuffer, "Binario");
					try {
						plaBin.addAll(ProcessamentoHistograma.processamento(recBin, matBin));
					} catch (ProcessamentoHistogramaException i) {
						throw new FiltroException();
					}
				} catch (FiltroException j) {
					// try teste4
					try {
						matBin = Filtro.filtroErosao(Filtro.filtroBinario(Filtro.filtroCinza(matBuffer), 52));
						recBin = Filtro.filtrarRuido(Filtro.localizarContorno(matBin), matBuffer, "Binario");
						try {
							plaBin.addAll(ProcessamentoHistograma.processamento(recBin, matBin));
						} catch (ProcessamentoHistogramaException k) {
							throw new FiltroException();
						}
					} catch (FiltroException l) {
						BinarioControllerException err = new BinarioControllerException("Processamento Binario não reconheceu placa!");
						Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), err);
					}
				}
			}
		} finally {
			if (!plaBin.isEmpty()) {
				try {
					//System.out.println("Bin: "+plaBin.size());
					for (Placa pcb : plaBin)
						try {
							//if (!pcb.getPlacaSeg().isEmpty())
								//System.out.println("Bin: "+pcb.getPlacaSegTo());
							Filtro.filtroHarris(new Mat(matBuffer, new Rect(pcb.getX(), pcb.getY(), pcb.getWidht(), pcb.getHeight())));
							pcb.confirmar(matBuffer);
						} catch (PlacaException e) {
							// TODO Auto-generated catch block
							//System.out.println(e.getMessage());
						}
				} catch (Exception e) {
					// TODO: handle exception
					//System.out.println(plaBin.size());
				}
				this.plaSrc.addAll(plaBin);
			}
		}
	}

}
