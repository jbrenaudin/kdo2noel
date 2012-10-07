package controllers;

import play.mvc.Controller;

public abstract class Controleur extends Controller {

	@SuppressWarnings("static-access")
	protected static void flashParamsAndKeepValidation() {
		params.flash();
		validation.keep();
	}
	
	@SuppressWarnings("static-access")
	protected static boolean hasErrors() {
		return validation.hasErrors();
	}
	
	protected static void retourne403IfNull(Object objet) {
		if (objet == null) {
			forbidden();
		}
	}
}