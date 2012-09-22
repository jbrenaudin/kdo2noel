package serializer;

import java.lang.reflect.Type;
import models.Invitation;
import models.Utilisateur;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.sun.xml.internal.ws.api.pipe.NextAction;

public class SerialiseurJsonDInvitation implements JsonSerializer<Invitation> {
	
	private final static SerialiseurJsonDUtilisateur serialiseurDUtilisateur = new SerialiseurJsonDUtilisateur();
	
	@Override
	public JsonElement serialize(Invitation invitation, Type type, JsonSerializationContext contexte) {
		JsonObject obj = new JsonObject();
        obj.add("emetteur", serialiseurDUtilisateur.serialize(invitation.emetteur, type, contexte));
        obj.add("destinataire", serialiseurDUtilisateur.serialize(invitation.destinataire, type, contexte));
        return obj;
	}
}
