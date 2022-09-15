package uz.pdp.pcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.pcmarket.entity.Magazine;


public interface MagazineRepository extends JpaRepository<Magazine,Integer> {


    boolean existsByName(String name);
}
