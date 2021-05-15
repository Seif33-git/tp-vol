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
import sopra.vol.dao.IAeroportDao;
import sopra.vol.dao.IVolDao;
import sopra.vol.model.Aeroport;
import sopra.vol.model.Billet;
import sopra.vol.model.CompagnieAerienneVol;
import sopra.vol.model.StatutVol;
import sopra.vol.model.Vol;

public class VolDaoJdbc implements IVolDao {
	
	private List<Billet> BilletById(Long id){
		List<Billet> l_billet = new ArrayList<Billet>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT ID FROM billet WHERE VOL_ID = ?;");
			ps.setLong(1, id);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Billet b = Application.getInstance().getBilletDao().findById(rs.getLong("ID"));
				l_billet.add(b);
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
		return l_billet;
	}
	
	private List<CompagnieAerienneVol> CompagnieAerienneVolById(Long id){
		List<CompagnieAerienneVol> l_comp_aer_vol = new ArrayList<CompagnieAerienneVol>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT ID FROM compagnie_aerienne_vol WHERE VOL_ID = ?;");
			ps.setLong(1, id);
			rs = ps.executeQuery();
		
		while (rs.next()) {
			CompagnieAerienneVol c = Application.getInstance().getCompagnieAerienneVolDao().findById(rs.getLong("ID"));		
			l_comp_aer_vol.add(c);
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
		return l_comp_aer_vol;
	}
	
	@Override
	public List<Vol> findAll() {
		List<Vol> vols = new ArrayList<Vol>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT id, dt_depart, dt_arrivee, statut_vol, depart_code, arrivee_code, nb_place_dispo FROM vol;");

			rs = ps.executeQuery();
			
			while (rs.next()) {
				Long id = rs.getLong("id");
				Date dt_depart = rs.getDate("dt_depart");
				Date dt_arrivee = rs.getDate("dt_arrivee");
				String statut_vol = rs.getString("statut_vol");
				String depart_code = rs.getString("depart_code");
				String arrivee_code = rs.getString("arrivee_code");
				Integer nb_place_dispo = rs.getInt("nb_place_dispo");
			
				Vol vol = new Vol(id, StatutVol.valueOf(statut_vol), dt_depart, dt_arrivee, nb_place_dispo);
				
				if (depart_code != null && arrivee_code != null) {
					IAeroportDao aeroDao = Application.getInstance().getAeroportDao();
					Aeroport aeroDepart = aeroDao.findById(depart_code);
					Aeroport aeroArrivee = aeroDao.findById(arrivee_code);
					vol.setDepart(aeroDepart);
					vol.setArrivee(aeroArrivee);
				}
				
				vol.setBillets(BilletById(id));
				vol.setCompagnieAeriennes(CompagnieAerienneVolById(id));
				
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
			ps = conn.prepareStatement("SELECT dt_depart, dt_arrivee, statut_vol, depart_code, arrivee_code, nb_place_dispo FROM vol WHERE id = ?;");

			ps.setLong(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				Date dt_depart = rs.getDate("dt_depart");
				Date dt_arrivee = rs.getDate("dt_arrivee");
				String statut_vol = rs.getString("statut_vol");
				String depart_code = rs.getString("depart_code");
				String arrivee_code = rs.getString("arrivee_code");
				Integer nb_place_dispo = rs.getInt("nb_place_dispo");

				vol = new Vol(id, StatutVol.valueOf(statut_vol), dt_depart, dt_arrivee, nb_place_dispo);
				if (depart_code != null && arrivee_code != null) {
					IAeroportDao aeroDao = Application.getInstance().getAeroportDao();
					Aeroport aeroDepart = aeroDao.findById(depart_code);
					Aeroport aeroArrivee = aeroDao.findById(arrivee_code);
					vol.setDepart(aeroDepart);
					vol.setArrivee(aeroArrivee);
				}
				
				vol.setBillets(BilletById(id));
				vol.setCompagnieAeriennes(CompagnieAerienneVolById(id));
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
		Long id_true = -1L;

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
				ps.setString(4, vol.getDepart().getCode());
			} else {
				ps.setNull(4, Types.VARCHAR);
			}
			
			if (vol.getArrivee() != null) {
				ps.setString(5, vol.getArrivee().getCode());
			} else {
				ps.setNull(5, Types.VARCHAR);
			}
			
			ps.setInt(6, vol.getNbPlaceDispo());

			int rows = ps.executeUpdate();

			if (rows == 1) {
				ResultSet keys = ps.getGeneratedKeys();

				if (keys.next()) {
					id_true = keys.getLong(1);
					vol.setId(id_true);
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
		
		// Peut-être pas nécessaire selon l'utilisation de l'API
		for (Billet b : vol.getBillets()) {
			Application.getInstance().getBilletDao().create(b);
		}
		
		for (CompagnieAerienneVol c : vol.getCompagnieAeriennes()) {
			Application.getInstance().getCompagnieAerienneVolDao().create(c);
		}
		
	}

	@Override
	public void update(Vol vol) { // Les billets et compagnies ne seront pas modifiés suite à la non définition du comportement de la fonction dans les specs non existantes
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
			
			if (vol.getDepart() != null) {
				ps.setString(4, vol.getDepart().getCode());
			} else {
				ps.setNull(4, Types.DATE);
			}
			
			if (vol.getArrivee() != null) {
				ps.setString(5, vol.getArrivee().getCode());
			} else {
				ps.setNull(5, Types.DATE);
			}
			
			ps.setInt(6, vol.getNbPlaceDispo());
			ps.setLong(7, vol.getId());

			ps.executeUpdate();

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
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		
		try {
			conn = Application.getInstance().getConnection();
			
			try {
				ps2 = conn.prepareStatement("SELECT ID FROM billet WHERE VOL_ID = ?;");
				ps2.setLong(1, id);
				rs2 = ps2.executeQuery();
				
				while (rs2.next()) {
					Application.getInstance().getBilletDao().deleteById(rs2.getLong("ID"));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					rs2.close();
					ps2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			
			try {
				ps3 = conn.prepareStatement("SELECT ID FROM compagnie_aerienne_vol WHERE VOL_ID = ?;");
				ps3.setLong(1, id);
				rs3 = ps3.executeQuery();
			
				while (rs3.next()) {
					Application.getInstance().getCompagnieAerienneVolDao().deleteById(rs3.getLong("ID"));		
				}
			
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					rs3.close();
					ps3.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			ps = conn.prepareStatement("DELETE FROM vol WHERE id = ?");

			ps.setLong(1, id);
			
			ps.executeUpdate();

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
