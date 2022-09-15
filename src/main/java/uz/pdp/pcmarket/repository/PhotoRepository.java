package uz.pdp.pcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.pcmarket.entity.Photo;


public interface PhotoRepository extends JpaRepository<Photo,Integer> {
}
