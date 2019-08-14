package control;

public class DataMatcher {
	public boolean custNamePattern(String s) {
		return s.matches(".+");
	}
	
	public String custPattern(String customerName, String customerStreet,
					String customerCity, String customerPostCode, String customerNip) {
		
		String result = "ok";
		
		if (!customerNip.matches("\\d{10}+")) {result = "Wrong nip! 10 digits! Right pattern: XXXXXXXXXX";};
		if (!customerPostCode.matches("\\d\\d-\\d\\d\\d")) {result = "Wrong post code! Right pattern: XX-XXX";};
		if (!customerCity.matches(".{1,30}+")) {result = "Wrong city!";};
		if (!customerStreet.matches(".{1,40}+")) {result = "Wrong street!";};
		if (!customerName.matches(".+")) {result = "Wrong customer name!";};
		
		
		return result;
	}
}
