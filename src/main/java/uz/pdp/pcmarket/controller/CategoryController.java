package uz.pdp.pcmarket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.pcmarket.payload.CategoryDTO;

@RequestMapping("/category")
public interface CategoryController {

    @PostMapping
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN')")
    ResponseEntity<?> add(@RequestBody CategoryDTO categoryDTO);

    @GetMapping
    ResponseEntity<?> getAll();


    @PutMapping("/{id}")
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN')")
    ResponseEntity<?> edit(@PathVariable Integer id,
                   @RequestBody CategoryDTO categoryDTO);

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN')")
    ResponseEntity<?> delete(@PathVariable Integer id);


}
