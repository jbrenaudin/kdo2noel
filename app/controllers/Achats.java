package controllers;

import models.Achat;
import play.data.validation.Valid;
import play.mvc.With;

@With(Securite.class)
public class Achats extends Connecte {

	public static void mesAchats() {
		render();
	}

	public static void nouveau() {
		render();
	}

	public static void ajoute(@Valid Achat achat) {
		if (hasErrors()) {
			flashParamsAndKeepValidation();
			nouveau();
		}
		utilisateurConecte().ajouteAchat(achat).save();
		mesAchats();
	}

	public static void supprime(long id) {
		utilisateurConecte().supprimeAchat(id).save();
		mesAchats();
	}

	public static void affiche(long id) {
		Achat achat = utilisateurConecte().recupereAchat(id);
		render(achat);
	}

	public static void modifie(long id, @Valid Achat nouveau) {
		if (hasErrors()) {
			params.flash();
			render("@affiche", nouveau);
		}
		Achat achat = utilisateurConecte().recupereAchat(id);
		achat.cadeau = nouveau.cadeau;
		achat.pour = nouveau.pour;
		achat.save();
		mesAchats();
	}
}
