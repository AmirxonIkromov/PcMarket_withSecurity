package uz.pdp.pcmarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import uz.pdp.pcmarket.entity.Magazine;
import uz.pdp.pcmarket.payload.ApiResult;
import uz.pdp.pcmarket.payload.MagazinDTO;
import uz.pdp.pcmarket.repository.MagazineRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MagazineServiceImpl implements MagazineService {

    private final MagazineRepository magazineRepository;


    private final JdbcTemplate jdbcTemplate;


    @Override
    public ResponseEntity<?> add(MagazinDTO magazinDTO) {
            if (!magazinDTO.getAddress().isEmpty() || !magazinDTO.getName().isEmpty() || !magazinDTO.getPhone().isEmpty()) {
                if (!magazineRepository.existsByName(magazinDTO.getName())) {
                    Magazine magazin = new Magazine();
                    magazin.setAddress(magazinDTO.getAddress());
                    magazin.setName(magazinDTO.getName());
                    magazin.setPhone(magazinDTO.getPhone());
                    magazineRepository.save(magazin);

                    return ResponseEntity.ok(ApiResult.successResponce("Magazine added"));
                } else {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("Bunday magazine mavjud!!!", HttpStatus.CONFLICT.value()));
                }
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("Magazine malumotlari toliq beriliwi wart!!!", HttpStatus.CONFLICT.value()));
            }
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from magazine where is_active = true");
        if (maps.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("Magazinelar yoq", HttpStatus.CONFLICT.value()));
        }
        return ResponseEntity.ok(ApiResult.successResponce(maps));
    }

    @Override
    public ResponseEntity<?> get(Integer id) {
        String query = "select * from magazine where id=" + id + "and is_active = true";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(query);
        if (maps.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("Malumot topilmadi!!", HttpStatus.CONFLICT.value()));
        }
        return ResponseEntity.ok(ApiResult.successResponce(maps));

    }

    @Override
    public ResponseEntity<?> edit(Integer id, MagazinDTO magazinDTO) {
            if (!magazineRepository.existsByName(magazinDTO.getName())) {
                Magazine magazin = magazineRepository.findById(id).orElseThrow(() -> new IllegalStateException("Magazine id not found"));

                magazin.setName(magazinDTO.getName());
                magazin.setPhone(magazinDTO.getPhone());
                magazin.setAddress(magazinDTO.getAddress());

                magazineRepository.save(magazin);

                return ResponseEntity.ok(ApiResult.successResponce("Magazine  edidded"));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("Bunday magazine mavjud!!!", HttpStatus.CONFLICT.value()));
            }
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
            Magazine magazin = magazineRepository.findById(id).orElseThrow(() -> new IllegalStateException("Magazine id not found"));
            if (!magazineRepository.existsByName(magazin.getName())) {
                magazin.setActive(false);
                magazineRepository.save(magazin);

                return ResponseEntity.ok(ApiResult.successResponce("Magazin delete !!!"));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("Bunday magazine mavjud emas!!!", HttpStatus.CONFLICT.value()));
            }

    }
}
