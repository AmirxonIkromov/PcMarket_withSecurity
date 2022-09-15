package uz.pdp.pcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.pcmarket.entity.Basket;

public interface BasketRepository extends JpaRepository<Basket,Integer> {
}
