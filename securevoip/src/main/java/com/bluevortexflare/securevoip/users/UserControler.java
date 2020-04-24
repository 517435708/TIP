package com.bluevortexflare.securevoip.users;

import com.bluevortexflare.securevoip.users.register.UserRegisterService;
import com.bluevortexflare.securevoip.users.register.dto.RegisterRequest;
import com.bluevortexflare.securevoip.users.register.dto.RegisterResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserControler {

    private final UserRegisterService registerService;


    UserControler(UserRegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        return registerService.registerUser(request);
    }

}
