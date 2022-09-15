package uz.pdp.pcmarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.pcmarket.entity.Photo;
import uz.pdp.pcmarket.entity.Photo_content;
import uz.pdp.pcmarket.repository.PhotoRepository;
import uz.pdp.pcmarket.repository.Photo_ContentRepository;

@RestController
@RequestMapping("photo")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoRepository photoRepository;

    private final Photo_ContentRepository photo_contentRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPhoto(@PathVariable Integer id){
        Photo photo = photoRepository.findById(id).orElseThrow(() -> new IllegalStateException("photo id not foun"));
        Photo_content photo_content = photo_contentRepository.findByPhotoId(id).orElseThrow(() -> new IllegalStateException("photo not found"));

        return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.valueOf(photo.getType()))
                        .body(photo_content.getBytes());

    }

}
