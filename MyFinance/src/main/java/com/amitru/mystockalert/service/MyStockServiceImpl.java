package com.amitru.mystockalert.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import com.amitru.mystockalert.beans.Stock;
import com.amitru.mystockalert.beans.WatchListStock;


public class MyStockServiceImpl implements MyStockServiceI {

	@Autowired
	List<WatchListStock> watchListStockList;
	
	@Autowired
	List<WatchListStock> stockListForEmailTrigger;
	
	@Autowired
	String url;
	
	//MaxLimit is 200 shares can be added...
	//Date: 18July'16,YAHOO Finance Not working Now.. , changed to google API
	public Map<String,String> fetchCurrentPriceForMultipleStocks(List<WatchListStock> stock) throws Exception
	{
		HashMap<String, String> stockPriceMap = new HashMap<String, String>();
		
		System.out.println("fetchCurrentPriceForMultipleStocks() entered stock list size -" + stock.size());
		
		String dynamicURL = getUrl();
		String stockString = "";
		
		for(Stock myStock: stock) {
			//stockString = stockString + myStock.getScriptCode() + "." + myStock.getScriptType() + ",";
			stockString = stockString + myStock.getScriptCode() + ":" + myStock.getScriptType() + ",";
		}
		
		stockString = stockString.substring(0, stockString.length()-1);
		
		dynamicURL = dynamicURL.replaceAll("PLACEHOLDER", stockString);
		
		System.out.println("URL-" + dynamicURL);
		
		URL financeAPIURL = new URL(dynamicURL);
        HttpURLConnection con = (HttpURLConnection) financeAPIURL.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
        String inputLine;
        
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        
        in.close();
        
        
        System.out.println(response.toString());
        
        String token[] = StringUtils.split(response.toString(), "//"); 
        
        System.out.println("token-"+token[1]);
        
        JSONArray jsonArray = new JSONArray(token[1]);
         
        for(int i=0;i<jsonArray.length();i++) {
	        JSONObject resourceObject = new JSONObject(jsonArray.get(i).toString());
	        stockPriceMap.put(resourceObject.get("t").toString(), resourceObject.get("l_cur").toString());
        }
        
       /* JSONObject stockJsonObj  = new JSONObject(response.toString());
        JSONObject listObject = stockJsonObj.getJSONObject("list");
        JSONArray resourcesArray = listObject.getJSONArray("resources");
        
        System.out.println("resourcesArray size " + resourcesArray.length());
        
	   for(int i=0;i<resourcesArray.length();i++) {
	        JSONObject resourceObject = new JSONObject(resourcesArray.get(i).toString());
	        JSONObject res = new JSONObject(resourceObject.get("resource").toString());
	        JSONObject priceObj = new JSONObject(res.get("fields").toString());
	        stockPriceMap.put(priceObj.get("symbol").toString(), priceObj.get("price").toString());
        }*/
        
        System.out.println(stockPriceMap);
	   
		return stockPriceMap;
	}
	
	
	public static void main(String[] args) throws Exception {
		ApplicationContext myAppContext = new ClassPathXmlApplicationContext("Spring-Module.xml");
		MyStockServiceImpl stockAlertService = (MyStockServiceImpl)myAppContext.getBean("StockAlertService", MyStockServiceImpl.class);
		
		List<WatchListStock> stock = new ArrayList<WatchListStock>();
		WatchListStock ws = new WatchListStock("NSE","GABRIEL");
		stock.add(ws);
		ws = new WatchListStock("NSE","BRITANNIA");
		stock.add(ws);
		stockAlertService.fetchCurrentPriceForMultipleStocks(stock);
		
		  
	}
	
	public void findStocksForEmailAlert(Map<String, String> stockPriceMap, List<WatchListStock> wList)
			throws Exception {

		
		for(WatchListStock wLStock: wList) {
			double intrestedPrice = wLStock.getIntrestedPriceBuy();
			double livePrice = Double.parseDouble(stockPriceMap.get(wLStock.getScriptCode() + "." + wLStock.getScriptType()));
			
			if(livePrice<=intrestedPrice) {
				System.out.println("Price alert triggered for stock-" + wLStock.getScriptCode() );
				wLStock.setLivePrice(livePrice);
				this.stockListForEmailTrigger.add(wLStock);
			}
		}
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<WatchListStock> getWatchListStockList() {
		return watchListStockList;
	}

	public void setWatchListStockList(List<WatchListStock> watchListStockList) {
		this.watchListStockList = watchListStockList;
	}

	public List<WatchListStock> getStockListForEmailTrigger() {
		return stockListForEmailTrigger;
	}

	public void setStockListForEmailTrigger(List<WatchListStock> stockListForEmailTrigger) {
		this.stockListForEmailTrigger = stockListForEmailTrigger;
	}
}

