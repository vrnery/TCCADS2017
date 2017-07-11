package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception.PlacaException;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Placa {

	private Mat imgPlaca;
	private int x;
	private int y;
	private int height;
	private int width;
	private List<Segmentacao> placaSeg = new ArrayList<Segmentacao>();
	private Lock lock = new ReentrantLock();
	
	public Mat getImgPlaca() {
		return imgPlaca;
	}
	public void setImgPlaca(Mat imgPlaca) {
		//System.out.println(imgPlaca.dump());
		this.imgPlaca = imgPlaca;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidht() {
		return width;
	}
	public void setWidht(int widht) {
		this.width = widht;
	}
	public List<Segmentacao> getPlacaSeg() {
		return placaSeg;
	}
	public void setPlacaSeg(List<Segmentacao> placaSeg) {
		this.placaSeg = placaSeg;
	}
	
	/*public byte[] getImgPlacaByte() {
		int cols = imgPlaca.cols();
		int rows = imgPlaca.rows();
		int elemSize = (int) imgPlaca.elemSize();
		byte[] inData = new byte[cols * rows * elemSize];
		//int type;
		imgPlaca.get(0, 0, inData);
//		System.out.println("ConverteMatBuffered::channels::" + imageMat.channels());
		switch (imgPlaca.channels()) {
		case 1:
			//type = BufferedImage.TYPE_BYTE_GRAY;
			break;
		case 3:
			//type = BufferedImage.TYPE_3BYTE_BGR;
			byte b;
			for (int i = 0; i < inData.length; i = i + 3) {
				b = inData[i];
				inData[i] = inData[i + 2];
				inData[i + 2] = b;
			}
			break;
		default:
			return null;
		}
		//BufferedImage buff = new BufferedImage(cols, rows, type);
		//buff.getRaster().setDataElements(0, 0, cols, rows, inData);
		//return buff;
		return inData;
	}*/
	
	public String getPlacaSegTo() {
		String retorno = "";
		for (Segmentacao segmentacao : placaSeg) {
			retorno += segmentacao.getCaracter();
		}
		return retorno;
	}
	
	public void setPlacaSegAdd(Segmentacao placaSeg) {
		this.placaSeg.add(placaSeg);
	}
	
	public void setPlacaThread(Placa pl) {
		//System.out.println("\"13\": "+this.getPlacaSegTo());
		//System.out.println("\"14\": "+pl.getPlacaSegTo());
		try {
			//if (lock.tryLock(10, TimeUnit.SECONDS)) {
			if (lock.tryLock()) {
				this.imgPlaca = pl.imgPlaca;
				this.x = pl.x;
				this.y = pl.y;
				this.height = pl.height;
				this.width = pl.width;
				this.placaSeg = pl.placaSeg;
				//System.out.println("\"15\": "+this.getPlacaSegTo());
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			//lock.unlock();
		}
	}
	
	public void confirmar(Mat matbuffer) throws PlacaException {
		Mat reserva = new Mat();
		reserva = this.imgPlaca;
		this.imgPlaca = new Mat(matbuffer, new Rect(this.x, this.y, this.width, this.height));
		Mat ampliar = new Mat();
		Imgproc.resize(this.imgPlaca, ampliar, new Size(140, 45));
		this.imgPlaca = new Mat();
		this.imgPlaca = ampliar;
		//System.out.println(this.imgPlaca.dump());
		ITesseract tesseract = new Tesseract();
		//tesseract.setLanguage("por");
		tesseract.setTessVariable("tessedit_char_whitelist", " ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
		try {
			String txt = tesseract.doOCR(this.getImgPlacaBuffered());
			txt = txt.trim();
			//System.out.println(txt);
			//List<java.awt.Rectangle> rets = tesseract.getSegmentedRegions(getImgPlacaBuffered(), 3);
			//for (java.awt.Rectangle pintar : rets) {
				//System.out.println("Retangulo - x="+pintar.getX()+"\ty="+pintar.getY()+"\twidht="+pintar.getWidth()+"\theight="+pintar.getHeight());
				//Imgproc.rectangle(this.imgPlaca, new Point(this.x, this.y), new Point((pintar.getX() + pintar.getWidth()), (pintar.getY() + pintar.getHeight())), new Scalar(0, 255, 0), 2);
			//}
			Imgcodecs.imwrite("Placas-"+Calendar.getInstance().getTimeInMillis()+"-"+txt+".jpg", this.imgPlaca);
		} catch (TesseractException e) {
			throw new PlacaException("Falha no OCR!");
		} finally {
			this.imgPlaca = new Mat();
			this.imgPlaca = reserva;
		}
	}
	
	public BufferedImage getImgPlacaBuffered() {
		int cols = this.imgPlaca.cols();
		int rows = this.imgPlaca.rows();
		int elemSize = (int) this.imgPlaca.elemSize();
		byte[] inData = new byte[cols * rows * elemSize];
		int type;
		this.imgPlaca.get(0, 0, inData);
		switch (this.imgPlaca.channels()) {
		case 1:
			type = BufferedImage.TYPE_BYTE_GRAY;
			break;
		case 3:
			type = BufferedImage.TYPE_3BYTE_BGR;
			byte b;
			for (int i = 0; i < inData.length; i = i + 3) {
				b = inData[i];
				inData[i] = inData[i + 2];
				inData[i + 2] = b;
			}
			break;
		default:
			return null;
		}
		BufferedImage buff = new BufferedImage(cols, rows, type);
		buff.getRaster().setDataElements(0, 0, cols, rows, inData);
		return buff;
	}
}
