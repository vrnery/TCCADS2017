package br.edu.ifrs.restinga.tcc.vrnery.sipveicular.model;

import java.awt.image.BufferedImage;

import org.opencv.core.Mat;

import br.edu.ifrs.restinga.tcc.vrnery.sipveicular.exception.SegmentacaoException;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Segmentacao {

	private Mat imgSeg;
	private int x;
	private int y;
	private int height;
	private int widht;
	private String caracter = "";
	
	public Mat getImgSeg() {
		return imgSeg;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getHeight() {
		return height;
	}
	public int getWidht() {
		return widht;
	}
	public String getCaracter() {
		return caracter;
	}
	
	public Segmentacao() {}
	
	public Segmentacao(Mat imgSeg, int x, int y, int height, int widht) {
		//System.out.println(imgSeg.dump());
		this.imgSeg = imgSeg;
		this.x = x;
		this.y = y;
		this.height = height;
		this.widht = widht;
	}
	
	public BufferedImage getImgSegBuffered() {
		return converter(this.imgSeg);
	}
	
	public void reconhecer(boolean alpha) throws SegmentacaoException {
		ITesseract tesseract = new Tesseract();
		//tesseract.setLanguage("eng");
		if (alpha)
			tesseract.setTessVariable("tessedit_char_whitelist", " ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		else
			tesseract.setTessVariable("tessedit_char_whitelist", " 0123456789");
		try {
			String c = (tesseract.doOCR(this.getImgSegBuffered())).trim();
			//System.out.println(this.caracter+"\tLenght: "+this.caracter.length());
			if (c.length() != 1) {
				//System.out.println("Length != 1;");
				throw new SegmentacaoException("Segmentação não identificada!");
			}
			this.caracter = c;
		} catch (TesseractException e) {
			throw new SegmentacaoException("Falha no OCR!");
		}
	}
	
	public BufferedImage converter(Mat src) {
		int cols = src.cols();
		int rows = src.rows();
		int elemSize = (int) src.elemSize();
		byte[] inData = new byte[cols * rows * elemSize];
		int type;
		src.get(0, 0, inData);
//		System.out.println("ConverteMatBuffered::channels::" + imageMat.channels());
		switch (src.channels()) {
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
