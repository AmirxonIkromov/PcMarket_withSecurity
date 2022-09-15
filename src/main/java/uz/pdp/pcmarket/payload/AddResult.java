package uz.pdp.pcmarket.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import uz.pdp.pcmarket.entity.Photo;

@Data
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AddResult {

    private boolean success;

    private Photo photo;


}
