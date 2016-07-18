package com.amitru.mystockalert.interfaces;

import java.util.List;

import com.amitru.mystockalert.beans.Stock;
import com.amitru.mystockalert.beans.WatchListStock;

public interface MyStockAlertI {
	
	//This can be XLS or DB or any source
	public void readDataFromSource() throws Exception;
	
	//Call API for getting share details
	public void fetchWatchListShareDetails() throws Exception;

	//public MyFoiloStock fetchMyFolioShareDetails(Stock stock);
	
	public void triggerEmailForWatchList();
}
