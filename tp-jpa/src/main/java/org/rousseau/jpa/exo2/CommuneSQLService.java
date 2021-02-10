package org.rousseau.jpa.exo2;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class CommuneSQLService implements CommuneDBService{
	
	//********************
	// Mysql information login désormais inutile :
	// ********************
	static final String URL = "jdbc:mysql://localhost:3306/tp_jdbc";
	static final String USER = "jdbc_user";
	static final String PWD = "jdbc";
	//********************
	// Variable pour gestion du Batch d'importation des communes :
	// ********************
	private int compteuCommuneDansrBatch = 0;
	private static Boolean lastCommune = false;

	@Override
	public void writeCommune(Commune commune) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("tp-jpa-hibernate-create");
		System.out.println("EMF = "+ emf);
		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(commune);
		transaction.commit(); 

	}


	
	@Override
	public Commune getCommuneById(int id) { 

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("tp-jpa-hibernate-select");
		System.out.println("EMF = "+ emf);
		EntityManager entityManager = emf.createEntityManager();
		Commune communeById = entityManager.find(Commune.class, id);
		return communeById;

	}
	
	
// NE FONCTIONNE PAS : 
	@Override
	public Commune getCommuneByName(String name) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("tp-jpa-hibernate-select");
		System.out.println("EMF = "+ emf);
		EntityManager entityManager = emf.createEntityManager();
		Query query = entityManager.createQuery("SELECT * FROM communes WHERE nomCommune= ?");
		query.setParameter(1, name);
		List<Commune> communeByName = (List<Commune>) query.getResultList(); 
		return communeByName.get(1);	
		
	}
	


	@Override
	public int countCommuneByCP(String codePostal) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("tp-jpa-hibernate-select");
		System.out.println("EMF = "+ emf);
		EntityManager entityManager = emf.createEntityManager();
		Query query = entityManager.createQuery("SELECT * FROM communes WHERE codePostal like ?");
		query.setParameter(1, codePostal+"%");
		
		//Partie restante à faire : récupérer résultat pour transformer en Commune
		
		
		List communesByCP = query.getResultList();
		
		return communesByCP.size();
		
		
	}
	

}