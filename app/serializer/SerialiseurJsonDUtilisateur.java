package serializer;

import java.lang.reflect.Type;
import models.Utilisateur;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class SerialiseurJsonDUtilisateur implements JsonSerializer<Utilisateur> {

	@Override
	public JsonElement serialize(Utilisateur utilisateur, Type type, JsonSerializationContext contexte) {
		JsonObject obj = new JsonObject();
        obj.addProperty("id", utilisateur.id);
        obj.addProperty("email", utilisateur.email);
        obj.addProperty("surnom", utilisateur.surnom);
        return obj;
	}
}
