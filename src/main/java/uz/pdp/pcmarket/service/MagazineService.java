package uz.pdp.pcmarket.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.pcmarket.payload.MagazinDTO;

public interface MagazineService {
    ResponseEntity<?> add(MagazinDTO magazinDTO);

    ResponseEntity<?> getAll();

    ResponseEntity<?> get(Integer id);

    ResponseEntity<?> edit(Integer id, MagazinDTO magazinDTO);

    ResponseEntity<?> delete(Integer id);
}
