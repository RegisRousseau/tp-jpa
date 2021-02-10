package org.rousseau.jpa.exo2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;



public class Exo2_jpa {
	
	public static void main(String[] args) {
		
//		testEcritureEnBD();
//		importCommuneFromCSV();
		CommuneSQLService communeSQLServive = new CommuneSQLService();
		int nbCommuneIn02 = communeSQLServive.countCommuneByCP("02");
		System.out.println("Commune in 02 = "+ nbCommuneIn02);
		
		Commune communeNamedSOISSONS = communeSQLServive.getCommuneByName("SOISSONS");
		System.out.println(communeNamedSOISSONS);
		
		Commune communeWithID616 = communeSQLServive.getCommuneById(616);
		System.out.println(communeWithID616);
		
	}
	
	static void importCommuneFromCSV() {
		//Importation des communes du CSV et les écrire dans la BD.
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("tp-jpa-hibernate-create");
		System.out.println("EMF = "+ emf);
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		List<String> communesInCSVLine = null; //Liste des communes en chaine de carratères
		Path pathCSV = Path.of("src/main/resources/laposte_hexasmal.csv");
		try (Stream<String> lines = Files.lines(pathCSV);) {
			communesInCSVLine = lines.skip(1).collect(Collectors.toList());//Mettre dans la liste les communes en chaine de carractère
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Nombre de commune: "+communesInCSVLine.stream().count());
		int compteur = 0;
		transaction.begin();
		for (String line : communesInCSVLine) {
			compteur++;
			if(compteur>19) {	//On envoie les communes par paquets de 19 (20 étant la limite fixé avec hibernate)
				transaction.commit(); 
				compteur = 0; 
				transaction.begin();
			}
			entityManager.persist(importCommune.apply(line));

		}
		transaction.commit();
	}
	
	static Function<String, Commune> importCommune = new Function<String, org.rousseau.jpa.exo2.Commune>() {
		public org.rousseau.jpa.exo2.Commune apply(String ligne) {
			String[] elementLigne = ligne.split(";");
			return new Commune(elementLigne[0], // codeINSEE
					elementLigne[1], // nomCommune
					elementLigne[2], // codePostal
					elementLigne[4] // libelleAcheminement
			);
		}
	};
	
	
	static void testEcritureEnBD() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("tp-jpa-hibernate-create");
		System.out.println("EMF = "+ emf);
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		//		Tests inutiles d'écriture dans la base de donnée :
		String ligneTest = "01001;L ABERGEMENT CLEMENCIAT;01400;;L ABERGEMENT CLEMENCIAT;46.1534255214,4.92611354223";
		String ligneTest2 = "01002;L ABERGEMENT CLEMENCIAT;01400;;L ABERGEMENT CLEMENCIAT;46.1534255214,4.92611354223";
		Commune communeTest = importCommune.apply(ligneTest);
		Commune communeTest2 = importCommune.apply(ligneTest2);
		transaction.begin();
		entityManager.persist(communeTest);
		entityManager.persist(communeTest2);
		transaction.commit(); 
	}
}
