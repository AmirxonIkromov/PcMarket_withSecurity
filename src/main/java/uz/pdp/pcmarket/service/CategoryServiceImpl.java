package uz.pdp.pcmarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import uz.pdp.pcmarket.entity.Category;
import uz.pdp.pcmarket.entity.Magazine;
import uz.pdp.pcmarket.payload.ApiResult;
import uz.pdp.pcmarket.payload.CategoryDTO;
import uz.pdp.pcmarket.repository.CategoryRepository;
import uz.pdp.pcmarket.repository.MagazineRepository;

import java.util.List;
import java.util.Map;

;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepositary;

    private final JdbcTemplate jdbcTemplate;

    private final MagazineRepository magazineRepository;


    @Override
    public ResponseEntity<?> add(CategoryDTO categoryDTO) {
            Magazine magazine = magazineRepository.findById(categoryDTO.getMagazin_id()).orElseThrow(() -> new IllegalStateException("Magazine not found"));
            Category category = new Category();
            category.setName(categoryDTO.getName());
            category.setMagazin(magazine);
            categoryRepositary.save(category);
            return ResponseEntity.ok(ApiResult.successResponce("Category added"));

    }

    @Override
    public ResponseEntity<?> getAll() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from category where is_active = true");
        if (maps.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("Categoriala yoq", HttpStatus.CONFLICT.value()));
        }
        return ResponseEntity.ok(ApiResult.successResponce(maps));
    }


    @Override
    public ResponseEntity<?> edit(Integer id, CategoryDTO categoryDTO) {

        Category category1 = categoryRepositary.findById(id).orElseThrow(() -> new IllegalStateException("bunday category yoq"));
        Magazine magazin = magazineRepository.findById(categoryDTO.getMagazin_id()).orElseThrow(() -> new IllegalStateException("bunday magazin yoq"));
        boolean b = categoryRepositary.existsByName(category1.getName());
        if (category1.isActive() && !b) {
            category1.setName(categoryDTO.getName());
            category1.setMagazin(magazin);
            categoryRepositary.save(category1);
            return ResponseEntity.ok(ApiResult.successResponce("success category"));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("Bunday category mavjud", HttpStatus.CONFLICT.value()));
        }
    }
    @Override
    public ResponseEntity<?> delete(Integer id) {
            Category category = categoryRepositary.findById(id).orElseThrow(() -> new IllegalStateException("bunday category yoq!"));
            if (category.isActive()) {
                category.setActive(false);
                categoryRepositary.save(category);
                return ResponseEntity.ok(ApiResult.successResponce("delete boldi!"));
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("Category not found", HttpStatus.CONFLICT.value()));
        }
}
