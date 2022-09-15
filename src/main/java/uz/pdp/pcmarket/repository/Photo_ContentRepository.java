package uz.pdp.pcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.pcmarket.entity.Photo_content;

import java.util.Optional;


public interface Photo_ContentRepository extends JpaRepository<Photo_content,Integer> {

    Optional<Photo_content> findByPhotoId(Integer id);
}
