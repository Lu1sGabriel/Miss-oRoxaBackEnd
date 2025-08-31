package org.missao.roxa.missaoroxabackend.modules.authority.domain.enums;

import lombok.Getter;

@Getter
public enum AuthorityUser implements IAuthority {
    VIEW_PROFILE("user:view_profile", "Allows the user to view their complete profile information."),
    CHANGE_FULL_NAME("user:change-full-name", "Allows the user to update or modify their full name."),
    CHANGE_BIRTH_DATE("user:change-birth-date", "Allows the user to update their date of birth information."),
    CHANGE_EMAIL("user:change-email", "Allows the user to change their registered email address."),
    CHANGE_PASSWORD("user:change-password", "Allows the user to change their account password for security purposes."),
    DEACTIVATE_ACCOUNT("user:deactivate-account", "Allows the user to deactivate their account, making it inactive and preventing further access until reactivation.");

    private final String name;
    private final String description;

    AuthorityUser(String name, String description) {
        this.name = name;
        this.description = description;
    }

}