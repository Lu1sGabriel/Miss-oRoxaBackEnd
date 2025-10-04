package org.missao.roxa.missaoroxabackend.modules.products.domain.value;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.exception.types.InvalidRequestDataException;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Embeddable
@NoArgsConstructor(force = true)
@EqualsAndHashCode(of = "price")
public final class ProductPrice {

    private final BigDecimal price;

    public ProductPrice(BigDecimal price) {
        this.price = validate(price);
    }

    private static BigDecimal validate(BigDecimal price) {
        if (price == null) {
            throw new InvalidRequestDataException("The product price cannot be null.");
        }

        BigDecimal scaled = price.setScale(2, RoundingMode.HALF_UP);

        if (scaled.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidRequestDataException("The product price must be greater than zero.");
        }

        if (scaled.compareTo(new BigDecimal("1000000.00")) > 0) {
            throw new InvalidRequestDataException("The product price exceeds the maximum allowed value. (1000000.00)");
        }

        return scaled;
    }

    public BigDecimal getValue() {
        return price;
    }

}