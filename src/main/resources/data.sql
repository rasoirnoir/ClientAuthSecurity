INSERT INTO adresse (id, code_postal, complement, pays, ville, voie) VALUES
(1, "75015", '', "FRANCE", "PARIS", "5, rue du Renard"),
(2, "78300", '', "FRANCE", "POISSY", "20, boulevard Gambetta"),
(4, "35740", '', "FRANCE", "PACE", "6 place de l'église");

INSERT INTO client (id, nom,prenom, address_id) VALUES
(1, "MARTIN", "Jean", NULL),
(2, "DUPONT", "sophie", 1),
(3, "DURAND", "Pierre", 4);


