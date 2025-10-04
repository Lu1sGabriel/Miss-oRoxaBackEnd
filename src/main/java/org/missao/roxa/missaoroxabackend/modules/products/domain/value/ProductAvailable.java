package org.missao.roxa.missaoroxabackend.modules.products.domain.value;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(force = true)
@EqualsAndHashCode(of = "isAvailable")
public final class ProductAvailable {

    private final boolean isAvailable;

    public ProductAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public boolean getValue() {
        return isAvailable;
    }

}