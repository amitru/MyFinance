package com.amitru.mystockalert.service;

import java.util.List;
import java.util.Map;

import com.amitru.mystockalert.beans.WatchListStock;

public interface MyStockServiceI {

	public Map<String,String> fetchCurrentPriceForMultipleStocks(List<WatchListStock> stock) throws Exception;
	
	public void findStocksForEmailAlert(Map<String,String> stockPriceMap,List<WatchListStock> wList) throws Exception;
	
	//public void triggerEmailForWatchList() throws Exception;
}
