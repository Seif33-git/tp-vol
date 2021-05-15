package sopra.vol.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import sopra.vol.Application;
import sopra.vol.dao.ICompagnieAerienneVolDao;
import sopra.vol.model.CompagnieAerienne;
import sopra.vol.model.CompagnieAerienneVol;
import sopra.vol.model.Vol;

public class CompagnieAerienneVolDaoJdbc implements ICompagnieAerienneVolDao{

	@Override
	public List<CompagnieAerienneVol> findAll() {
		List<CompagnieAerienneVol> comps = new ArrayList<CompagnieAerienneVol>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT ID, NUMERO_VOL, COMPAGNIE_AERIENNE_CODE, VOL_ID FROM compagnie_aerienne_vol;");

			rs = ps.executeQuery();
			
			while (rs.next()) {
				Long id = rs.getLong("ID");
				String num = rs.getString("NUMERO_VOL");
				String code = rs.getString("COMPAGNIE_AERIENNE_CODE");
				int vol_id = rs.getInt("VOL_ID");
			
				Vol vol = Application.getInstance().getVolDao().findById((long)vol_id);
				CompagnieAerienne c = Application.getInstance().getCompagnieAerienneDao().findById(code);
				
				CompagnieAerienneVol comp = new CompagnieAerienneVol(id, num);
				comp.setVol(vol);
				comp.setCompagnieAerienne(c);
				comps.add(comp);
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

		return comps;
	}

	@Override
	public CompagnieAerienneVol findById(Long id) {
		CompagnieAerienneVol comp = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT NUMERO_VOL, COMPAGNIE_AERIENNE_CODE, VOL_ID FROM compagnie_aerienne_vol WHERE ID=?;");
			ps.setLong(1, id);

			rs = ps.executeQuery();
			
			if (rs.next()) {
				String num = rs.getString("NUMERO_VOL");
				String code = rs.getString("COMPAGNIE_AERIENNE_CODE");
				int vol_id = rs.getInt("VOL_ID");
			
				Vol vol = Application.getInstance().getVolDao().findById((long)vol_id);
				CompagnieAerienne c = Application.getInstance().getCompagnieAerienneDao().findById(code);
				
				comp = new CompagnieAerienneVol(id, num);
				comp.setVol(vol);
				comp.setCompagnieAerienne(c);
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

		return comp;
	}

	@Override
	public void create(CompagnieAerienneVol comp) {
		Connection conn = null;
		PreparedStatement ps = null;
		Long id_true = -1L;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("INSERT INTO compagnie_aerienne_vol (NUMERO_VOL, COMPAGNIE_AERIENNE_CODE, VOL_ID) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);

			if (comp.getNumeroVol() != null) {
				ps.setString(1, comp.getNumeroVol());
			} else {
				ps.setNull(1, Types.DATE);
			}
				
			if (comp.getCompagnieAerienne() != null) {
				ps.setString(2, comp.getCompagnieAerienne().getCode());
			} else {
				ps.setNull(2, Types.DATE);
			}
			
			if (comp.getVol() != null) {
				ps.setLong(3, comp.getVol().getId());
			} else {
				ps.setNull(3, Types.VARCHAR);
			}

			int rows = ps.executeUpdate();

			if (rows == 1) {
				ResultSet keys = ps.getGeneratedKeys();

				if (keys.next()) {
					id_true = keys.getLong(1);
					comp.setId(id_true);
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
	public void update(CompagnieAerienneVol comp) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("UPDATE compagnie_aerienne_vol SET NUMERO_VOL=?, COMPAGNIE_AERIENNE_CODE=?, VOL_ID=? WHERE ID=?");

			if (comp.getNumeroVol() != null) {
				ps.setString(1, comp.getNumeroVol());
			} else {
				ps.setNull(1, Types.DATE);
			}
				
			if (comp.getCompagnieAerienne() != null) {
				ps.setString(2, comp.getCompagnieAerienne().getCode());
			} else {
				ps.setNull(2, Types.DATE);
			}
			
			if (comp.getVol() != null) {
				ps.setLong(3, comp.getVol().getId());
			} else {
				ps.setNull(3, Types.VARCHAR);
			}
			
			ps.setLong(4, comp.getId());

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
	public void delete(CompagnieAerienneVol comp) {
		deleteById(comp.getId());
	}

	@Override
	public void deleteById(Long id) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("DELETE FROM compagnie_aerienne_vol WHERE id = ?");
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
