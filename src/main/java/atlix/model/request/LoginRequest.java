package atlix.model.request;

import java.io.Serializable;

public record LoginRequest(String username, String password) implements Serializable { }

