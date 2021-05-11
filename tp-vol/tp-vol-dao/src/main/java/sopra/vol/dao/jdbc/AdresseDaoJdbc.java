package sopra.vol.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sopra.vol.Application;
import sopra.vol.model.Evaluation;
import sopra.vol.dao.IAdresseDao;
import sopra.vol.model.Adresse;

public class AdresseDaoJdbc implements IAdresseDao{

	@Override
	public List<Adresse> findAll() {
		List<Adresse> adresses = new ArrayList<Adresse>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT id, comportementale, technique, commentaires FROM evaluation");
			rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong("id");
				Integer comportementale = rs.getInt("comportementale");
				Integer technique = rs.getInt("technique");
				String commentaires = rs.getString("commentaires");

				Adresse evaluation = new Adresse();

				adresses.add(evaluation);
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
		
		return adresses;
	}

	@Override
	public Adresse findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(Adresse obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Adresse obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Adresse obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

}
