package API.demo_jwt.Auth;

import API.demo_jwt.JWT.JwtService;
import API.demo_jwt.User.Rol;
import API.demo_jwt.User.Usuario;
import API.demo_jwt.User.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepositorio usuarioRepositorio;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    public AuthResponse register(registerRequest request) {
        Usuario user = Usuario.builder()
                .username(request.getUsername())
                /*Encodificacion de la contraseña*/
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .country(request.getCountry())
                .rol(Rol.USER)
                .build();
        usuarioRepositorio.save(user);
        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    public AuthResponse login(loginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = usuarioRepositorio.findByUsername(request.getUsername()).orElseThrow();
        String token= jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
