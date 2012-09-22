package controllers;

import models.Utilisateur;
import play.mvc.Before;
import play.mvc.Controller;

public abstract class Connecte extends Controller {

	@Before
	static void ajouteUtilisateur() {
		Utilisateur utilisateurConnecte = utilisateur();
		if (utilisateurConnecte != null) {
			renderArgs.put("utilisateur", utilisateurConnecte);
		}
	}

	public static Utilisateur utilisateur() {
		Utilisateur utilisateur = getUtilisateurInRender();
		if (utilisateur == null) {
			utilisateur = getUtilisateurInEntrepot(session.get("email"));
		}
		return utilisateur;
	}

	private static Utilisateur getUtilisateurInEntrepot(String email) {
		if (email == null) {
			return null;
		}
		return Utilisateur.recupereParEmail(email);
	}

	private static Utilisateur getUtilisateurInRender() {
		if (renderArgs.get("utilisateur") == null) {
			return null;
		}
		return renderArgs.get("utilisateur", Utilisateur.class);
	}
}