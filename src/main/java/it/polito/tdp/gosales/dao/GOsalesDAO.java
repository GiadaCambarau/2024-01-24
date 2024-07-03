package it.polito.tdp.gosales.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.gosales.model.DailySale;
import it.polito.tdp.gosales.model.Methods;
import it.polito.tdp.gosales.model.Products;
import it.polito.tdp.gosales.model.Retailers;
import it.polito.tdp.gosales.model.Ricavo;

public class GOsalesDAO {
	
	
	/**
	 * Metodo per leggere la lista di tutti i rivenditori dal database
	 * @return
	 */

	public List<Retailers> getAllRetailers(){
		String query = "SELECT * from go_retailers";
		List<Retailers> result = new ArrayList<Retailers>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Retailers(rs.getInt("Retailer_code"), 
						rs.getString("Retailer_name"),
						rs.getString("Type"), 
						rs.getString("Country")));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}
	
	
	/**
	 * Metodo per leggere la lista di tutti i prodotti dal database
	 * @return
	 */
	public List<Products> getAllProducts(){
		String query = "SELECT * from go_products";
		List<Products> result = new ArrayList<Products>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Products(rs.getInt("Product_number"), 
						rs.getString("Product_line"), 
						rs.getString("Product_type"), 
						rs.getString("Product"), 
						rs.getString("Product_brand"), 
						rs.getString("Product_color"),
						rs.getDouble("Unit_cost"), 
						rs.getDouble("Unit_price")));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}

	
	/**
	 * Metodo per leggere la lista di tutte le vendite nel database
	 * @return
	 */
	public List<DailySale> getAllSales(){
		String query = "SELECT * from go_daily_sales";
		List<DailySale> result = new ArrayList<DailySale>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new DailySale(rs.getInt("retailer_code"),
				rs.getInt("product_number"),
				rs.getInt("order_method_code"),
				rs.getTimestamp("date").toLocalDateTime().toLocalDate(),
				rs.getInt("quantity"),
				rs.getDouble("unit_price"),
				rs.getDouble("unit_sale_price")  ));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	public List<Methods> getMetodi(){
		String query = "SELECT  g.* "
				+ "FROM go_methods g";
		List<Methods> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Methods(rs.getInt("Order_method_code"), rs.getString("Order_method_type")));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		
		}
	}
	
	public List<Products> getProdotti(Methods m, int anno, Map<Integer, Products> mappa){
		String query = "SELECT  distinct g.Product_number as p "
				+ "FROM go_daily_sales g "
				+ "WHERE g.Order_method_code= ? AND YEAR(g.Date) =? ";
		List<Products> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, m.getCode());
			st.setInt(2, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(mappa.get(rs.getInt("p")));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	

	public List<Ricavo> getRicavo(int anno, Methods m, Map<Integer, Products> mappa){
		String sql = "SELECT g1.Product_number as p, SUM(g1.Quantity* g1.Unit_sale_price) AS r "
				+ "FROM go_daily_sales g1 "
				+ "WHERE  YEAR(g1.Date) =? AND g1.Order_method_code= ? "
				+ "GROUP BY g1.Product_number";
		List<Ricavo> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(2, m.getCode());
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Ricavo(mappa.get(rs.getInt("p")), rs.getDouble("r")));
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
	

	
	
}
