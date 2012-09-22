package controllers;

import java.util.List;
import models.Cadeau;
import models.Utilisateur;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.mvc.With;
import serializer.SerialiseurJsonDUtilisateur;

@With(Securite.class)
public class Achats extends Connecte {

	public static void mesAchats() {
		render();
	}
}
