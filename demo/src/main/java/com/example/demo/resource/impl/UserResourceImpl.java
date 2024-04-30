//package com.example.demo.resource.impl;
//
//import com.example.demo.config.JwtTokenProvider;
//import com.example.demo.domain.User;
//import com.example.demo.repository.UserRepository;
//import org.codehaus.jettison.json.JSONException;
//import org.codehaus.jettison.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/user")
//public class UserResourceImpl {
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtTokenProvider tokenProvider;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @PostMapping(value = "/authenticate", produces = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8")
//    public ResponseEntity<String> authenticate(@RequestBody User user) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                user.getEmail(), user.getRole()
//        ));
//
//        JSONObject jsonObject = new JSONObject();
//        try {
//            String email = user.getEmail();
//            jsonObject.put("toke", tokenProvider.createToken(email, userRepository.findByEmail(email).getRole()));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return new ResponseEntity<String>(jsonObject.toString(), HttpStatus.OK);
//    }
//}
