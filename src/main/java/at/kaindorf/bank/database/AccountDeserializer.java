package at.kaindorf.bank.database;

import at.kaindorf.bank.pojos.Account;
import at.kaindorf.bank.pojos.GiroAccount;
import at.kaindorf.bank.pojos.SavingsAccount;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class AccountDeserializer extends StdDeserializer<Account> {

    public AccountDeserializer() {
        this(null);
    }

    @Override
    public Account deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        if (node.has("overdraft")) {
            return p.getCodec().treeToValue(node, GiroAccount.class);
        } else if (node.has("interest")) {
            return p.getCodec().treeToValue(node, SavingsAccount.class);
        } else {
            throw new IllegalArgumentException("Unknown account type");
        }
    }
    public AccountDeserializer(Class<?> vc) {
        super(vc);
    }

}
