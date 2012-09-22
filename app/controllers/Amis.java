package controllers;

import java.util.List;

import models.Cadeau;
import models.Utilisateur;
import play.mvc.With;
import serializer.SerialiseurJsonDUtilisateur;

@With(Securite.class)
public class Amis extends Connecte {

	public static void mesAmis() {
		render();
	}

	public static void possible(String surnom) {
		Utilisateur moi = Connecte.utilisateur();
		List<Utilisateur> amisPossibles = moi.chercheAmisPossibles(surnom);
		renderJSON(amisPossibles, new SerialiseurJsonDUtilisateur());
	}

	public static void supprime(long id) {
		Utilisateur moi = Connecte.utilisateur();
		Utilisateur ami = Utilisateur.findById(id);
		moi.supprimeAmi(ami).save();
		ami.supprimeAmi(moi).save();
		renderJSON(ami, new SerialiseurJsonDUtilisateur());
	}

	public static void changePosition(long id, int position) {
		Utilisateur utilisateur = Connecte.utilisateur();
		Utilisateur ami = utilisateur.recupereAmiParId(id);
		utilisateur.changePositionAmi(ami, position);
		utilisateur.save();
	}

	public static void acheteCadeau(long idAmi, long idCadeau) {
		Utilisateur utilisateur = Connecte.utilisateur();
		Utilisateur ami = utilisateur.recupereAmiParId(idAmi);
		Cadeau cadeau = ami.recupereCadeauParId(idCadeau);
		utilisateur.acheteCadeau(ami, cadeau);
		utilisateur.save();
	}
}