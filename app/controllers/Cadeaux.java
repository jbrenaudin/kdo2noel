package controllers;

import models.Cadeau;
import models.Utilisateur;
import play.mvc.With;

@With(Securite.class)
public class Cadeaux extends Connecte {

	public static void maListe() {
		render();
	}

	public static void ajoute(String libelle) {
		utilisateurConecte().ajouteCadeau(libelle).save();
		maListe();
	}

	public static void supprime(long id) {
		Utilisateur moi = utilisateurConecte();
		Cadeau cadeau = moi.supprimeCadeau(id);
		moi.save();
		renderJSON(cadeau);
	}

	public static void listeDUnAmi(long id) {
		Utilisateur moi = utilisateurConecte();
		Utilisateur ami = moi.recupereAmi(id);
		retourne403IfNull(ami);
		render(ami);
	}

	public static void changePosition(long id, int position) {
		Utilisateur moi = utilisateurConecte();
		Cadeau cadeau = moi.recupereCadeauParId(id);
		moi.changePositionCadeau(cadeau, position);
		moi.save();
	}
}
