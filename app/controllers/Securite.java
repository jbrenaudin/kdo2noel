package controllers;

import models.Utilisateur;
import play.data.validation.Required;
import play.libs.Crypto;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http;

public class Securite extends Controller {

	@Before(unless = { "connexion", "authentifie", "deconnexion" })
	static void verifieAcces() throws Throwable {
		if (!session.contains("email")) {
			flash.put("url", "GET".equals(request.method) ? request.url : "/");
			connexion();
		}
	}

	public static void connexion() {
		Http.Cookie seSouvenir = request.cookies.get("seSouvenir");
		if (seSouvenir != null && seSouvenir.value.indexOf("-") > 0) {
			String signature = seSouvenir.value.substring(0, seSouvenir.value.indexOf("-"));
			String email = seSouvenir.value.substring(seSouvenir.value.indexOf("-") + 1);
			if (Crypto.sign(email).equals(signature)) {
				session.put("email", email);
				redirigeVersURLOrigine();
			}
		}
		flash.keep("url");
		render();
	}

	public static void authentifie(@Required String email, String motDePasse, boolean seSouvenir) {
		Boolean authorise = Utilisateur.existe(email, motDePasse);
		if (validation.hasErrors() || !authorise) {
			flash.keep("url");
			flash.error("Oops, email ou mot de passe incorrect.");
			params.flash();
			connexion();
		}
		session.put("email", email);
		if (seSouvenir) {
			response.setCookie("seSouvenir", Crypto.sign(email) + "-" + email, "30d");
		}
		redirigeVersURLOrigine();
	}

	public static void deconnexion() {
		session.clear();
		response.removeCookie("seSouvenir");
		connexion();
	}

	static void redirigeVersURLOrigine() {
		redirect(flash.get("url") == null ? "/" : flash.get("url"));
	}
}
