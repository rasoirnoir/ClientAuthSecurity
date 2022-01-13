package fr.bouget.pinard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fr.bouget.pinard.exception.ExistingUsernameException;
import fr.bouget.pinard.exception.InvalidCredentialsException;
import fr.bouget.pinard.model.AppUser;
import fr.bouget.pinard.repository.AppUserRepository;
import fr.bouget.pinard.security.JwtTokenProvider;

@Service
public class AppUserServiceImpl implements AppUserService {

	@Autowired
    private AppUserRepository appUserRepository; // permet communication avec la BD
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder; // permet l'encodage du mot de passe
	
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;	// permet la fourniture du Jeton (Token)
	
	@Autowired
    private AuthenticationManager authenticationManager; // gestionnaire d'authentification


	/**
	 * Permet de se connecter en encodant le mot de passe avec génération du token.
	 */
    @Override
    public String signin(String username, String password) throws InvalidCredentialsException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, appUserRepository.findByUsername(username).get().getRoleList());
        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException();
        }
    }

    @Override
    public String signup(AppUser user) throws ExistingUsernameException {
        if (!appUserRepository.existsByUsername(user.getUsername())) {
            AppUser userToSave = new AppUser(user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getRoleList());
//        	AppUser userToSave = new AppUser(user.getUsername(), user.getPassword(), user.getRoleList());
            appUserRepository.save(userToSave);
            return jwtTokenProvider.createToken(user.getUsername(), user.getRoleList());
        } else {
            throw new ExistingUsernameException();
        }
    }

    @Override
    public List<AppUser> findAllUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public Optional<AppUser> findUserByUserName(String username) {
        return appUserRepository.findByUsername(username);
    }
}
