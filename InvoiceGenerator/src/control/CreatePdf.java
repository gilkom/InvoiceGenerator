package control;

import model.*;

import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.JTable;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;




public class CreatePdf {
	private static String FILE;
	private static Font invFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
	            Font.BOLD);
	
	//public CreatePdf(int id, String ordDate, String invDate, Double totalF, JTable items,
	//		int lastId, Map<Integer,Integer> mapId) {
	public CreatePdf(int lastId) {
		String filenameId = Integer.toString(lastId);
		FILE = "Invoice " + filenameId;
		try {
			Document document = new Document();
			PdfWriter.getInstance(document,  new FileOutputStream(FILE));
			document.open();
			addMetaData(document);
			//addContent(document,id, ordDate, invDate, totalF, items,lastId,  mapId);
			addContent(document, lastId);
			document.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static void addMetaData(Document document) {
		document.addTitle("My first PDF");
	}
	//private static void addContent(Document document, int id, String ordDate, String invDate
	//, Double totalF, JTable items,int lastId, Map<Integer,Integer> mapId) throws DocumentException{
	private static void addContent(Document document, int lastId) throws DocumentException {
		
		//----------Invoice's header--------------
		
		//getting data from invoice
		List<Orders> orders_list = Control.selectOrdersLikeId(lastId);
		List<Orders_Details> orders_details_list = Control.selectOrders_DetailsLikeId(lastId);
		List<Product> products = Control.selectProduct();

		
		Paragraph preface = new Paragraph();
		Paragraph title = new Paragraph("Invoice number: " + lastId, invFont);
		Paragraph invoiceDate = new Paragraph("Invoice Date: "+ orders_list.get(0).getInvoiceDate());
		Paragraph orderDate = new Paragraph("Order Date: "+ orders_list.get(0).getOrderDate());
		Paragraph customerDetails = new Paragraph("Customer details:\n"+ 
												Control.selectCustomer(orders_list.get(0).getCustomerId()));
		Paragraph issuerDetails = new Paragraph("Issuer details: \n " +"x"+"\n" + "x");
		
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
		preface.add(issuerDetails);
		preface.add(new Paragraph(" "));

		
		
		
		//Table
		PdfPTable table = new PdfPTable(8);
		float[] columnWidths = {7f,20f,5f,10f,10f,8f,14f,14f};
		table.setWidths(columnWidths);
		PdfPCell cell1 = new PdfPCell(new Paragraph("Item No."));
		PdfPCell cell2 = new PdfPCell(new Paragraph("Product name"));
		PdfPCell cell3 = new PdfPCell(new Paragraph("Qty"));
		PdfPCell cell4 = new PdfPCell(new Paragraph("Net Price"));
		PdfPCell cell5 = new PdfPCell(new Paragraph("Total Net"));
		PdfPCell cell6 = new PdfPCell(new Paragraph("Tax rate%"));
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
		
		int leng = orders_details_list.size();
		for(int i = 0; i < leng;i++) {

			
			String itemNo = Integer.toString(orders_details_list.get(i).getItemNumber());
			String itemName = products.get(orders_details_list.get(i).getProductId()).getProductName();
			String itemQuantity = Integer.toString(
					orders_details_list.get(i).getItemQuantity());
			String purchasePrice = Double.toString(
					orders_details_list.get(i).getPurchasePrice());
			String totalNet = Double.toString(
					Math.floor(Double.valueOf(itemQuantity) * Double.valueOf(purchasePrice)*100.00)/100d);
			String taxRate = Integer.toString(orders_details_list.get(i).getItemTax());

			String taxAmount = Double.toString(
					Math.floor((Double.valueOf(taxRate)* (Double.valueOf(purchasePrice) *
					Double.valueOf(itemQuantity))/100d)*100d)/100d);
			String totalGross = Double.toString(
					Math.floor((Double.valueOf(totalNet) + Double.valueOf(taxAmount))*100)/100d); 
			
			table.addCell(new PdfPCell(new Paragraph(itemNo)));
			table.addCell(new PdfPCell(new Paragraph(itemName)));
			table.addCell(new PdfPCell(new Paragraph(itemQuantity)));
			table.addCell(new PdfPCell(new Paragraph(purchasePrice)));
			table.addCell(new PdfPCell(new Paragraph(totalNet)));
			table.addCell(new PdfPCell(new Paragraph(taxRate)));	
			table.addCell(new PdfPCell(new Paragraph(taxAmount)));
			table.addCell(new PdfPCell(new Paragraph(totalGross)));
			

		}
		
		//Invoices footer
		Paragraph footer = new Paragraph();
		Paragraph totalField = new Paragraph(
				"Total:" + Math.floor(orders_list.get(0).getOrderTotal())*100d/100d);
		Paragraph SignaDot = new Paragraph("..............................             "
				+ "                         .....................................");
		Paragraph Signa = new Paragraph("Issuer signature                     "
				+ "                   Customer's signature");
		
		totalField.setAlignment(2);
		SignaDot.setAlignment(1);
		Signa.setAlignment(1);
		
		footer.add(new Paragraph(" "));
		footer.add(totalField);
		footer.add(new Paragraph(" "));
		footer.add(new Paragraph(" "));
		footer.add(SignaDot);
		footer.add(Signa);


		document.add(preface);
		document.add(table);
		document.add(footer);
		document.newPage();
	}
}
