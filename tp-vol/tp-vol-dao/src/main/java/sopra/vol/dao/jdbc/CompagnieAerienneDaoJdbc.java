package sopra.vol.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sopra.vol.Application;
import sopra.vol.dao.ICompagnieAerienneDao;
import sopra.vol.model.Aeroport;
import sopra.vol.model.CompagnieAerienne;

public class CompagnieAerienneDaoJdbc implements ICompagnieAerienneDao {

	@Override
	public List<CompagnieAerienne> findAll() {
		List<CompagnieAerienne> compagniesAeriennes = new ArrayList<CompagnieAerienne>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT code, nom FROM compagnie_aerienne");

			rs = ps.executeQuery();

			while (rs.next()) {
				String code = rs.getString("code");
				String nom = rs.getString("nom");

				CompagnieAerienne compagnieAerienne = new CompagnieAerienne(code, nom);

				compagniesAeriennes.add(compagnieAerienne);
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

		return compagniesAeriennes;
	}

	@Override
	public CompagnieAerienne findById(String id) {
		CompagnieAerienne compagnieAerienne = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT nom FROM compagnie_aerienne WHERE code = ?");

			ps.setString(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				String nom = rs.getString("nom");

				compagnieAerienne = new CompagnieAerienne(id, nom);
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

		return compagnieAerienne;
	}

	@Override
	public void create(CompagnieAerienne obj) {
		
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("INSERT INTO compagnie_aerienne (code,nom) VALUES (?,?)");

			ps.setString(1, obj.getCode());
			ps.setString(2, obj.getNom());

			int rows = ps.executeUpdate();


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
	public void update(CompagnieAerienne obj) {
		
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("UPDATE compagnie_aerienne SET nom = ? WHERE code = ?");

			ps.setString(1, obj.getNom());
			ps.setString(2, obj.getCode());
			

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
	public void delete(CompagnieAerienne obj) {
		
		deleteById(obj.getCode());
		
	}

	@Override
	public void deleteById(String id) {
		
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("DELETE FROM compagnie_aerienne WHERE code = ?");

			ps.setString(1, id);

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
