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
	}

	public static void affiche(long id) {
		Achat achat = utilisateurConecte().recupereAchat(id);
		render(achat);
	}

	public static void modifie(long id, @Valid Achat achat) {
		if (hasErrors()) {
			params.flash();
			render("@affiche", achat);
		}
		Achat achatAModifier = utilisateurConecte().recupereAchat(id);
		achatAModifier.cadeau = achat.cadeau;
		achatAModifier.pour = achat.pour;
		achatAModifier.save();
		mesAchats();
	}
}
