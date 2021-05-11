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
			ps = conn.prepareStatement("SELECT numero, dtReservation, statut FROM reservation");

			rs = ps.executeQuery();

			while (rs.next()) {
				Integer numero = rs.getInt("id");
				Date dtReservation = rs.getDate("dtReservation");
				StatutReservation statut = StatutReservation.valueOf(rs.getString("statut"));

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
			ps = conn.prepareStatement("SELECT dtReservation, statut FROM reservation WHERE numero = ?");

			ps.setInt(1, numero);

			rs = ps.executeQuery();

			if (rs.next()) {
				Date dtReservation = rs.getDate("dtReservation");
				StatutReservation statut = StatutReservation.valueOf(rs.getString("statut"));

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

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("INSERT INTO reservation (dtReservation, statut) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

			
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

			if (rows == 1) {
				ResultSet keys = ps.getGeneratedKeys();

				if (keys.next()) {
					Integer numero = keys.getInt(1);
					obj.setNumero(numero);
				}
			} else {
				throw new SQLException("Insertion en échec");
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
			ps = conn.prepareStatement("UPDATE reservation SET dtReservation = ?, statut = ? WHERE id = ?");

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
			ps = conn.prepareStatement("DELETE FROM reservation WHERE numero = ?");

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
