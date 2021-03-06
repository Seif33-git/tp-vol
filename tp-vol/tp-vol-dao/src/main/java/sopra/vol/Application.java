package sopra.vol;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import sopra.vol.dao.IAdresseDao;
import sopra.vol.dao.IAeroportDao;
import sopra.vol.dao.IBilletDao;
import sopra.vol.dao.IClientDao;
import sopra.vol.dao.ICompagnieAerienneDao;
import sopra.vol.dao.ICompagnieAerienneVolDao;
import sopra.vol.dao.IReservationDao;
import sopra.vol.dao.IVilleDao;
import sopra.vol.dao.jdbc.AdresseDaoJdbc;
import sopra.vol.dao.IVolDao;
import sopra.vol.dao.jdbc.AeroportDaoJdbc;
import sopra.vol.dao.jdbc.BilletDaoJdbc;
import sopra.vol.dao.jdbc.CompagnieAerienneDaoJdbc;
import sopra.vol.dao.jdbc.CompagnieAerienneVolDaoJdbc;
import sopra.vol.dao.jdbc.ClientDaoJdbc;
import sopra.vol.dao.jdbc.ReservationDaoJdbc;
import sopra.vol.dao.jdbc.VilleDaoJdbc;
import sopra.vol.dao.jdbc.VolDaoJdbc;

public class Application {
	private static Application instance = null;
	
	
	private final IVilleDao villeDao = new VilleDaoJdbc();
	private final IAeroportDao aeroportDao = new AeroportDaoJdbc();
	private final IVolDao volDao = new VolDaoJdbc();
	private final IReservationDao reservationDao = new ReservationDaoJdbc();
	private final IClientDao clientDao = new ClientDaoJdbc();
	private final IAdresseDao adresseDao = new AdresseDaoJdbc();
	private final ICompagnieAerienneDao compagnieAerienneDao = new CompagnieAerienneDaoJdbc();
	private final IBilletDao billetDao = new BilletDaoJdbc();
	private final ICompagnieAerienneVolDao compagnieAerienneVolDao = new CompagnieAerienneVolDaoJdbc();

	private final String jdbcUrl = "jdbc:mysql://localhost:3306/tp_vol";
	private final String username = "root";
	private final String password = "admin";

	private Application() {
		super();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Application getInstance() {
		if (instance == null) {
			instance = new Application();
		}

		return instance;
	}
	
	public IVilleDao getVilleDao() {
		return villeDao;
	}
	
	public IVolDao getVolDao() {
		return volDao;
	}
	
	public IClientDao getClientDao() {
		return clientDao;
	}
	
	public IAdresseDao getAdresseDao() {
		return adresseDao;
	}
	
	public IAeroportDao getAeroportDao() {
		return aeroportDao;
	}
	
	public ICompagnieAerienneDao getCompagnieAerienneDao() {
		return compagnieAerienneDao;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(jdbcUrl, username, password);
	}

	public IReservationDao getReservationDao() {
		return reservationDao;
	}
	
	public IBilletDao getBilletDao() {
		return billetDao;
	}
	
	public ICompagnieAerienneVolDao getCompagnieAerienneVolDao() {
		return compagnieAerienneVolDao;
	}
}
