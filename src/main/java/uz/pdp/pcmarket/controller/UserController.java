package uz.pdp.pcmarket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.pcmarket.payload.ApiResult;
import uz.pdp.pcmarket.payload.UserDTO;

@RequestMapping("/user")
public interface UserController {



    @PutMapping("/{id}")
    ApiResult edit(@PathVariable Integer id,
                   @RequestBody UserDTO userDTO);

    @PostMapping("/singUp")
     ResponseEntity<?> signUp(@RequestBody UserDTO userDTO);

    @PostMapping("/singIn")
    ResponseEntity<?> signIn(@RequestBody UserDTO userDTO);


}
