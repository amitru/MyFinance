package com.amitru.mystockalert.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.amitru.mystockalert.beans.WatchListStock;


public class StockAlertDAO implements StockAlertI {

	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	List<WatchListStock> watchListStockList;
	
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void initIt() throws Exception {
	  System.out.println("Init method of StockAlertDAO");
	  //this.fetchStockDetails();
	}
	
	
	public List<WatchListStock> fetchStockDetails() {
		
		String sql = "SELECT script_code,script_type,desired_price_buy,desired_price_sell,"
				+ " email_sent_flag,email_target,comments FROM mystockalert_wishlist";
		
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			WatchListStock watchListStock = null;
			//List<WatchListStock> watchListStockList = new ArrayList<WatchListStock>();
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				watchListStock = new WatchListStock(
					rs.getString("script_code"),
					rs.getString("script_type"), 
					rs.getInt("desired_price_buy"),
					rs.getInt("desired_price_sell"),
					rs.getBoolean("email_sent_flag"),
					Arrays.asList(rs.getString("email_target").split("\\s*,\\s*")),
					rs.getString("comments")
				);
			
				watchListStockList.add(watchListStock);
			}
			rs.close();
			ps.close();
			
			return watchListStockList;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		System.out.println("fetchStockDetails() Exited, watchListStockList-" + watchListStockList.size());
		}
	}
	


	public void updateStockDetails() {
			// TODO Auto-generated method stub
			
		}

	public List<WatchListStock> getWatchListStockList() {
		return watchListStockList;
	}

	public void setWatchListStockList(List<WatchListStock> watchListStockList) {
		this.watchListStockList = watchListStockList;
	}

	public DataSource getDataSource() {
		return dataSource;
	}
	
	
	
}
