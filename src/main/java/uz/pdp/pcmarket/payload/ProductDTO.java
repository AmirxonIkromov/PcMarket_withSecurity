package uz.pdp.pcmarket.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import uz.pdp.pcmarket.entity.Photo;

@Data
@AllArgsConstructor
public class ProductDTO {

    private Integer id;

    private String name;

    private Double price;

    private Integer category_id;

    private Photo photo;


    public ProductDTO(Integer id, String name, Double price, Integer id1) {

    }
}
