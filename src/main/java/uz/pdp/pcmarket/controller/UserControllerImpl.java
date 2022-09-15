package uz.pdp.pcmarket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.pcmarket.payload.ApiResult;
import uz.pdp.pcmarket.payload.UserDTO;
import uz.pdp.pcmarket.service.UserEditService;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserEditService userEditService;

    public ApiResult edit(Integer id, UserDTO userDTO) {
        return userEditService.edit(id,userDTO);
    }

    @Override
    public ResponseEntity<?> signUp(UserDTO userDTO) {
        return ResponseEntity.ok(userEditService.signUp(userDTO));
    }

    @Override
    public ResponseEntity<?> signIn(UserDTO userDTO) {
        return ResponseEntity.ok(userEditService.singIn(userDTO));
    }


}
