package uz.pdp.pcmarket.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    private boolean isActive = true;

    @ManyToOne
    private Category category;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Photo photo;
    
}
