package org.missao.roxa.missaoroxabackend.modules.account.domain.metadata;

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

    @Column(name = "phone_number", nullable = false, unique = true)
    private PhoneNumber phoneNumber;

    public AccountCredentials(Email email, Password password, PhoneNumber phoneNumber) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public void changeEmail(Email email) {
        this.email = email;
    }

    public void changePassword(Password password) {
        this.password = password;
    }

    public void changePhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}