package uz.pdp.pcmarket.service;


import org.springframework.http.ResponseEntity;
import uz.pdp.pcmarket.payload.ApiResult;
import uz.pdp.pcmarket.payload.UserDTO;

public interface UserEditService {
    ApiResult edit(Integer id, UserDTO userDTO);

    ResponseEntity<?> signUp(UserDTO userDTO);

    ResponseEntity<?> singIn(UserDTO userDTO);
}
