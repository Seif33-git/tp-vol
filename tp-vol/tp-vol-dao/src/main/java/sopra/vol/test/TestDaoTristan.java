package sopra.vol.test;

import java.util.List;

import sopra.vol.Application;
import sopra.vol.dao.IAdresseDao;
import sopra.vol.dao.IClientDao;
import sopra.vol.model.Adresse;
import sopra.vol.model.Client;
import sopra.vol.model.Entreprise;
import sopra.vol.model.Particulier;
import sopra.vol.model.StatutJuridique;

public class TestDaoTristan {

	public static void main(String[] args) {
		IClientDao clientDao = Application.getInstance().getClientDao();
		IAdresseDao adresseDao = Application.getInstance().getAdresseDao();
		
//		Particulier p = new Particulier();
//		p.setNom("Seif");
//		p.setPrenom("BOULKROUN");
//		
//		clientDao.create(p);
//		
//		Entreprise e = new Entreprise();
//		e.setNom("Sopra Steria");
//		e.setSiret("326 820 065 00083");
//		e.setNumeroTVA("FR18326820065");
//		e.setStatutJuridique(StatutJuridique.SA);
//		
//		clientDao.create(e);
//		
//		List<Client> clients = clientDao.findAll();
//		
//		System.out.println(clients.size());
//		
//		for(Client client : clients) {
//			System.out.println(client);
//		}
//		
//		Client client = clientDao.findById(2L);
//		System.out.println(client);
//		
//		clientDao.deleteById(1L);
//		clientDao.deleteById(2L);
//		
//		clients = clientDao.findAll();
//		
//		System.out.println(clients.size());
		
		Adresse a1 = new Adresse();
		a1.setRue("9 rue Georges Bonnac");
		a1.setComplement("3ème étage");
		a1.setCodePostal("33000");
		a1.setVille("Bordeaux");
		a1.setPays("France");
		

		Adresse a2 = new Adresse();
		a2.setRue("21th street");
		a2.setComplement("2nd floor");
		a2.setCodePostal("12333");
		a2.setVille("New-York");
		a2.setPays("Etats-Unis");
		
		adresseDao.create(a1);
		adresseDao.create(a2);
		
		List<Adresse> adresses = adresseDao.findAll();
		
		System.out.println(adresses.size());
		
		a2.setVille("Manchester");
		
		adresseDao.update(a2);
		
		Adresse a3 = adresseDao.findById(1L);
		
		System.out.println(a3.getVille());
		
		adresseDao.delete(a1);
		adresseDao.delete(a2);
		
	}

}
