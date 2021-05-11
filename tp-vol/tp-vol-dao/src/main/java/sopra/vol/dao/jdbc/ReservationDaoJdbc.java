package sopra.vol.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sopra.vol.Application;
import sopra.vol.dao.IReservationDao;
import sopra.vol.model.Reservation;
import sopra.vol.model.StatutReservation;

public class ReservationDaoJdbc implements IReservationDao{

	@Override
	public List<Reservation> findAll() {
		List<Reservation> reservations = new ArrayList<Reservation>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT NUMERO, DT_RESERVATION, STATUT_RESERVATION FROM reservation");

			rs = ps.executeQuery();

			while (rs.next()) {
				Integer numero = rs.getInt("NUMERO");
				Date dtReservation = rs.getDate("DT_RESERVATION");
				StatutReservation statut = StatutReservation.valueOf(rs.getString("STATUT_RESERVATION"));

				Reservation reservation = new Reservation(numero, dtReservation, statut);

				reservations.add(reservation);
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

		return reservations;
	}

	@Override
	public Reservation findById(Integer numero) {
		Reservation reservation = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT DT_RESERVATION, STATUT_RESERVATION FROM reservation WHERE NUMERO = ?");

			ps.setInt(1, numero);

			rs = ps.executeQuery();

			if (rs.next()) {
				Date dtReservation = rs.getDate("DT_RESERVATION");
				StatutReservation statut = StatutReservation.valueOf(rs.getString("STATUT_RESERVATION"));

				reservation = new Reservation(numero, dtReservation, statut);
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

		return reservation;
	}

	@Override
	public void create(Reservation obj) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("INSERT INTO reservation (NUMERO, DT_RESERVATION, STATUT_RESERVATION) VALUES (?, ?, ?)",Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, obj.getNumero());
			if (obj.getDtReservation() != null) {
				ps.setDate(2, new java.sql.Date(obj.getDtReservation().getTime()));
			} else {
				ps.setNull(2, Types.DATE);
			}
			if (obj.getStatut() != null) {
				ps.setString(3, obj.getStatut().toString());
			} else {
				ps.setNull(3, Types.VARCHAR);
			}
			
			int rows = ps.executeUpdate();
			
			if(rows > 0) {
				rs = ps.getGeneratedKeys();
				if(rs.next()) {
					Integer numero = rs.getInt(1);
					obj.setNumero(numero);
				}
			}


		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Reservation obj) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("UPDATE reservation SET DT_RESERVATION = ?, STATUT_RESERVATION = ? WHERE NUMERO = ?");
			
			ps.setInt(3, obj.getNumero());
			if (obj.getDtReservation() != null) {
				ps.setDate(1, new java.sql.Date(obj.getDtReservation().getTime()));
			} else {
				ps.setNull(1, Types.DATE);
			}
			if (obj.getStatut() != null) {
				ps.setString(2, obj.getStatut().toString());
			} else {
				ps.setNull(2, Types.VARCHAR);
			}

			int rows = ps.executeUpdate();

			if (rows != 1) {
				throw new SQLException("Mise à jour en échec");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void delete(Reservation obj) {
		deleteById(obj.getNumero());
		
	}

	@Override
	public void deleteById(Integer numero) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("DELETE FROM reservation WHERE NUMERO = ?");

			ps.setLong(1, numero);

			int rows = ps.executeUpdate();

			if (rows != 1) {
				throw new SQLException("Suppression en échec");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
