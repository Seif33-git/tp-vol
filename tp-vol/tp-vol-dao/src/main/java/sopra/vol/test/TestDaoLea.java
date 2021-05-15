package sopra.vol.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import sopra.vol.Application;
import sopra.vol.dao.IBilletDao;
import sopra.vol.dao.ICompagnieAerienneVolDao;
import sopra.vol.dao.IVolDao;
import sopra.vol.model.Billet;
import sopra.vol.model.CompagnieAerienneVol;
import sopra.vol.model.StatutVol;
import sopra.vol.model.Vol;

public class TestDaoLea {

	public static void main(String[] args) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		IVolDao volDao = Application.getInstance().getVolDao();
		IBilletDao billetDao = Application.getInstance().getBilletDao();
		ICompagnieAerienneVolDao compVolDao = Application.getInstance().getCompagnieAerienneVolDao();
//		IAeroportDao aeroDao = Application.getInstance().getAeroportDao();
//		
//		Aeroport aeroD = new Aeroport("CDG", "Charles de Gaulle");
//		Aeroport aeroA = new Aeroport("GAT", "Gatwick");
//		
//		aeroDao.create(aeroD);
//		aeroDao.create(aeroA);
		
//		Billet b1 = new Billet("B14", "Eco", 101f, 1254);
//		Billet b2 = new Billet("F5", "Premiere", 43f, 4478);
		
//		billetDao.create(b1);
//		billetDao.create(b2);
		
		CompagnieAerienneVol c1 = new CompagnieAerienneVol("AF214");
		CompagnieAerienneVol c2 = new CompagnieAerienneVol("RY222");
		
		compVolDao.create(c1);
		compVolDao.create(c2);
		
		Vol vol = new Vol();
		//vol.setId((long)123);
		vol.setNbPlaceDispo(150);
		vol.setStatutVol(StatutVol.OUVERT);
		vol.setDtDepart(sdf.parse("15/06/2021"));
		vol.setDtArrivee(sdf.parse("16/06/2021"));
//		vol.setDepart(aeroD);
//		vol.setArrivee(aeroA);
//		vol.getBillets().add(b1);
//		vol.getBillets().add(b2);
		vol.getCompagnieAeriennes().add(c1);
		vol.getCompagnieAeriennes().add(c2);
		
		volDao.create(vol);
		
		vol.setNbPlaceDispo(200);
		
		System.out.println("AA " + vol.toString());
		
		volDao.update(vol);
		
		Vol volFind = volDao.findById(vol.getId());
		System.out.println("CC " + volFind.toString());
		//volDao.delete(vol);
		
//		Vol vol2 = new Vol(StatutVol.FERME, sdf.parse("22/10/2022"), sdf.parse("23/10/2022"), 50);
//		volDao.create(vol2);
		
		List<Vol> listVol = volDao.findAll();
		
		for(Vol volb : listVol) {
			System.out.println("EE " + volb.toString());
		}
		
		volDao.delete(vol);
			
	}

}
