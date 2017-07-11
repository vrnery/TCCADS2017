package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.controller;

import java.util.ArrayList;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception.ProcessamentoHistogramaException;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception.SegmentacaoException;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.model.Filtro;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.model.Placa;
import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.model.Segmentacao;

public class ProcessamentoHistograma {

	private static ArrayList<Placa> procPlaca;
	private static ArrayList<Rect> recHistograma;
	private static Mat matSrc;
	
	public static ArrayList<Placa> processamento(ArrayList<Rect> rect, Mat src) throws ProcessamentoHistogramaException {
		recHistograma = rect;
		matSrc = src;
		procPlaca = new ArrayList<Placa>();
		for (Rect r : recHistograma) {
			Placa histoPlaca = new Placa();
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
			
			// Imagem histogramaVertical
			Imgcodecs.imwrite("Imagem6-histogramaVertical.jpg", matHis);
			
			//System.out.println(matHis.dump());
			ArrayList<Rect> procSegmentacao = Filtro.histogramaHorizontal(matHis); 
			if (procSegmentacao.size() > 6) {
				for (int i = 0; i < procSegmentacao.size(); i++) {
					Rect rSeg = procSegmentacao.get(i);
					Mat ampliarRSeg = new Mat();
					Imgproc.resize(new Mat(matHis, rSeg), ampliarRSeg, new Size(200, 300));
					Segmentacao seg = new Segmentacao(ampliarRSeg, (r.x + rSeg.x), (r.y + dbottom), (r.y + rSeg.height), (r.x + rSeg.width));
					//Segmentacao seg = new Segmentacao(new Mat(matHis, rSeg), (r.x + rSeg.x), (r.y + dbottom), (r.y + rSeg.height), (r.x + rSeg.width));
					/*try {
						ImageIO.write(seg.getImgSegBuffered(), "jpg", new File("seg0"+i+".jpg"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}*/
					//Imgcodecs.imwrite("seg"+i+"0.jpg", seg.getImgSeg());
					try {
						if (histoPlaca.getPlacaSeg().size() < 3)
							seg.reconhecer(true);
						else
							seg.reconhecer(false);
						histoPlaca.setPlacaSegAdd(seg);
						// Imagem segmentação
						Imgcodecs.imwrite("seg"+seg.getCaracter()+".jpg", seg.getImgSeg());
					} catch (SegmentacaoException e) {
						//Vai para a proxima segmentação;
					}
					//System.out.println(histoPlaca.getPlacaSeg().size());
					if (histoPlaca.getPlacaSeg().size() == 7) {
						histoPlaca.setImgPlaca(matHis);
						histoPlaca.setX(r.x);
						histoPlaca.setY(r.y);
						histoPlaca.setHeight(r.height);
						histoPlaca.setWidht(r.width);
						Imgcodecs.imwrite("placa-"+histoPlaca.getPlacaSegTo()+".jpg", histoPlaca.getImgPlaca());
						//histoPlaca.confirmar();
						procPlaca.add(histoPlaca);
						break;
					} else if (((procSegmentacao.size() - i) + histoPlaca.getPlacaSeg().size()) < 7)
						break;
				}
			}
		}
		if (procPlaca.isEmpty())
			throw new ProcessamentoHistogramaException("Placa não encontrada!");
		return procPlaca;
	}
}
