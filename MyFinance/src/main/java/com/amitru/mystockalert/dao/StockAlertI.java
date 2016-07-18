package com.amitru.mystockalert.dao;

import java.util.List;

import com.amitru.mystockalert.beans.WatchListStock;

public interface StockAlertI {
	
	public List<WatchListStock> fetchStockDetails();
	
	public void updateStockDetails();
	
	
}
