package sopra.vol.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sopra.vol.Application;
import sopra.vol.dao.IAeroportDao;
import sopra.vol.model.Aeroport;
import sopra.vol.model.Ville;

public class AeroportDaoJdbc implements IAeroportDao {

	@Override
	public List<Aeroport> findAll() {
		List<Aeroport> aeroports = new ArrayList<Aeroport>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT code, nom FROM aeroport");

			rs = ps.executeQuery();

			while (rs.next()) {
				String code = rs.getString("code");
				String nom = rs.getString("nom");

				Aeroport aeroport = new Aeroport(code, nom);

				aeroports.add(aeroport);
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

		return aeroports;
	}

	@Override
	public Aeroport findById(String id) {
		Aeroport aeroport = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT nom FROM aeroport WHERE id = ?");

			ps.setString(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				String nom = rs.getString("nom");

				aeroport = new Aeroport(id, nom);
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

		return aeroport;
	}

	@Override
	public void create(Aeroport obj) {
		
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("INSERT INTO aeroport (code,nom) VALUES (?,?)");

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
	public void update(Aeroport obj) {
		
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("UPDATE aeroport SET nom = ? WHERE code = ?");

			ps.setString(1, obj.getNom());
			ps.setString(2, obj.getCode());

			int rows = ps.executeUpdate();

			if (rows != 1) {
				throw new SQLException("Mise ?? jour en ??chec");
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
	public void delete(Aeroport obj) {
		
		deleteById(obj.getCode());
		
	}

	@Override
	public void deleteById(String code) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("DELETE FROM aeroport WHERE code = ?");

			ps.setString(1, code);

			int rows = ps.executeUpdate();

			if (rows != 1) {
				throw new SQLException("Suppression en ??chec");
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
