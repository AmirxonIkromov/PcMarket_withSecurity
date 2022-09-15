package uz.pdp.pcmarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.pcmarket.payload.MagazinDTO;
import uz.pdp.pcmarket.service.MagazineService;

@RestController
@RequiredArgsConstructor
public class MagazineControllerImpl implements MagazineController {

    private final MagazineService magazineService;


    @Override
    public ResponseEntity<?> add(MagazinDTO magazinDTO) {
        return magazineService.add(magazinDTO);
    }

    @Override
    public ResponseEntity<?> getAll() {
        return magazineService.getAll();
    }

    @Override
    public ResponseEntity<?> getOne(Integer id) {
        return magazineService.get(id);
    }

    @Override
    public ResponseEntity<?> edit(Integer id, MagazinDTO magazinDTO) {
        return magazineService.edit(id,magazinDTO);
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        return magazineService.delete(id);
    }
}
