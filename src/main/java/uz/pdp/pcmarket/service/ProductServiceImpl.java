package uz.pdp.pcmarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.pcmarket.entity.Category;
import uz.pdp.pcmarket.entity.Product;
import uz.pdp.pcmarket.payload.AddResult;
import uz.pdp.pcmarket.payload.ApiResult;
import uz.pdp.pcmarket.repository.CategoryRepository;
import uz.pdp.pcmarket.repository.PhotoRepository;
import uz.pdp.pcmarket.repository.Photo_ContentRepository;
import uz.pdp.pcmarket.repository.ProductRepository;

import java.io.IOException;
import java.util.*;

;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final PhotoRepository photoRepository;

    private final Photo_ContentRepository photo_contentRepository;

    private final ProductRepository productRepository;

    private final PhotoService photoService;

    private final JdbcTemplate jdbcTemplate;

    private final CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> add(MultipartHttpServletRequest multipartHttpServletRequest) throws IllegalStateException {
        try {
            Product product = new Product();
            product.setName(multipartHttpServletRequest.getParameter("name"));
            product.setPrice(Double.valueOf(multipartHttpServletRequest.getParameter("price")));
            Optional<Category> category = categoryRepository.findById(Integer.valueOf(multipartHttpServletRequest.getParameter("category")));
            if (category.isPresent()) {
                product.setCategory(category.get());
                Iterator<String> fileNames = multipartHttpServletRequest.getFileNames();
                MultipartFile file = multipartHttpServletRequest.getFile(fileNames.next());

                if (file != null) {
                    String originalFilename = file.getOriginalFilename();

                    String[] split = originalFilename.split("\\.");
                    String image = split[split.length - 1];

                    boolean status = image.equals("bmp") || image.equals("cod") || image.equals("gif") || image.equals("ief")
                            || image.equals("jpeg") || image.equals("jpg") || image.equals("jfif") || image.equals("svg")
                            || image.equals("tif") || image.equals("tiff") || image.equals("ras") || image.equals("cmx")
                            || image.equals("ico") || image.equals("pnm") || image.equals("pbm") || image.equals("pgm")
                            || image.equals("ppm") || image.equals("rgb") || image.equals("xbm") || image.equals("xpm")
                            || image.equals("xwd") || image.equals("jpe");
                    if (status) {
                        AddResult add = photoService.add(file, multipartHttpServletRequest);
                        if (add.isSuccess()) {
                            product.setPhoto(add.getPhoto());
                            productRepository.save(product);
                            return ResponseEntity.ok(ApiResult.successResponce("Product added"));
                        } else {
                            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("Image error", HttpStatus.CONFLICT.value()));
                        }
                    } else {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("Photo not found", HttpStatus.CONFLICT.value()));
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("File is empty", HttpStatus.CONFLICT.value()));
                }
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("Category is empty", HttpStatus.CONFLICT.value()));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("Malumotlarni toliq berish majburiy", HttpStatus.CONFLICT.value()));
        }
    }

    @Override
    public ResponseEntity<?> addList(MultipartHttpServletRequest request) throws IllegalStateException, IOException {
        List<Product> productList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Product product = new Product();
            product.setName(request.getParameter("name") + i);
            Double price = Double.parseDouble(request.getParameter("price")) + (Math.random() * 1000);
            product.setPrice(price);
            Optional<Category> category = categoryRepository.findById(Integer.valueOf(request.getParameter("category")));
            if (category.isPresent()) {
                product.setCategory(category.get());
                Iterator<String> fileNames = request.getFileNames();
                MultipartFile file = request.getFile(fileNames.next());
                String image = "jpg";
                if (file != null) {
                    AddResult add = photoService.add(file, request);
                    if (add.isSuccess()) {
                        product.setPhoto(add.getPhoto());
                        productList.add(product);
                    }
                } else {
                    productList.add(product);
                }
            }
        }
        productRepository.saveAll(productList);
        return ResponseEntity.ok(ApiResult.successResponce("Product added"));
    }

    @Override
    public ResponseEntity<?> getAll(Integer page) {
        int i = page * 20;
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select p.id, p.name, p.price, ph.name as photo_name, ph.id as photo_id, p.category_id, c.name as category_name from product p left join category c on c.id = p.category_id left join photo ph on ph.id = p.photo_id where p.is_active = true order by p.id offset ? limit 20", i);
        if (maps.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("Malumot topilmadi", HttpStatus.CONFLICT.value()));
        }
        return ResponseEntity.ok(ApiResult.successResponce("Product added", maps));
    }


    @Override
    public ResponseEntity<?> edit(Integer id, MultipartHttpServletRequest multipartHttpServletRequest) throws IllegalStateException {
        try {
                Product product = productRepository.findById(id).orElseThrow(() -> new IllegalStateException("Product id not foun"));
                Category category = categoryRepository.findById(Integer.valueOf(multipartHttpServletRequest.getParameter("category"))).orElseThrow(() -> new IllegalStateException("Category id not found"));


                Iterator<String> fileNames = multipartHttpServletRequest.getFileNames();
                MultipartFile file = multipartHttpServletRequest.getFile(fileNames.next());

                product.setName(multipartHttpServletRequest.getParameter("name"));

                String price = multipartHttpServletRequest.getParameter("price");

                product.setPrice(Double.valueOf(price));
                product.setCategory(category);

                if (file != null) {
                    String originalFilename = file.getOriginalFilename();

                    String[] split = originalFilename.split("\\.");
                    String image = split[split.length - 1];

                    boolean status = image.equals("bmp") || image.equals("cod") || image.equals("gif") || image.equals("ief")
                            || image.equals("jpeg") || image.equals("jpg") || image.equals("jfif") || image.equals("svg")
                            || image.equals("tif") || image.equals("tiff") || image.equals("ras") || image.equals("cmx")
                            || image.equals("ico") || image.equals("pnm") || image.equals("pbm") || image.equals("pgm")
                            || image.equals("ppm") || image.equals("rgb") || image.equals("xbm") || image.equals("xpm")
                            || image.equals("xwd") || image.equals("jpe");

                    if (product.getPhoto() == null && status) {

                        AddResult add = photoService.add(file, multipartHttpServletRequest);
                        if (add.isSuccess()) {
                            product.setPhoto(add.getPhoto());
                            productRepository.save(product);
                            return ResponseEntity.ok(ApiResult.successResponce("Product added"));
                        } else {
                            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("File error ", HttpStatus.CONFLICT.value()));
                        }
                    } else if(status) {
                        AddResult edit = photoService.edit(file, multipartHttpServletRequest, product.getPhoto().getId());
                        if (edit.isSuccess()) {
                            return ResponseEntity.ok(ApiResult.successResponce("Product edited"));
                        }
                        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("Foto not found", HttpStatus.CONFLICT.value()));
                    }
                        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("Photo notogri berilgan", HttpStatus.CONFLICT.value()));

                } else {
                    productRepository.save(product);
                    return ResponseEntity.ok(ApiResult.successResponce("Product edited"));
                }
            } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
            Product product = productRepository.findById(id).orElseThrow(() -> new IllegalStateException("Product id not found"));
            if (product.isActive()) {
                product.setActive(false);
                productRepository.save(product);

                return ResponseEntity.ok(ApiResult.successResponce("Delete success!"));
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("Product not found", HttpStatus.CONFLICT.value()));
        }

    @Override
    public ResponseEntity<?> filter(String name, String priceOT, String priceDO, Integer category) {
        String sql = "";
        if (name != null) {
            sql += " and upper(p.name) like upper('%" + name + "%') ";
        }
        if (priceOT != null && priceDO != null) {
            sql += "  and p.price between '" + priceOT + "' and '" + priceDO + "' ";
        }
        if (category != null) {
            sql += "  and p.category_id = " + category;
        }
        List<Map<String, Object>> maps;
        if (!sql.isEmpty()) {
            maps = jdbcTemplate.queryForList("select p.id, p.name, p.price, ph.name as photo_name, ph.id as photo_id, p.category_id, c.name as category_name from product p left join category c on c.id = p.category_id left join photo ph on ph.id = p.photo_id where p.is_active = true  " + sql);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("Malumotlar topilmadi", HttpStatus.CONFLICT.value()));
        }

        if (maps.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResult.failResponse("Malumot not found", HttpStatus.CONFLICT.value()));
        }
        return ResponseEntity.ok(ApiResult.successResponce(maps));
    }

}
