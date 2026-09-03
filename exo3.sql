DROP DATABASE IF EXISTS salle_sport;
CREATE DATABASE salle_sport;
USE salle_sport;

CREATE TABLE adherent (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    telephone VARCHAR(20),
    dateNaissance DATE
);

CREATE TABLE coach (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    specialite VARCHAR(100)
);

CREATE TABLE salle (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    capacite INT NOT NULL
);

CREATE TABLE cours_collectif (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    capacite INT NOT NULL,
    creneau VARCHAR(50) NOT NULL,
    id_salle INT NOT NULL,
    id_coach INT NOT NULL,
    FOREIGN KEY (id_salle) REFERENCES salle(id),
    FOREIGN KEY (id_coach) REFERENCES coach(id)
);

CREATE TABLE abonnement (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_adherent INT NOT NULL,
    dateDebut DATE NOT NULL,
    dateFin DATE NOT NULL,
    statut VARCHAR(30) NOT NULL,
    type VARCHAR(50) NOT NULL,
    montant DECIMAL(10,2) NOT NULL,
    date_paiement DATE,
    mode_paiement VARCHAR(30),
    FOREIGN KEY (id_adherent) REFERENCES adherent(id)
);

CREATE TABLE inscription (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_adherent INT NOT NULL,
    id_cours INT NOT NULL,
    dateInscription DATE NOT NULL,
    presence TINYINT(1) NOT NULL DEFAULT 0,
    FOREIGN KEY (id_adherent) REFERENCES adherent(id),
    FOREIGN KEY (id_cours) REFERENCES cours_collectif(id),
    UNIQUE (id_adherent, id_cours)
);

--ADHERENTS
INSERT INTO adherent (nom, prenom, email, telephone, dateNaissance) VALUES
('Ndoye', 'Fatou', 'fatou.ndoye@email.com', '771234567', '1995-03-12'),
('Ndiaye', 'Moussa', 'moussa.ndiaye@email.com', '772345678', '1992-07-22'),
('Fall', 'Aissatou', 'aissatou.fall@email.com', '773456789', '1990-11-05'),
('Sow', 'Ibrahima', 'ibrahima.sow@email.com', '774567890', '2000-01-18'),
('Ba', 'Mariama', 'mariama.ba@email.com', '775678901', '1998-09-30'),
('Diallo', 'Cheikh', 'cheikh.diallo@email.com', '776789012', '2004-05-14');

--COACHS
INSERT INTO coach (nom, prenom, specialite) VALUES
('Sarr', 'Amadou', 'Musculation'),
('Gueye', 'Bineta', 'Yoga'),
('Faye', 'Ousmane', 'Cardio / HIIT');

--SALLES
INSERT INTO salle (nom, capacite) VALUES
('Salle A', 20),
('Salle B', 25),
('Salle Yoga', 10);

--COURS COLLECTIF
INSERT INTO cours_collectif (nom, type, capacite, creneau, id_salle, id_coach) VALUES
('Musculation Débutant', 'Musculation', 12, 'Lundi 08h00-09h00', 1, 1),
('Yoga Matinal', 'Yoga', 10, 'Mardi 07h00-08h00', 3, 2),
('HIIT Intensif', 'Cardio', 15, 'Mercredi 18h00-19h00', 2, 3),
('Musculation Avancé', 'Musculation', 12, 'Jeudi 17h00-18h00', 1, 1);

--ABONNEMENT
INSERT INTO abonnement (id_adherent, dateDebut, dateFin, statut, type, montant, date_paiement, mode_paiement) VALUES
(1, '2026-08-01', '2026-08-31', 'actif', 'Mensuel', 15000, '2026-08-01', 'Orange Money'),
(2, '2026-07-01', '2026-09-30', 'actif', 'Trimestriel', 40000, '2026-07-01', 'Espèces'),
(3, '2026-01-01', '2026-06-30', 'expiré', 'Semestriel', 70000, '2026-01-01', 'Carte bancaire'),
(4, '2026-08-15', '2026-09-14', 'actif', 'Mensuel', 15000, '2026-08-15', 'Wave'),
(5, '2026-08-01', '2027-07-31', 'actif', 'Annuel', 130000, '2026-08-01', 'Carte bancaire');

--INSCRIPTION
INSERT INTO inscription (id_adherent, id_cours, dateInscription, presence) VALUES
(1, 1, '2026-08-02', 1),
(1, 3, '2026-08-05', 1),
(2, 2, '2026-08-03', 1),
(2, 4, '2026-08-06', 0),
(3, 1, '2026-08-02', 1),
(4, 3, '2026-08-05', 1),
(4, 2, '2026-08-03', 0),
(5, 1, '2026-08-02', 1),
(5, 4, '2026-08-06', 1);
