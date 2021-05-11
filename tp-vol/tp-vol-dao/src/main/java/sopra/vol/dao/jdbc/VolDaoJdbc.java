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
import sopra.vol.dao.IVolDao;
import sopra.vol.model.StatutVol;
import sopra.vol.model.Vol;

public class VolDaoJdbc implements IVolDao {

	@Override
	public List<Vol> findAll() {
		List<Vol> vols = new ArrayList<Vol>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT id, dt_depart, dt_arrivee, statut_vol, depart_code, arrivee_code, nb_place_dispo FROM vol");

			rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong("id");
				Date dt_depart = rs.getDate("dt_debut");
				Date dt_arrivee = rs.getDate("dt_arrivee");
				String statut_vol = rs.getString("statut_vol");
				String depart_code = rs.getString("depart_code");
				String arrivee_code = rs.getString("arrivee_code");
				Integer nb_place_dispo = rs.getInt("nb_place_dispo");
			
				Vol vol = new Vol(id, StatutVol.valueOf(statut_vol), dt_depart, dt_arrivee, nb_place_dispo);
				
				if (depart_code != null && arrivee_code != null) {
					// TODO
				}

				vols.add(vol);
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

		return vols;
	}
	

	@Override
	public Vol findById(Long id) {
		Vol vol = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT dt_depart, dt_arrivee, statut_vol, depart_code, arrivee_code, nb_place_dispo FROM vol WHERE id = ?");

			ps.setLong(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				Date dt_depart = rs.getDate("dt_debut");
				Date dt_arrivee = rs.getDate("dt_arrivee");
				String statut_vol = rs.getString("statut_vol");
				String depart_code = rs.getString("depart_code");
				String arrivee_code = rs.getString("arrivee_code");
				Integer nb_place_dispo = rs.getInt("nb_place_dispo");

				vol = new Vol(id, StatutVol.valueOf(statut_vol), dt_depart, dt_arrivee, nb_place_dispo);
				if (depart_code != null && arrivee_code != null) {
					// TODO
				}
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

		return vol;
	}

	@Override
	public void create(Vol vol) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("INSERT INTO vol (dt_depart, dt_arrivee, statut_vol, depart_code, arrivee_code, nb_place_dispo) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			if (vol.getDtDepart() != null) {
				ps.setDate(1, new java.sql.Date(vol.getDtDepart().getTime()));
			} else {
				ps.setNull(1, Types.DATE);
			}
				
			if (vol.getDtArrivee() != null) {
				ps.setDate(2, new java.sql.Date(vol.getDtArrivee().getTime()));
			} else {
				ps.setNull(2, Types.DATE);
			}
			
			ps.setString(3, vol.getStatutVol().toString());
			
			if (vol.getDepart() != null) {
				// TODO
			} else {
				ps.setNull(4, Types.DATE);
			}
			
			if (vol.getArrivee() != null) {
				// TODO
			} else {
				ps.setNull(5, Types.DATE);
			}
			
			ps.setInt(6, vol.getNbPlaceDispo());

			int rows = ps.executeUpdate();

			if (rows == 1) {
				ResultSet keys = ps.getGeneratedKeys();

				if (keys.next()) {
					Long id = keys.getLong(1);
					vol.setId(id);
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
	public void update(Vol vol) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("UPDATE vol SET dt_depart = ?, dt_arrivee = ?, statut_vol = ?, depart_code = ?, arrivee_code = ?, nb_place_dispo = ? WHERE id = ?");

			if (vol.getDtDepart() != null) {
				ps.setDate(1, new java.sql.Date(vol.getDtDepart().getTime()));
			} else {
				ps.setNull(1, Types.DATE);
			}
				
			if (vol.getDtArrivee() != null) {
				ps.setDate(2, new java.sql.Date(vol.getDtArrivee().getTime()));
			} else {
				ps.setNull(2, Types.DATE);
			}
			
			ps.setString(3, vol.getStatutVol().toString());
			
//			if (vol.getDepart() != null) {
//				// TODO
//			} else {
//				ps.setNull(4, Types.DATE);
//			}
//			
//			if (vol.getArrivee() != null) {
//				// TODO
//			} else {
//				ps.setNull(5, Types.DATE);
//			}
			
			ps.setInt(6, vol.getNbPlaceDispo());
			ps.setLong(7, vol.getId());

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
	public void delete(Vol vol) {
		deleteById(vol.getId());
	}

	@Override
	public void deleteById(Long id) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("DELETE FROM vol WHERE id = ?");

			ps.setLong(1, id);

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

