package API.demo_jwt.Auth;

import API.demo_jwt.JWT.JwtService;
import API.demo_jwt.User.Rol;
import API.demo_jwt.User.Usuario;
import API.demo_jwt.User.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepositorio usuarioRepositorio;
    private final JwtService jwtService;

    public AuthResponse register(registerRequest request) {
        Usuario user = Usuario.builder()
                .username(request.getUsername())
                .password(request.getPassword())
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
        return null;
    }
}
