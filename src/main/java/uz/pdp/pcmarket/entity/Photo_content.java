package uz.pdp.pcmarket.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Photo_content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private byte[] bytes;


    @OneToOne
    private Photo photo;
}
