package org.missao.roxa.missaoroxabackend.modules.user.domain.value;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.HttpException;

import java.time.LocalDate;
import java.time.Period;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode(of = "birthDate")
public final class BirthDate {

    private LocalDate birthDate;

    private static final byte MIN_AGE = 10;
    private static final byte MAX_AGE = 100;

    public BirthDate(LocalDate birthDate) {
        this.birthDate = validate(birthDate);
    }

    private static LocalDate validate(LocalDate birthDate) {
        if (birthDate == null) {
            throw HttpException.badRequest("Birth date cannot be null.");
        }

        final LocalDate today = LocalDate.now();

        if (birthDate.isAfter(today)) {
            throw HttpException.badRequest("The date of birth cannot be in the future.");
        }

        final int age = Period.between(birthDate, today).getYears();

        if (age < MIN_AGE || age > MAX_AGE) {
            throw HttpException.badRequest(
                    String.format("Age must be between %d and %d years. Provided: %d", MIN_AGE, MAX_AGE, age)
            );
        }

        return birthDate;
    }

    public LocalDate getValue() {
        return birthDate;
    }

}