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
import sopra.vol.dao.IClientDao;
import sopra.vol.model.Client;
import sopra.vol.model.Entreprise;
import sopra.vol.model.Particulier;
import sopra.vol.model.StatutJuridique;

public class ClientDaoJdbc implements IClientDao {

	@Override
	public List<Client> findAll() {
		List<Client> clients = new ArrayList<Client>();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT id, type, nom, prenom, siret, numero_tva, statut_juridique FROM client");
			rs = ps.executeQuery();

			while (rs.next()) {
				Long id = rs.getLong("id");
				String type = rs.getString("type");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String siret = rs.getString("siret");
				String numeroTva = rs.getString("numero_tva");
				
				StatutJuridique statutJuridique = null;
				if(rs.getString("statut_juridique") != null ) {
					statutJuridique = StatutJuridique.valueOf(rs.getString("statut_juridique"));
				}
				
				Particulier particulier = null;
				Entreprise entreprise = null;
				
				System.out.println(type);
				
				if(type.equals("p")) {
					particulier = new Particulier();
					particulier.setId(id);
					particulier.setNom(nom);
					particulier.setPrenom(prenom);
					clients.add(particulier);
				}  
				
				if (type.equals("e")) {
					entreprise = new Entreprise();
					entreprise.setId(id);
					entreprise.setNom(nom);
					entreprise.setSiret(siret);
					entreprise.setNumeroTVA(numeroTva);
					entreprise.setStatutJuridique(statutJuridique);
					clients.add(entreprise);
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
		
		return clients;
	}

	@Override
	public Client findById(Long id) {
		Client client = null;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("SELECT id, type, nom, prenom, siret, numero_tva, statut_juridique FROM client WHERE id = ?");
			ps.setLong(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				String type = rs.getString("type");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String siret = rs.getString("siret");
				String numeroTva = rs.getString("numero_tva");
				
				StatutJuridique statutJuridique = null;
				if(rs.getString("statut_juridique") != null ) {
					statutJuridique = StatutJuridique.valueOf(rs.getString("statut_juridique"));
				}
				
				Particulier particulier = null;
				Entreprise entreprise = null;
				if(type.equals("p")) {
					particulier = new Particulier();
					particulier.setId(id);
					particulier.setNom(nom);
					particulier.setPrenom(prenom);
					client = particulier;
				}
				
				if (type.equals("e")) {
					entreprise = new Entreprise();
					entreprise.setId(id);
					entreprise.setNom(nom);
					entreprise.setSiret(siret);
					entreprise.setNumeroTVA(numeroTva);
					entreprise.setStatutJuridique(statutJuridique);
					client = entreprise;
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
		
		return client;
	}

	@Override
	public void create(Client obj) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("INSERT INTO client (type, nom, prenom, siret, numero_tva, statut_juridique) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			
			if(obj instanceof Particulier) {
				ps.setString(1, "p");
				ps.setString(2, obj.getNom());
				ps.setString(3, ((Particulier)obj).getPrenom());
				ps.setNull(4, Types.VARCHAR);
				ps.setNull(5, Types.VARCHAR);
				ps.setNull(6, Types.VARCHAR);
			}
			
			if(obj instanceof Entreprise) {
				ps.setString(1, "e");
				ps.setString(2, obj.getNom());
				ps.setNull(3, Types.VARCHAR);
				ps.setString(4, ((Entreprise)obj).getSiret());
				ps.setString(5, ((Entreprise)obj).getNumeroTVA());
				ps.setString(6, ((Entreprise)obj).getStatutJuridique().toString());
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
	public void update(Client obj) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("UPDATE client SET type = ?, nom = ?, prenom = ?, siret = ?, numero_tva = ?, statut_juridique = ? WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
			
			if(obj instanceof Particulier) {
				ps.setString(1, "p");
				ps.setString(2, obj.getNom());
				ps.setString(3, ((Particulier)obj).getPrenom());
				ps.setNull(4, Types.VARCHAR);
				ps.setNull(5, Types.VARCHAR);
				ps.setNull(6, Types.VARCHAR);
			}
			
			if(obj instanceof Entreprise) {
				ps.setString(1, "e");
				ps.setString(2, obj.getNom());
				ps.setNull(3, Types.VARCHAR);
				ps.setString(4, ((Entreprise)obj).getSiret());
				ps.setString(5, ((Entreprise)obj).getNumeroTVA());
				ps.setString(6, ((Entreprise)obj).getStatutJuridique().toString());
			}
			
			ps.setLong(7, obj.getId());
			
			int rows = ps.executeUpdate();
			
			if(rows > 0) {
				
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
	public void delete(Client obj) {
		deleteById(obj.getId());
	}

	@Override
	public void deleteById(Long id) {
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = Application.getInstance().getConnection();
			ps = conn.prepareStatement("DELETE FROM client WHERE id= ?");
			
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
