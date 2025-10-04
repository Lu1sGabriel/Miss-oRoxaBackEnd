package org.missao.roxa.missaoroxabackend.modules.products.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.common.entity.domain.IEntity;
import org.missao.roxa.missaoroxabackend.core.common.entity.value.Description;
import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMappableEntity;
import org.missao.roxa.missaoroxabackend.core.shared.helper.uuid.UUIDV7;
import org.missao.roxa.missaoroxabackend.modules.category.domain.entity.CategoryEntity;
import org.missao.roxa.missaoroxabackend.modules.products.domain.metadata.ProductCommerceInfo;
import org.missao.roxa.missaoroxabackend.modules.products.domain.metadata.ProductDateInfo;
import org.missao.roxa.missaoroxabackend.modules.products.domain.value.ProductName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "products")
@NoArgsConstructor
@Getter
public class ProductEntity implements Serializable, IMappableEntity, IEntity {

    @Serial
    private static final long serialVersionUID = -1995208443409598708L;

    @Id
    @UUIDV7
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "name", nullable = false, unique = true))
    private ProductName name;

    @Embedded
    @AttributeOverride(name = "description", column = @Column(name = "description", nullable = false, columnDefinition = "TEXT"))
    private Description description;

    @Embedded
    private ProductCommerceInfo commerceInfo;

    @Embedded
    private ProductDateInfo dateInfo;

    public ProductEntity(CategoryEntity category, ProductName name, Description description,
                         ProductCommerceInfo commerceInfo) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.commerceInfo = commerceInfo;
        this.dateInfo = new ProductDateInfo();
    }

    public void changeName(String name) {
        this.name = new ProductName(name);
    }

    public void changeDescription(String description) {
        this.description = new Description(description);
    }

    public void changeCategory(CategoryEntity category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof ProductEntity that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hash(id) : System.identityHashCode(this);
    }

}