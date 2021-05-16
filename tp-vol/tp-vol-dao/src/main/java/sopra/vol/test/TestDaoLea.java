package sopra.vol.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sopra.vol.Application;
import sopra.vol.dao.IAeroportDao;
import sopra.vol.dao.IVolDao;
import sopra.vol.model.Aeroport;
import sopra.vol.model.StatutVol;
import sopra.vol.model.Vol;

public class TestDaoLea {

	public static void main(String[] args) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		IVolDao volDao = Application.getInstance().getVolDao();
//		IAeroportDao aeroDao = Application.getInstance().getAeroportDao();
//		
//		Aeroport aeroD = new Aeroport("CDG", "Charles de Gaulle");
//		Aeroport aeroA = new Aeroport("GAT", "Gatwick");
//		
//		aeroDao.create(aeroD);
//		aeroDao.create(aeroA);
		
		
		Vol vol = new Vol();
		//vol.setId((long)123);
		vol.setNbPlaceDispo(150);
		vol.setStatutVol(StatutVol.OUVERT);
		vol.setDtDepart(sdf.parse("15/06/2021"));
		vol.setDtArrivee(sdf.parse("16/06/2021"));
//		vol.setDepart(aeroD);
//		vol.setArrivee(aeroA);
		
		volDao.create(vol);
		
		vol.setNbPlaceDispo(200);
		
		volDao.update(vol);
		//volDao.delete(vol);
			
	}

}
