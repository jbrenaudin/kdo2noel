package controllers;

import play.data.validation.Validation;
import play.data.validation.Error;

public class Arrete {

	public static void siContientDesErreurs(Validation validation) {
		if (validation.hasErrors()) {
    		StringBuffer message = new StringBuffer();
    		for (Error error : validation.errors()) {
				message.append(error.getKey() + " - " + error.message() + "\n");
			}
    		throw new RuntimeException(message.toString());
		}
	}
}
