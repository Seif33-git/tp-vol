package sopra.vol;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import sopra.vol.dao.IAeroportDao;
import sopra.vol.dao.IReservationDao;
import sopra.vol.dao.IVilleDao;
import sopra.vol.dao.jdbc.AeroportDaoJdbc;
import sopra.vol.dao.jdbc.ReservationDaoJdbc;
import sopra.vol.dao.jdbc.VilleDaoJdbc;

public class Application {
	private static Application instance = null;
	
	
	private final IVilleDao villeDao = new VilleDaoJdbc();
	private final IAeroportDao aeroportDao = new AeroportDaoJdbc();
	private final IReservationDao reservationDao = new ReservationDaoJdbc();

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

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(jdbcUrl, username, password);
	}

	public IAeroportDao getAeroportDao() {
		return aeroportDao;
	}

	public IReservationDao getReservationDao() {
		return reservationDao;
	}
	
	

}
