package sopra.vol.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import sopra.vol.Application;
import sopra.vol.dao.IAeroportDao;
import sopra.vol.dao.ICompagnieAerienneDao;
import sopra.vol.dao.IVilleDao;
import sopra.vol.model.Aeroport;
import sopra.vol.model.CompagnieAerienne;
import sopra.vol.model.Ville;

public class TestDaoSeif {
	
	public static void main(String[] args) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		IVilleDao villeDao = Application.getInstance().getVilleDao();
		IAeroportDao aeroportDao = Application.getInstance().getAeroportDao();
		ICompagnieAerienneDao compagnieAerienneDao = Application.getInstance().getCompagnieAerienneDao();
		
		
//		Ville ville1 = new Ville("Bamako");
//		villeDao.create(ville1);	
//		
//		Ville ville2 = new Ville("Toulon");
//		villeDao.create(ville2);
//		
//		ville1.setNom("Sydney");
//		villeDao.update(ville1);
		
		
//		Aeroport a1 = new Aeroport("555", "CDG");
//		aeroportDao.create(a1);
//		
//		Aeroport a2 = new Aeroport("666", "Haneda");
//		aeroportDao.create(a2);
//		
//		a1.setNom("Marignane");
//		aeroportDao.update(a1);
		
//		CompagnieAerienne ca1 = new CompagnieAerienne("101010", "Air France");
//		compagnieAerienneDao.create(ca1);
		
//		CompagnieAerienne ca2 = new CompagnieAerienne("8989898", "British Airways");
//		compagnieAerienneDao.create(ca2);
		
//		ca1.setCode("200000");
//		ca1.setNom("Air Maroc");
//		compagnieAerienneDao.update(ca1);
		
	}

}
