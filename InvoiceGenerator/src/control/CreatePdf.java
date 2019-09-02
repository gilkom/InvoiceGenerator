package control;

import java.io.*;
import java.util.Date;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;




public class CreatePdf {
	private static String FILE = "CreatePdf - result.pdf";
	private static Font invFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
	            Font.BOLD);
	
	public CreatePdf(int id, String ordDate, String invDate, Double totalF) {
		try {
			Document document = new Document();
			PdfWriter.getInstance(document,  new FileOutputStream(FILE));
			document.open();
			addMetaData(document);
			addContent(document,id, ordDate, invDate, totalF);
			document.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static void addMetaData(Document document) {
		document.addTitle("My first PDF");
	}
	private static void addContent(Document document, int id, String ordDate, String invDate
			, Double totalF) throws DocumentException{
		//Invoice's header
		Paragraph preface = new Paragraph();
		Paragraph title = new Paragraph("Invoice number: " + id, invFont);
		Paragraph invoiceDate = new Paragraph("Invoice Date: "+ invDate);
		Paragraph orderDate = new Paragraph("Order Date: "+ ordDate);
		Paragraph customerDetails = new Paragraph("Customer details: \n" + 
				Control.selectCustomer(5));
		
		orderDate.setAlignment(2);
		invoiceDate.setAlignment(2);
		title.setAlignment(1);
		customerDetails.setAlignment(2);
		
		preface.add(invoiceDate);
		preface.add(orderDate);
		preface.add(new Paragraph(" "));
		preface.add(title);
		preface.add(new Paragraph(" "));	
		preface.add(customerDetails);

		preface.add(new Paragraph("Issuer details:"));
		preface.add(new Paragraph(" "));

		
		
		
		//Table
		PdfPTable table = new PdfPTable(8);
		float[] columnWidths = {1f,2f,1f,1f,1f,1f,1f,1f};
		table.setWidths(columnWidths);
		PdfPCell cell1 = new PdfPCell(new Paragraph("Item No."));
		PdfPCell cell2 = new PdfPCell(new Paragraph("Product name"));
		PdfPCell cell3 = new PdfPCell(new Paragraph("Quantity"));
		PdfPCell cell4 = new PdfPCell(new Paragraph("Net Price"));
		PdfPCell cell5 = new PdfPCell(new Paragraph("Total Net"));
		PdfPCell cell6 = new PdfPCell(new Paragraph("Tax rate"));
		PdfPCell cell7 = new PdfPCell(new Paragraph("Tax amount"));
		PdfPCell cell8 = new PdfPCell(new Paragraph("Total Gross"));
		
		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);
		table.addCell(cell4);
		table.addCell(cell5);
		table.addCell(cell6);
		table.addCell(cell7);
		table.addCell(cell8);
		
		//Invoices footer
		Paragraph footer = new Paragraph();
		footer.add(new Paragraph(" "));
		footer.add(new Paragraph("                                   "
				+ "                                                    "
				+ "                                         Total:"));
		footer.add(new Paragraph(" "));
		footer.add(new Paragraph(" "));
		footer.add(new Paragraph("............................               "
				+ "                                  "
				+ "                                 .................................."));
		footer.add(new Paragraph("Issuer signature                                    "
				+ "                                                Customer's signature"));
		
		
		
		document.add(preface);
		document.add(table);
		document.add(footer);
		document.newPage();
	}
}
