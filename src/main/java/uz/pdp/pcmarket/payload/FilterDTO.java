package uz.pdp.pcmarket.payload;

import lombok.Data;

@Data
public class FilterDTO {
    private Integer id;

    private String name;

    private Double priceOT;

    private Double priceDO;

    private Integer category_id;
}
