package fr.bouget.pinard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.bouget.pinard.model.Adresse;

/**
 * @author Philippe
 *
 */
public interface AdresseRepository extends JpaRepository<Adresse, Integer> {

}
