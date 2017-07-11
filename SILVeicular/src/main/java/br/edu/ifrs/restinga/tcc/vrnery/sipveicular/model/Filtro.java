package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.model;

import java.util.ArrayList;
import java.util.Calendar;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception.FiltroException;

public class Filtro {

	public static ArrayList<Rect> filtrarRuido(ArrayList<MatOfPoint> src, Mat org, String strFil) throws FiltroException {
		Mat matOfPoint = new Mat();
		ArrayList<MatOfPoint> mop = new ArrayList<MatOfPoint>();
		org.copyTo(matOfPoint);
		ArrayList<Rect> dst = new ArrayList<Rect>();
		for (MatOfPoint m : src) {
			Rect r = Imgproc.boundingRect(m);
			// Condição para remover contornos de ruidos
			if (r.height > 50 && r.height < 70 && r.width > 130 && r.width < 180 && r.tl().x != 1) {
				dst.add(r);
				mop.add(m);
			}
		}
		Imgproc.drawContours(matOfPoint, mop, 0, new Scalar(0, 0, 255), 1);
		
		// Imagem contours
		Imgcodecs.imwrite("Imagem4-contours("+strFil+").jpg", matOfPoint);
		
		// ajustar a imagem
		filtroTransformatic(matOfPoint, mop);
		
		Imgcodecs.imwrite(strFil+".jpg", matOfPoint);
		if (dst.isEmpty())
			throw new FiltroException("Matriz sem possivel placa!");
		
		// Testando a segmentação
		criarContornoSegmentado(src, mop, org);
		
		return dst;
	}

	public static Mat filtroAdaptativo(Mat src) {
		Mat dst = new Mat(src.rows(), src.cols(), src.type());
		//System.out.println("adaptiveThreshold");
		Imgproc.adaptiveThreshold(src, dst, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 11,	4);
		
		// Imagem filtro Adaptativo
		Imgcodecs.imwrite("Imagem7-filtroAdaptativo.jpg", dst);
		
		return dst;
	}
	
	public static Mat filtroBinario(Mat src, int thresh) {
		Mat dst = new Mat(src.rows(), src.cols(), CvType.CV_8UC1);
		//System.out.println("Type: "+dst.type());
		//System.out.println("threshold");
		Imgproc.threshold(src, dst, thresh, 255, Imgproc.THRESH_BINARY);
		//System.out.println("Type: "+dst.type());
		
		// Imagem com thresh
		Imgcodecs.imwrite("Imagem2-filtroBinario("+thresh+").jpg", dst);
		
		return dst;
	}

	public static Mat filtroCinza(Mat src) {
		Mat dst = new Mat(src.rows(), src.cols(), CvType.CV_8UC1);
		//System.out.println("cvtColor");
		Imgproc.cvtColor(src, dst, Imgproc.COLOR_RGB2GRAY);
		//System.out.println("Type2: "+dst.type());
		
		// Imagem filtro Cinza
		Imgcodecs.imwrite("Imagem1-filtroCinza.jpg", dst);
		
		return dst;
	}
	
	public static Mat filtroEqualizar(Mat src) {
		Mat dst = new Mat(src.rows(), src.cols(), src.type());
		//System.out.println("equalizeHist");
		Imgproc.equalizeHist(src, dst);
		
		// Imagem equalizeHist
		Imgcodecs.imwrite("Imagem10-equalizeHist.jpg", dst);
		
		return dst;
	}
	
