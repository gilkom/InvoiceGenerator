package control;

import java.io.*;
import java.util.Date;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;


public class CreatePdf {
	private static String FILE = "CreatePdf - result.pdf";
	private static Font invFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
	            Font.BOLD);
	
	public CreatePdf() {
		try {
			Document document = new Document();
			PdfWriter.getInstance(document,  new FileOutputStream(FILE));
			document.open();
			addMetaData(document);
			addContent(document);
			document.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static void addMetaData(Document document) {
		document.addTitle("My first PDF");
	}
	private static void addContent(Document document) throws DocumentException{
		Paragraph preface = new Paragraph();
		preface.add(new Paragraph("                                "
				+ "                   Invoice number " + "x", invFont));
		preface.add(new Paragraph("                                   "
				+ "                                                    "
				+ "                                         invoice date:"));
		preface.add(new Paragraph("                                   "
				+ "                                                    "
				+ "                                         order date:"));
		preface.add(new Paragraph(" "));		
		preface.add(new Paragraph("                                   "
				+ "                                                    "
				+ "                                         Customer details:"));

		preface.add(new Paragraph("Issuer details:"));

		document.add(preface);
		document.newPage();
	}
}
