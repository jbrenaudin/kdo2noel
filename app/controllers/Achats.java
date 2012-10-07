package controllers;

import models.Achat;
import models.Utilisateur;
import play.cache.Cache;
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
		if (validation.hasErrors()) {
			params.flash();
			validation.keep();
			nouveau();
		}
		Utilisateur moi = Connecte.utilisateur();
		moi.ajouteAchat(achat);
		moi.save();
		mesAchats();
	}
	
	public static void supprime(long id) {
		Utilisateur moi = Connecte.utilisateur();
		moi.supprimeAchat(id);
		moi.save();
		mesAchats();
	}
}
