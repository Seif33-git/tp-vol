package sopra.vol.dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import sopra.vol.Application;
import sopra.vol.dao.IPassagerDao;
import sopra.vol.dao.IReservationDao;
import sopra.vol.model.Passager;
import sopra.vol.model.Entreprise;
import sopra.vol.model.Particulier;
import sopra.vol.model.Reservation;
import sopra.vol.model.StatutJuridique;
import sopra.vol.model.TypeIdentite;

public class PassagerDaoJdbc implements IPassagerDao{

	@Override
	public List<Passager> findAll() {
		List<Passager> passagers = new ArrayList<Passager>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT id, nom, prenom, numeroIdentite, typeIdentite, reservation_id FROM passager;");

			rs = ps.executeQuery();
			
			while (rs.next()) {
				Long id = rs.getLong("id");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String numeroIdentite = rs.getString("numeroIdentite");
				TypeIdentite typeIdentite = null;
				if(rs.getString("statut_juridique") != null ) {
					typeIdentite = TypeIdentite.valueOf(rs.getString("typeIdentite"));
				}
				Integer reservation_id = rs.getInt("reservation_id");
				
				IReservationDao resaDao = Application.getInstance().getReservationDao();
				Reservation reservation = resaDao.findById(reservation_id);
				
				Passager passager = new Passager(id, nom, prenom, numeroIdentite, typeIdentite);
				passager.setReservation(reservation);
				
				passagers.add(passager);
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return passagers;
	}

	@Override
	public Passager findById(Long id) {
		Passager passager = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT nom, prenom, numeroIdentite, typeIdentite, reservation_id FROM passager WHERE id = ?");
			ps.setLong(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String numeroIdentite = rs.getString("numeroIdentite");
				TypeIdentite typeIdentite = null;
				if(rs.getString("statut_juridique") != null ) {
					typeIdentite = TypeIdentite.valueOf(rs.getString("typeIdentite"));
				}
				Integer reservation_id = rs.getInt("reservation_id");
				
				IReservationDao resaDao = Application.getInstance().getReservationDao();
				Reservation reservation = resaDao.findById(reservation_id);
				
				passager = new Passager(id, nom, prenom, numeroIdentite, typeIdentite);
				passager.setReservation(reservation);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return passager;
	}

	@Override
	public void create(Passager obj) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("INSERT INTO passager (nom, prenom, numeroIdentite, typeIdentite, reservation_id) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			
			

			ps.setString(1, obj.getNom());
			ps.setString(2, obj.getPrenom());
			ps.setString(3, obj.getNumeroIdentite());
			ps.setString(4, obj.getTypeIdentite().toString());
			if (obj.getReservation() != null) {
				ps.setLong(5, obj.getReservation().getNumero());
			} else {
				ps.setNull(5, Types.VARCHAR);
			}
			
			int rows = ps.executeUpdate();
			
			if(rows > 0) {
				rs = ps.getGeneratedKeys();
				if(rs.next()) {
					Long id = rs.getLong(1);
					obj.setId(id);
				}
			}
		

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void update(Passager obj) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("UPDATE passager SET nom = ?, prenom = ?, numeroIdentite = ?, typeIdentite = ?, reservation_id = ? WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
			
			

			ps.setString(1, obj.getNom());
			ps.setString(2, obj.getPrenom());
			ps.setString(3, obj.getNumeroIdentite());
			ps.setString(4, obj.getTypeIdentite().toString());
			if (obj.getReservation() != null) {
				ps.setLong(5, obj.getReservation().getNumero());
			} else {
				ps.setNull(5, Types.VARCHAR);
			}
			
			int rows = ps.executeUpdate();
			
			if(rows > 0) {
				rs = ps.getGeneratedKeys();
				if(rs.next()) {
					Long id = rs.getLong(1);
					obj.setId(id);
				}
			}
		

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void delete(Passager obj) {
		deleteById(obj.getId());
		
	}

	@Override
	public void deleteById(Long id) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("DELETE FROM passager WHERE id= ?");
			
			ps.setLong(1, id);
			
			int rows = ps.executeUpdate();
			
			if(rows != 1) {
				// TODO renvoyer une exception
			}
		

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
		
	

}
