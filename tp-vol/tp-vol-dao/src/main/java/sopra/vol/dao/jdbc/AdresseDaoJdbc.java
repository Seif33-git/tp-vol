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
import sopra.vol.dao.IAdresseDao;
import sopra.vol.model.Adresse;
import sopra.vol.model.Client;

public class AdresseDaoJdbc implements IAdresseDao{

	@Override
	public List<Adresse> findAll() {
		List<Adresse> adresses = new ArrayList<Adresse>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT id, rue, complement, code_postal, ville, pays, client_id  FROM adresse");
			rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong("id");
				String rue = rs.getString("rue");
				String complement = rs.getString("complement");
				String code_postal = rs.getString("code_postal");
				String ville = rs.getString("ville");
				String pays = rs.getString("pays");
				Long clientId = rs.getLong("client_id");

				Adresse adresse = new Adresse();
				adresse.setId(id);
				adresse.setRue(rue);
				adresse.setComplement(complement);
				adresse.setCodePostal(code_postal);
				adresse.setVille(ville);
				adresse.setPays(pays);
				
				if(clientId != null) {
					Client client = Application.getInstance().getClientDao().findById(clientId);
					adresse.setClient(client);
				}

				adresses.add(adresse);
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
		Adresse adresse = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT rue, complement, code_postal, ville, pays, client_id  FROM adresse WHERE id = ?");
			ps.setLong(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				String rue = rs.getString("rue");
				String complement = rs.getString("complement");
				String code_postal = rs.getString("code_postal");
				String ville = rs.getString("ville");
				String pays = rs.getString("pays");
				Long clientId = rs.getLong("client_id");

				adresse = new Adresse();
				adresse.setId(id);
				adresse.setRue(rue);
				adresse.setComplement(complement);
				adresse.setCodePostal(code_postal);
				adresse.setVille(ville);
				adresse.setPays(pays);
				
				if(clientId != null) {
					Client client = Application.getInstance().getClientDao().findById(clientId);
					adresse.setClient(client);
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
		
		return adresse;
	}

	@Override
	public void create(Adresse obj) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("INSERT INTO adresse (rue, complement, code_postal, ville, pays, client_id) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, obj.getRue());
			ps.setString(2, obj.getComplement());
			ps.setString(3, obj.getCodePostal()); 
			ps.setString(4, obj.getVille());
			ps.setString(5, obj.getPays());

			if(obj.getClient() != null && obj.getClient().getId() != null) {
				System.out.println("Hello bitcj");
				ps.setLong(6, obj.getClient().getId());
			} else {
				ps.setNull(6, Types.INTEGER);
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
	public void update(Adresse obj) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("UPDATE adresse SET rue = ? , complement = ? , code_postal = ? , ville = ? , pays = ?, client_id = ? WHERE id = ?");
			
			ps.setString(1, obj.getRue());
			ps.setString(2, obj.getComplement());
			ps.setString(3, obj.getCodePostal());
			ps.setString(4, obj.getVille());
			ps.setString(5, obj.getPays());
			
			if(obj.getClient() != null && obj.getClient().getId() != null) {
				ps.setLong(6, obj.getClient().getId());
			} else {
				ps.setNull(6, Types.INTEGER);
			}
			
			ps.setLong(7, obj.getId());
			
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

	@Override
	public void delete(Adresse obj) {
		deleteById(obj.getId());
	}

	@Override
	public void deleteById(Long id) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("DELETE FROM adresse WHERE id = ?");
			
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
