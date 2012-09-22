package controllers;

import models.Invitation;
import models.Utilisateur;
import play.mvc.With;

@With(Securite.class)
public class Invitations extends Connecte {

	public static void envoiAUnUtilisateur(Long idDestinataire) {
		Utilisateur emetteur = Connecte.utilisateur();
		Utilisateur destinataire = Utilisateur.findById(idDestinataire);
		Invitation invitation = new Invitation(emetteur, destinataire);
		if (!invitation.validateAndCreate()) {
			params.flash();
			validation.keep();
		}
		Amis.mesAmis();
	}

	public static void annuleUneInvitationEnvoyee(Long idDestinataire) {
		Utilisateur emetteur = Connecte.utilisateur();
		Utilisateur destinataire = Utilisateur.findById(idDestinataire);
		Invitation invitation = Invitation.envoyeeParEtRecuePar(emetteur,
				destinataire);
		if (invitation != null) {
			invitation.delete();
		}
		Amis.mesAmis();
	}

	public static void refuseUneInvitationRecue(Long idEmetteur) {
		Utilisateur emetteur = Utilisateur.findById(idEmetteur);
		Utilisateur destinataire = Connecte.utilisateur();
		Invitation invitation = Invitation.envoyeeParEtRecuePar(emetteur,
				destinataire);
		if (invitation != null) {
			invitation.delete();
		}
		Amis.mesAmis();
	}

	public static void accepteUneInvitationRecue(Long idEmetteur) {
		Utilisateur emetteur = Utilisateur.findById(idEmetteur);
		Utilisateur destinataire = Connecte.utilisateur();
		Invitation invitation = Invitation.envoyeeParEtRecuePar(emetteur,
				destinataire);
		if (invitation != null) {
			invitation.accepte();
			invitation.save();
			invitation.delete();
		}
		Amis.mesAmis();
	}
}