	public static Mat filtroErosao(Mat src) {
		//Mat dst = new Mat(src.rows(), src.cols(), CvType.CV_32SC1);
		Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1, 1));
		//System.out.println("erode");
		Imgproc.erode(src, src, element);
		
		// Imagem erode
		Imgcodecs.imwrite("Imagem3-erode.jpg", src);
		
		Mat dst = new Mat(src.rows(), src.cols(), CvType.CV_8UC1);
		for (int y = 0; y < src.rows(); y++)
			for (int x = 0; x < src.cols(); x++)
				dst.put(y, x, src.get(y, x));
		//System.out.println("Type3: "+dst.type());
		return dst;
	}
	
	public static Mat filtroGaussiano(Mat src) {
		Mat dst = new Mat(src.rows(), src.cols(), src.type());
		Mat gauss = new Mat(src.rows(), src.cols(), src.type());
		//System.out.println("GaussianBlur");
		Imgproc.GaussianBlur(src, gauss, new Size(0, 0), 25);
		//System.out.println("addWeighted");
		Core.addWeighted(src, 2, gauss, -2.25, 1, dst);
		return dst;
	}
	
	public static void filtroHarris(Mat src) {
		Mat dst = new Mat();
		Mat dstColor = new Mat();
		Mat dstCorner = new Mat();
		Mat dstNormalize = new Mat();
		Imgproc.cvtColor(src, dstColor, Imgproc.COLOR_BGR2GRAY);
		Imgproc.cornerHarris(dstColor, dstCorner, 2, 3, 0.1);
		Core.normalize(dstCorner, dstNormalize, 0, 255, Core.NORM_MINMAX, CvType.CV_32FC1, new Mat());
		Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1, 1));
		Imgproc.dilate(dstNormalize, dstNormalize, element);
		Core.convertScaleAbs(dstNormalize, dst);
		for( int j = 0; j < dstNormalize.rows() ; j++){
	        for( int i = 0; i < dstNormalize.cols(); i++){
	        	//System.out.print((int) dstNormalize.get(j,i)[0]+" ");
	            if ((int) dstNormalize.get(j,i)[0] < 227){
	                //Imgproc.circle(dst, new Point(i,j), 3 , new Scalar(0), 2 ,8 , 0);
	            	dst.put(j, i, 0);
	            }
	        }
	        //System.out.println();
	    }
		Imgcodecs.imwrite("Harris-"+Calendar.getInstance().getTimeInMillis()+".jpg", dst);
	}
	
	public static Mat filtroOtcu(Mat src) {
		Mat dst = new Mat(src.rows(), src.cols(), src.type());
		//System.out.println("GaussianBlur");
		Imgproc.GaussianBlur(src, dst, new Size(5, 5), 15);
		
		// Imagem filtro Gaussian
		Imgcodecs.imwrite("Imagem8-filtroGaussian.jpg", dst);
		
		//System.out.println("threshold");
		Imgproc.threshold(dst, dst, 0, 255, Imgproc.THRESH_BINARY + Imgproc.THRESH_OTSU);
		
		// Imagem threshold
		Imgcodecs.imwrite("Imagem9-threshold.jpg", dst);
		
		return dst;
	}
	
	public static void filtroTransformatic(Mat src, ArrayList<MatOfPoint> listPoint) {
		MatOfPoint2f srcTri, dstTri;
		srcTri = new MatOfPoint2f(new Point(0, 0), new Point(src.cols() - 1.f, 0), new Point(0, src.rows() - 1.f));
		
		// Ver os pontos mais proximos do rect
//		int topLeft[] = new int[]{src.width(), src.height()};
//		int topRight[] = new int[]{src.width(), 0};
//		int bottomLeft[] = new int[]{0, src.height()};
//		int bottomRight[] = new int[]{0, 0};
//		int centro[] = new int[]{(int)(src.width()/2), (int)(src.height()/2)};
		
//		Mat ttc = new Mat();
		//src.convertTo(ttc, CvType.CV_8UC1);
//		for (MatOfPoint mtofpt : listPoint) {
//			Rect rmtofpt = Imgproc.boundingRect(mtofpt);
			//System.out.println("X: "+rmtofpt.x+"\tY: "+rmtofpt.y+"\tWidth: "+rmtofpt.width+"\tHeight: "+rmtofpt.height);
//			Mat dump = new Mat(src, rmtofpt);
			//System.out.println(dump.dump());
			
			//ArrayList<MatOfPoint> arrayPoint = new ArrayList<MatOfPoint>();
			//Imgproc.findContours(new Mat(ttc, rmtofpt), arrayPoint, new Mat(), Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
			//for (MatOfPoint fc : arrayPoint) {
				//Rect tx = Imgproc.boundingRect(fc);
				//System.out.println("X: "+tx.x+"\tY: "+tx.y+"\tWidth: "+tx.width+"\tHeight: "+tx.height);
			//}
//		}
		
		//Imgproc.findContours(src, contours, hierarchy, mode, method);
		
		dstTri = new MatOfPoint2f(new Point(src.cols()*0.0f, src.rows()*0.33f), new Point(src.cols()*0.85f, src.rows()*0.25f), new Point(src.cols()*0.15f, src.rows()*0.7f));
		Mat warp_mat = new Mat(2, 3, CvType.CV_32FC1);
		Mat warp_dst = Mat.zeros(src.rows(), src.cols(), src.type());
		warp_mat = Imgproc.getAffineTransform(srcTri, dstTri);
		Imgproc.warpAffine(src, warp_dst, warp_mat, warp_dst.size());
		//Imgcodecs.imwrite("Warp"+Calendar.getInstance().getTimeInMillis()+".jpg", warp_dst);
	}
	
	public static ArrayList<Rect> histogramaHorizontal(Mat src) {
		int[] arrayHorizontal = new int[src.cols()];
		int max = 0;
		for (int i = 0; i < src.cols(); ++i) {
			arrayHorizontal[i] = Core.countNonZero(src.col(i));
			if (arrayHorizontal[i] > max)
				max = arrayHorizontal[i];
		}
		int coluna = 0;
		int colunaIni = 0;
		ArrayList<Rect> segmentoPosicao = new ArrayList<Rect>();
		for (int i = 0; i < src.cols(); i++) {
			if (arrayHorizontal[i] < (max / 1.2)) {
				++coluna;
				if (colunaIni == 0)
					colunaIni = i;
			} else {
				if (coluna > 3)
					segmentoPosicao.add(new Rect((colunaIni - 1), 0, coluna + 2, src.height()));
				coluna = 0;
				colunaIni = 0;
			}
		}
		return segmentoPosicao;
	}
	
	public static int histogramaVertical(Mat src) {
		int[] arrayVertical = new int[src.rows()];
		int totalVertical = 0;
		for (int i = 0; i < src.rows(); ++i) {
			arrayVertical[i] = Core.countNonZero(src.row(i));
			totalVertical += arrayVertical[i];
		}
		int posMedia = ((arrayVertical.length + 1) / 2);
		float porcVertical = (float) ((totalVertical * 9.6) / 10);
		float somaVertical = (float) arrayVertical[posMedia];
		int limite = 0;
		for (int i = 1; i < arrayVertical.length; i++) {
			somaVertical += (float) arrayVertical[posMedia - i];
			somaVertical += (float) arrayVertical[posMedia + i];
			if (somaVertical > porcVertical) {
				limite = i;
				break;
			}
		}
		return limite;
	}
	
	public static ArrayList<MatOfPoint> localizarContorno(Mat src) throws FiltroException {
		ArrayList<MatOfPoint> dst = new ArrayList<MatOfPoint>();
		//System.out.println("cvtColor");
		//Imgproc.cvtColor(matOfPoint, matOfPoint, Imgproc.COLOR_BGR2RGB);
		//System.out.println("findContours");
		Imgproc.findContours(src, dst, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
		if (dst.isEmpty())
			throw new FiltroException("Matriz sem contorno!");
		return dst;
	}
	
	public static void criarContornoSegmentado(ArrayList<MatOfPoint> src, ArrayList<MatOfPoint> src2, Mat org) {
		Mat t = new Mat();
		
		Mat u = new Mat(org.rows(), org.cols(), t.type());
		for(int a = 0; a < org.rows(); a++)
			for(int b = 0; b < org.cols(); b++)
				u.put(a, b, 255);
		
		org.copyTo(t);
		
		ArrayList<MatOfPoint> desenha = new ArrayList<MatOfPoint>();
		for(MatOfPoint m : src2) {
			Rect mr = Imgproc.boundingRect(m);
			for(MatOfPoint n : src) {
				Rect nr = Imgproc.boundingRect(n);
				if ((mr.x < nr.x) && (nr.x < (mr.x + mr.width))
						&& (mr.y <= nr.y) && ((nr.y + nr.height) <= (mr.y + mr.height))
						&& (nr.height > 10)) {
					desenha.add(n);
					Imgproc.rectangle(t, new Point(nr.x, nr.y), new Point(nr.x + nr.width, nr.y + nr.height), new Scalar(255, 255, 0));
				}
			}
			Imgproc.rectangle(t, new Point(mr.x, mr.y), new Point((mr.x + mr.width), (mr.y + mr.height)), new Scalar(100, 100, 0));
		}
		
		//Imgproc.polylines(t, desenha, true, new Scalar(255, 0, 255), 2);
		Imgproc.drawContours(u, desenha, -1, new Scalar(0, 0, 0));
		//Imgproc.drawContours(t, desenha, -1, new Scalar(255, 255, 0));
		
		// Imagem segmentado
		Imgcodecs.imwrite("Imagem5-segmentado.jpg", u);
		
		String testenome = "Teste-"+Calendar.getInstance().getTimeInMillis()+".jpg";
		Imgcodecs.imwrite(testenome, u);
		Imgcodecs.imwrite("Teste"+Calendar.getInstance().getTimeInMillis()+".jpg", t);
		// pintar o interior do contorno
		Mat interior = new Mat();
		u.copyTo(interior);
		for(MatOfPoint mopint : desenha) {
			Rect mi = Imgproc.boundingRect(mopint);
			if (mi.height > 10)
				Imgproc.line(interior, new Point(mi.x, mi.y), new Point(mi.width, mi.height), new Scalar(0, 0, 0));
		}
		Imgcodecs.imwrite("Teste+"+Calendar.getInstance().getTimeInMillis()+".jpg", interior);
		// localizar o contorno do Teste- e filtrar pela altura
		// cortar na altura e largura e salvar para passar no tesseract
	}
}
