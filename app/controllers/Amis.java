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
		Utilisateur moi = Connecte.utilisateur();
		Utilisateur ami = moi.recupereAmi(id);
		moi.changePositionAmi(ami, position);
		moi.save();
	}
}