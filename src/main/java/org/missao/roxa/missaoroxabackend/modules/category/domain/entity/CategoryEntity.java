package org.missao.roxa.missaoroxabackend.modules.category.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.missao.roxa.missaoroxabackend.core.common.entity.domain.IEntity;
import org.missao.roxa.missaoroxabackend.core.common.entity.value.Description;
import org.missao.roxa.missaoroxabackend.core.shared.helper.mapper.IMappableEntity;
import org.missao.roxa.missaoroxabackend.core.shared.helper.uuid.UUIDV7;
import org.missao.roxa.missaoroxabackend.modules.category.domain.metadata.CategoryDateInfo;
import org.missao.roxa.missaoroxabackend.modules.category.domain.values.CategoryName;
import org.missao.roxa.missaoroxabackend.modules.products.domain.entity.ProductEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
public class CategoryEntity implements Serializable, IEntity, IMappableEntity {

    @Serial
    private static final long serialVersionUID = 1505277449521294517L;

    @Id
    @UUIDV7
    private UUID id;

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "name", nullable = false, unique = true))
    private CategoryName name;

    @Embedded
    @AttributeOverride(name = "description", column = @Column(name = "description", nullable = false, columnDefinition = "TEXT"))
    private Description description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CategoryEntity> subCategories;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductEntity> products;

    @Embedded
    private CategoryDateInfo dateInfo;

    public CategoryEntity(CategoryName name, Description description) {
        this.name = name;
        this.description = description;
        this.subCategories = new HashSet<>();
        this.products = new HashSet<>();
        this.dateInfo = new CategoryDateInfo();
    }

    public void changeName(String name) {
        this.name = new CategoryName(name);
    }

    public void changeDescription(String description) {
        this.description = new Description(description);
    }

    public void changeParent(CategoryEntity parent) {
        this.parent = parent;
    }

    public void addSubCategory(CategoryEntity subCategory) {
        subCategory.parent = this;
        this.subCategories.add(subCategory);
    }

}