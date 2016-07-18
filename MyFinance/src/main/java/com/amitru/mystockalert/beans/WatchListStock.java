package com.amitru.mystockalert.beans;

import java.util.List;

public class WatchListStock extends Stock {

	private double intrestedPriceBuy;
	private double intrestedPriceSell;
	private double livePrice;
	private boolean emailFlag;
	private List<String> emailIdList;
	private String comments;
	
	
	public WatchListStock() { }
	
	public WatchListStock(String sCode,String sType,double intrestedPriceBuy,double intrestedPriceSell,
			boolean emailFlag, List<String> emailList,String comments) {
		
		this.setScriptCode(sCode);
		this.setScriptType(sType);
		this.setIntrestedPriceBuy(intrestedPriceBuy);
		this.setIntrestedPriceSell(intrestedPriceSell);
		//this.setLivePrice(livePrice);
		this.setEmailFlag(emailFlag);
		this.setEmailIdList(emailList);
		this.setComments(comments);
		
	}
	
	public WatchListStock(String sCode, String sType) {
		this.setScriptCode(sCode);
		this.setScriptType(sType);
	}
	
	public WatchListStock(int price, boolean flg, List<String> eids, String comments) {
		
	}

	
	public double getLivePrice() {
		return livePrice;
	}

	public void setLivePrice(double livePrice) {
		this.livePrice = livePrice;
	}

	

	public double getIntrestedPriceBuy() {
		return intrestedPriceBuy;
	}

	public void setIntrestedPriceBuy(double intrestedPriceBuy) {
		this.intrestedPriceBuy = intrestedPriceBuy;
	}

	public double getIntrestedPriceSell() {
		return intrestedPriceSell;
	}

	public void setIntrestedPriceSell(double intrestedPriceSell) {
		this.intrestedPriceSell = intrestedPriceSell;
	}

	public boolean isEmailFlag() {
		return emailFlag;
	}

	public void setEmailFlag(boolean emailFlag) {
		this.emailFlag = emailFlag;
	}

	public List<String> getEmailIdList() {
		return emailIdList;
	}

	public void setEmailIdList(List<String> emailIdList) {
		this.emailIdList = emailIdList;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
	
}
