package com.platform.naxterbackend.user.model;

public enum RoleEnum {

    GENERIC("GENERIC", "Generic user"),
    CONSUMER("CONSUMER", "Consumer user"),
    PRODUCER("PRODUCER", "Producer user"),
    MODERATOR("MODERATOR", "Moderator user"),
    ADMIN("ADMIN", "Aministrator user");

    private String type;
    private String description;

    private RoleEnum(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}
