package controllers;

import models.Utilisateur;
import play.cache.Cache;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.libs.Codec;
import play.libs.Images;

public class Utilisateurs extends Controleur {

	public static void nouveau() {
		String randomID = Codec.UUID();
		render(randomID);
	}

	public static void cree(@Valid Utilisateur utilisateur,
			@Required String motDePasseConfirmation, @Required String code,
			String randomID) {
		validation.equals("motDePasseConfirmation", motDePasseConfirmation, "mot de passe", utilisateur.motDePasse);
		validation.equals(code, Cache.get(randomID)).message("Code incorrect");
		if (validation.hasErrors()) {
			flashParamsAndKeepValidation();
			nouveau();
		}
		utilisateur.save();
		Securite.connexion();
	}

	public static void captcha(String id) {
		Images.Captcha captcha = Images.captcha();
		String code = captcha.getText("#000000");
		Cache.set(id, code, "10mn");
		renderBinary(captcha);
	}
}
