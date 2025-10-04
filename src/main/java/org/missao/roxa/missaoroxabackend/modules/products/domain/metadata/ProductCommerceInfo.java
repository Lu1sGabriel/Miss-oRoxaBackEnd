package org.missao.roxa.missaoroxabackend.modules.products.domain.metadata;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.modules.products.domain.value.ProductAvailable;
import org.missao.roxa.missaoroxabackend.modules.products.domain.value.ProductPrice;

import java.math.BigDecimal;

@Embeddable
@NoArgsConstructor
@Getter
public class ProductCommerceInfo {

    @Embedded
    @AttributeOverride(name = "price", column = @Column(name = "price", nullable = false, precision = 10, scale = 2))
    private ProductPrice price;

    @Embedded
    @AttributeOverride(name = "isAvailable", column = @Column(name = "available", nullable = false))
    private ProductAvailable available;

    public ProductCommerceInfo(ProductPrice price, ProductAvailable available) {
        this.price = price;
        this.available = available;
    }

    public void changePrice(BigDecimal price) {
        this.price = new ProductPrice(price);
    }

    public void changeAvailability(boolean availability) {
        this.available = new ProductAvailable(availability);
    }

}