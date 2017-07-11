package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.controller;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception.FiltroControllerException;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.model.Placa;

public class FiltroController {

	private ArrayList<Placa> placaEncontrada;

	public ArrayList<Placa> iniciarProcessamento(BufferedImage buffer) throws FiltroControllerException {
		placaEncontrada = new ArrayList<Placa>();
		//System.out.println("\"7\": "+placaEncontrada.getPlacaSegTo());
		byte[] data = ((DataBufferByte) buffer.getRaster().getDataBuffer()).getData();
		Mat matBuffer = new Mat(buffer.getHeight(), buffer.getWidth(), CvType.CV_8UC3);
		matBuffer.put(0, 0, data);
		
		// Imagem com cv_8uc3
		Imgcodecs.imwrite("Imagem-original.jpg", matBuffer);
		
		// Start thread
		BinarioController binControl = new BinarioController(matBuffer, placaEncontrada);
		Thread thBinario = new Thread(binControl);
		CinzaController cinControl = new CinzaController(matBuffer, placaEncontrada);
		Thread thCinza = new Thread(cinControl);
		EqualizadoController equControl = new EqualizadoController(matBuffer, placaEncontrada);
		Thread thEqualizado = new Thread(equControl);
		thBinario.start();
		thCinza.start();
		thEqualizado.start();
		
		/*while (this.placaEncontrada == null) {
			//System.out.println("Loop");
			//if (thBinario.isInterrupted() && thCinza.isInterrupted() && thEqualizado.isInterrupted())
			if (thBinario.isAlive())
				break;
		}*/
		// Stop Threads
		try {
			thBinario.join();
			thCinza.join();
			thEqualizado.join(0);
		} catch (InterruptedException e) {
			// TODO: handle exception
		}
		if (this.placaEncontrada == null)
			throw new FiltroControllerException("Placa não identificada!");
		return this.placaEncontrada;
	}
	
	/*public void processamentoBinario(Mat matBuffer) {
		Placa plaBin = null;
		Mat matBin;
		ArrayList<Rect> recBin;
		
		// try teste1
		try {
			matBin = Filtro.filtroErosao(Filtro.filtroBinario(matBuffer, 200));
			try {
				recBin = Filtro.filtrarRuido(Filtro.localizarContorno(matBin));
				plaBin = processamentoHistograma(recBin, matBin);
			} catch (FiltroException e) {
				throw new FiltroControllerException("Filtro 1 não encontrou localização para placa");
			}
		} catch (FiltroControllerException e) {
			// try teste2
			try {
				matBin = Filtro.filtroErosao(Filtro.filtroBinario(matBuffer, 150));
				try {
					recBin = Filtro.filtrarRuido(Filtro.localizarContorno(matBin));
					plaBin = processamentoHistograma(recBin, matBin);
				} catch (FiltroException f) {
					throw new FiltroControllerException("Filtro 2 não encontrou localização para placa");
				}
			} catch (FiltroControllerException f) {
				// try teste3
				try {
					matBin = Filtro.filtroErosao(Filtro.filtroBinario(matBuffer, 100));
					try {
						recBin = Filtro.filtrarRuido(Filtro.localizarContorno(matBin));
						plaBin = processamentoHistograma(recBin, matBin);
					} catch (FiltroException g) {
						throw new FiltroControllerException("Filtro 3 não encontrou localização para placa");
					}
				} catch (FiltroControllerException g) {
					// try teste4
					try {
						matBin = Filtro.filtroErosao(Filtro.filtroBinario(matBuffer, 50));
						try {
							recBin = Filtro.filtrarRuido(Filtro.localizarContorno(matBin));
							plaBin = processamentoHistograma(recBin, matBin);
						} catch (FiltroException h) {
							throw new FiltroControllerException("Filtro 4 não encontrou localização para placa");
						}
					} catch (Exception h) {
						// Filtro Binario não encontrou a placa
					}
				}
			}
		}
		if (plaBin != null)
			this.placaEncontrada = plaBin;
	}*/
	
