package controllers;

import models.Cadeau;
import models.Utilisateur;
import play.mvc.Controller;
import play.mvc.With;

@With(Securite.class)
public class Cadeaux extends Connecte {

	public static void maListe() {
		render();
	}

	public static void ajoute(String libelle) {
		Utilisateur utilisateur = Connecte.utilisateur();
		Cadeau cadeau = utilisateur.ajouteCadeau(libelle);
		Arrete.siContientDesErreurs(validation);
		utilisateur.save();
		renderJSON(cadeau);
	}

	public static void supprime(long id) {
		Utilisateur utilisateur = Connecte.utilisateur();
		Cadeau cadeau = utilisateur.supprimeCadeau(id);
		utilisateur.save();
		renderJSON(cadeau);
	}

	public static void listeDUnAmi(long id) {
		Utilisateur utilisateur = Connecte.utilisateur();
		Utilisateur amiSelectionne = utilisateur.recupereAmiParId(id);
		retourne403IfNull(amiSelectionne);
		render(amiSelectionne);
	}

	public static void changePosition(long id, int position) {
		Utilisateur utilisateur = Connecte.utilisateur();
		Cadeau cadeau = utilisateur.recupereCadeauParId(id);
		utilisateur.changePositionCadeau(cadeau, position);
		utilisateur.save();
	}

	private static void retourne403IfNull(Object objet) {
		if (objet == null) {
			forbidden();
		}
	}
}
