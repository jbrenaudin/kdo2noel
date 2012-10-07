package controllers;

import models.Invitation;
import models.Utilisateur;
import play.mvc.With;

@With(Securite.class)
public class Invitations extends Connecte {

	public static void envoiAUnUtilisateur(Long idDestinataire) {
		Utilisateur destinataire = Utilisateur.findById(idDestinataire);
		Invitation invitation = new Invitation(utilisateurConecte(), destinataire);
		if (!invitation.validateAndCreate()) {
			flashParamsAndKeepValidation();
		}
		Amis.mesAmis();
	}

	public static void annuleUneInvitationEnvoyee(Long idDestinataire) {
		Utilisateur destinataire = Utilisateur.findById(idDestinataire);
		Invitation invitation = Invitation.envoyeeParEtRecuePar(utilisateurConecte(), destinataire);
		if (invitation != null) {
			invitation.delete();
		}
	}

	public static void refuseUneInvitationRecue(Long idEmetteur) {
		Utilisateur emetteur = Utilisateur.findById(idEmetteur);
		Invitation invitation = Invitation.envoyeeParEtRecuePar(emetteur, utilisateurConecte());
		if (invitation != null) {
			invitation.delete();
		}
	}

	public static void accepteUneInvitationRecue(Long idEmetteur) {
		Utilisateur emetteur = Utilisateur.findById(idEmetteur);
		Invitation invitation = Invitation.envoyeeParEtRecuePar(emetteur, utilisateurConecte());
		if (invitation != null) {
			invitation.accepte().delete();
		}
	}
}
