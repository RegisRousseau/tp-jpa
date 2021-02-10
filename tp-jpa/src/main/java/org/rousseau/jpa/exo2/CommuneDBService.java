package org.rousseau.jpa.exo2;

public interface CommuneDBService {
	void writeCommune(Commune commune);
	Commune getCommuneById(int id);
	Commune getCommuneByName(String name);
	int countCommuneByCP(String codePostal);
}

