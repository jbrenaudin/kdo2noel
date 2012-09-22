package controllers;

import play.mvc.With;

@With(Securite.class)
public class Achats extends Connecte {

	public static void mesAchats() {
		render();
	}
}
