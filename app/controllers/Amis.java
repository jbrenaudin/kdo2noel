package controllers;

import java.util.List;

import models.Utilisateur;
import play.mvc.With;
import serializer.SerialiseurJsonDUtilisateur;

@With(Securite.class)
public class Amis extends Connecte {

	public static void mesAmis() {
		render();
	}

	public static void possiblesAyant(String surnom) {
		List<Utilisateur> amisPossibles = utilisateurConecte().chercheAmisPossiblesAyant(surnom);
		renderJSON(amisPossibles, new SerialiseurJsonDUtilisateur());
	}
	
	public static void ayant(String surnom) {
		List<Utilisateur> amis = utilisateurConecte().chercheAmisAyant(surnom);
		renderJSON(amis, new SerialiseurJsonDUtilisateur());
	}

	public static void supprime(long id) {
		Utilisateur moi = utilisateurConecte();
		Utilisateur ami = moi.recupereAmi(id);
		moi.supprimeAmi(ami).save();
		ami.supprimeAmi(moi).save();
		renderJSON(ami, new SerialiseurJsonDUtilisateur());
	}

	public static void changePosition(long id, int position) {
		Utilisateur moi = utilisateurConecte();
		Utilisateur ami = moi.recupereAmi(id);
		moi.changePositionAmi(ami, position).save();
	}
}