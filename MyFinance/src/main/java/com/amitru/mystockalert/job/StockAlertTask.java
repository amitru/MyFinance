package com.amitru.mystockalert.job;

import java.util.List;

import com.amitru.mystockalert.beans.WatchListStock;
import com.amitru.mystockalert.dao.StockAlertDAO;
import com.amitru.mystockalert.main.StockAlertContextProvider;
import com.amitru.mystockalert.service.MyStockServiceImpl;
import com.amitru.mystockalert.util.MailUtil;

public class StockAlertTask {

	public void runTask() {
		
		System.out.println("runTask() Entered");
		
		try 
		{
			StockAlertContextProvider appContext = new StockAlertContextProvider();
			StockAlertDAO stockAlertDAO = (StockAlertDAO)appContext.getApplicationContext().getBean("StockAlertDAO", StockAlertDAO.class);
	    	List<WatchListStock> wList = stockAlertDAO.getWatchListStockList();
	        
	       //STEP 2: CALL WS AND FETCH SHARE DETAILS, CALL THIS METHOD ON EVERY 5 MINS OR SO
	        //MyStockServiceImpl stockAlertService = (MyStockServiceImpl) context.getBean("StockAlertService");
	    	MyStockServiceImpl stockAlertService = (MyStockServiceImpl)appContext.getApplicationContext().getBean("StockAlertService", MyStockServiceImpl.class);
	        stockAlertService.findStocksForEmailAlert(stockAlertService.fetchCurrentPriceForMultipleStocks(wList), wList);
	        
	        //STEP3: TRIGGER EMAIL FOR SELCTED SCRIPTS found at STEP-2
	        if(stockAlertService.getStockListForEmailTrigger()!=null && 
	        		stockAlertService.getStockListForEmailTrigger().size()>0) {
	        	//MailUtil mailService = (MailUtil) context.getBean("emailService");
	        	MailUtil mailService = (MailUtil)appContext.getApplicationContext().getBean("emailService", MailUtil.class);
	        	mailService.sendEmail(stockAlertService.getStockListForEmailTrigger());
	        }
	        
		} 
		catch(Exception ex) 
		{
			ex.printStackTrace();
		}
		finally
		{
			System.out.println("runTask() Exited..");
		}
	}
	
	
}
