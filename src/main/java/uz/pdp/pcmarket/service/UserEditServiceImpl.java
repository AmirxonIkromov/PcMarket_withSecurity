package uz.pdp.pcmarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.pcmarket.entity.User;
import uz.pdp.pcmarket.payload.ApiResult;
import uz.pdp.pcmarket.payload.UserDTO;
import uz.pdp.pcmarket.repository.UserRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserEditServiceImpl implements UserEditService{

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public ApiResult edit(Integer id, UserDTO userDTO) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            user.setFullName(userDTO.getFullName());
            user.setPassword(encoder.encode(userDTO.getPassword()));
            user.setUsername(userDTO.getUsername());

            userRepository.save(user);

            return new ApiResult(true, "User edded");
        }
        return new ApiResult(false, "User not found");
    }

    @Override
    public ResponseEntity<?> signUp(UserDTO userDTO) {
        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Added");
    }

    @Override
    public ResponseEntity<?> singIn(UserDTO userDTO) {
       userRepository.findByUsername(userDTO.getUsername()).orElseThrow(IllegalStateException::new);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
}
