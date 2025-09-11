package org.missao.roxa.missaoroxabackend.modules.account.domain.metadata;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.Email;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.Password;
import org.missao.roxa.missaoroxabackend.modules.account.domain.value.PhoneNumber;

@Embeddable
@NoArgsConstructor
@Getter
public class AccountCredentials {

    @Column(name = "email", nullable = false, unique = true)
    @NaturalId
    private Email email;

    @Column(name = "password", nullable = false)
    private Password password;

    @AttributeOverride(name = "number", column = @Column(name = "phone_number", nullable = false, unique = true))
    private PhoneNumber phoneNumber;

    public AccountCredentials(Email email, Password password, PhoneNumber phoneNumber) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public void changeEmail(String email) {
        this.email = new Email(email);
    }

    public void changePassword(String password) {
        this.password = new Password(password);
    }

    public void changePhoneNumber(String phoneNumber) {
        this.phoneNumber = new PhoneNumber(phoneNumber);
    }

    public void checkIfPasswordMatch(String currentPassword) {
        this.getPassword().isPasswordMatches(this.getPassword().getValue(), currentPassword);
    }

}