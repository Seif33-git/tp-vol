package sopra.vol.test;

import java.util.List;

import sopra.vol.Application;
import sopra.vol.dao.IClientDao;
import sopra.vol.model.Client;
import sopra.vol.model.Entreprise;
import sopra.vol.model.Particulier;
import sopra.vol.model.StatutJuridique;

public class TestDaoTristan {

	public static void main(String[] args) {
		IClientDao clientDao = Application.getInstance().getClientDao();
		
		Particulier p = new Particulier();
		p.setNom("Seif");
		p.setPrenom("BOULKROUN");
		
		clientDao.create(p);
		
		Entreprise e = new Entreprise();
		e.setNom("Sopra Steria");
		e.setSiret("326 820 065 00083");
		e.setNumeroTVA("FR18326820065");
		e.setStatutJuridique(StatutJuridique.SA);
		
		clientDao.create(e);
		
		List<Client> clients = clientDao.findAll();
		
		System.out.println(clients.size());
		
		for(Client client : clients) {
			System.out.println(client);
		}
		
		Client client = clientDao.findById(2L);
		System.out.println(client);
		
		clientDao.deleteById(1L);
		clientDao.deleteById(2L);
		
		clients = clientDao.findAll();
		
		System.out.println(clients.size());
	}

}
