package com.validateRequest;

public class ValidateR {

	public boolean validate(String encrypted, int id) {
		boolean valid=false;
		int len=encrypted.length();
		if(encrypted.substring(0,3).equals("121") && encrypted.substring(len-4,len).equals("1234")) {
			if(Integer.parseInt(encrypted.substring(3, len-4))==id){
				valid=true;
			}
			else {
				System.err.println("unauthorized connection between client and webservices");
			}
		}
		return valid;
	}
}
