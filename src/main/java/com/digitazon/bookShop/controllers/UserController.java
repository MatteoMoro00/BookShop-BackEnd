package com.digitazon.bookShop.controllers;

import com.digitazon.bookShop.entities.User;
import com.digitazon.bookShop.exceptions.AlreadySavedException;
import com.digitazon.bookShop.repositories.UserRepository;
import com.digitazon.bookShop.security.auth.AuthRequest;
import com.digitazon.bookShop.security.auth.AuthResponse;
import com.digitazon.bookShop.security.auth.JwtTokenUtil;
import com.digitazon.bookShop.servicies.abstractions.BookShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookShopService bookShopService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
            userRepository.findByUsername(user.getUsername());
            return ResponseEntity.ok("ok");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            User user = (User) authentication.getPrincipal();      //cast
            String accessToken = jwtTokenUtil.generatedAccessToken(user);
            AuthResponse response = new AuthResponse(user.getId(), user.getUsername(), accessToken, user.isAdmin());         //nella risposta passo it token e l'username
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();           //per avere codice 401 non autorizzato
        }
    }

    @PostMapping
    public ResponseEntity insert(@RequestBody User newUser) {
        try {
            return ResponseEntity.ok(bookShopService.insertUserToken(newUser));
        } catch (AlreadySavedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