	/*public void processamentoCinza(Mat matBuffer) {
		Placa plaCin = null;
		Mat matCin = Filtro.filtroAdaptativo(Filtro.filtroCinza(matBuffer));
		ArrayList<Rect> recCin;
		
		// try teste1
		try {
			try {
				recCin = Filtro.filtrarRuido(Filtro.localizarContorno(matCin));
				plaCin = processamentoHistograma(recCin, matCin);
			} catch (FiltroException e) {
				throw new FiltroControllerException("Filtro 1 não encontrou localização para placa");
			}
		} catch (FiltroControllerException e) {
			// try teste2
			try {
				matCin = Filtro.filtroOtcu(matCin);
				try {
					recCin = Filtro.filtrarRuido(Filtro.localizarContorno(matCin));
					plaCin = processamentoHistograma(recCin, matCin);
				} catch (FiltroException f) {
					throw new FiltroControllerException("Filtro 2 não encontrou localização para placa");
				}
			} catch (FiltroControllerException f) {
				// Filtro Cinza não encontrou a placa
			}
		}
		
		if (plaCin != null)
			this.placaEncontrada = plaCin;
	}*/
	
	/*public void processamentoEqualizado(Mat matBuffer) {
		Placa plaEqu = null;
		Mat matEqu = Filtro.filtroAdaptativo(Filtro.filtroEqualizar(Filtro.filtroCinza(matBuffer)));
		ArrayList<Rect> recEqu;
		
		// try teste1
		try {
			try {
				recEqu = Filtro.filtrarRuido(Filtro.localizarContorno(matEqu));
				plaEqu = processamentoHistograma(recEqu, matEqu);
			} catch (FiltroException e) {
				throw new FiltroControllerException("Filtro 1 não encontrou localização para placa");
			}
		}catch (FiltroControllerException e) {
			// try teste2
			try {
				matEqu = Filtro.filtroOtcu(matEqu);
				try {
					recEqu = Filtro.filtrarRuido(Filtro.localizarContorno(matEqu));
					plaEqu = processamentoHistograma(recEqu, matEqu);
				} catch (FiltroException f) {
					throw new FiltroControllerException("Filtro 2 não encontrou localização para placa");
				}
			} catch (FiltroControllerException f) {
				// Filtro Equalizado não encontrou a placa
			}
		}
		if (plaEqu != null)
			this.placaEncontrada = plaEqu;
	}*/
	
	/*public Placa processamentoHistograma(ArrayList<Rect> recHistograma, Mat matSrc) throws FiltroControllerException {
		Placa procPlaca = new Placa();
		for (Rect r : recHistograma) {
			Mat matHis = new Mat(r.height, r.width, matSrc.type());
			for (int i = r.x; i < (r.x + r.width); i++) {
				for (int j = r.y; j < (r.y + r.height); j++) {
					matHis.put((j - r.y), (i - r.x), matSrc.get(j, i));
				}
			}
			int ajuste = Filtro.histogramaVertical(matHis);
			int dtop = ((int) ((ajuste) / 1.25) * (-1));
			int dbottom = ((int) ((ajuste) / 4) * (-1));
			matHis = matHis.adjustROI(dtop, dbottom, 0, 0);
			ArrayList<Rect> procSegmentacao = Filtro.histogramaHorizontal(matHis); 
			if (procSegmentacao.size() > 6) {
				for (int i = 0; i < procSegmentacao.size(); i++) {
					Rect rSeg = procSegmentacao.get(i);
					Segmentacao seg = new Segmentacao(new Mat(matHis, rSeg), (r.x + rSeg.x), (r.y + dbottom), (r.y + rSeg.height), (r.x + rSeg.width));
					try {
						if (i < 3)
							seg.reconhecer(true);
						else
							seg.reconhecer(false);
						procPlaca.setPlacaSegAdd(seg);
					} catch (SegmentacaoException e) {
						if ((procPlaca.getPlacaSeg().size() + (procSegmentacao.size() - i)) < 5)
							throw new FiltroControllerException("Placa não encontrada!");
					}
				}
			}
			procPlaca.setImgPlaca(matHis);
			procPlaca.setX(r.x);
			procPlaca.setY(r.y);
			procPlaca.setHeight(r.height);
			procPlaca.setWidht(r.width);
		}
		return procPlaca;
	}*/
}
