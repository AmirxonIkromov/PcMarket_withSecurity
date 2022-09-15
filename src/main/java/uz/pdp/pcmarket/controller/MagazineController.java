package uz.pdp.pcmarket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.pcmarket.payload.MagazinDTO;

@RequestMapping("/magazine")
public interface MagazineController {

    @PostMapping
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN')")
    ResponseEntity<?> add(@RequestBody MagazinDTO magazinDTO);

    @GetMapping
    ResponseEntity<?> getAll();

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN', 'MODERATOR')")
    ResponseEntity<?> getOne(@PathVariable Integer id);



    @PutMapping("/{id}")
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN')")
    ResponseEntity<?> edit(@PathVariable Integer id,
                   @RequestBody MagazinDTO magazinDTO);

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN')")
    ResponseEntity<?> delete(@PathVariable Integer id);


}
