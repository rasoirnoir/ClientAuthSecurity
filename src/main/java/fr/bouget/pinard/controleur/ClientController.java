package fr.bouget.pinard.controleur;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.bouget.pinard.model.Client;
import fr.bouget.pinard.repository.ClientRepository;

/**
 * @author Philippe
 *
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientRepository clientRepository;
	
	


	@GetMapping(value = "/clients")
	// ajouter les autorisations en fonction des droits... rôle READER
	public ResponseEntity<?> getAll(){
		List<Client> liste = null;
		try
		{
			liste = clientRepository.findAll();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

		return ResponseEntity.status(HttpStatus.OK).body(liste);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addClient(@RequestBody Client client){
		try {
			clientRepository.save(client);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(client);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteClient(@PathVariable long id){
		Client clientToDelete = clientRepository.findById((int)id).get();
		try {
			clientRepository.delete(clientToDelete);
		}
		catch(NoSuchElementException nsee) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(nsee);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}
		return ResponseEntity.status(HttpStatus.OK).body(clientToDelete);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateClient(@RequestBody Client clientToUpdate){
		try {
			clientRepository.save(clientToUpdate);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.OK).body(clientToUpdate);
	}

}
