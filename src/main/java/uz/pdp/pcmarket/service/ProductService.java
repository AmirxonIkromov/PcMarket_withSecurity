package uz.pdp.pcmarket.service;


import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

public interface ProductService {
    ResponseEntity<?> add(MultipartHttpServletRequest request) throws IllegalStateException;
    ResponseEntity<?>  addList(MultipartHttpServletRequest request) throws IllegalStateException, IOException;

    ResponseEntity<?>  getAll(Integer page);


    ResponseEntity<?>  edit(Integer id, MultipartHttpServletRequest multipartHttpServletRequest) throws IllegalStateException;

    ResponseEntity<?>  delete(Integer id);

    ResponseEntity<?>  filter(String name, String priceOT, String priceDO, Integer id);
}
