package ru.geekbrains.hibernate.m2m.meta;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "payers")
public class Payer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "payers_products",
            joinColumns = @JoinColumn(name = "payer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Payer() {
    }

    @Override
    public String toString() {
        return "Payer{" +
                "id=" + id +
                ", name='" + name + '}';
    }
}
