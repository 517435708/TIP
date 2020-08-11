package com.bluevortexflare.securevoip.users;

import com.bluevortexflare.securevoip.users.register.UserRegisterService;
import com.bluevortexflare.securevoip.users.register.dto.RegisterRequest;
import com.bluevortexflare.securevoip.users.register.dto.RegisterResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRegisterService registerService;


    UserController(UserRegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping(value = "/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        return registerService.registerUser(request);
    }

}
