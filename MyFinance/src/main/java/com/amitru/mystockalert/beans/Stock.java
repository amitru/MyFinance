package com.amitru.mystockalert.beans;

public class Stock {
	
	private String scriptCode;
	private String scriptType;
	
	public Stock() {}
	
	public Stock(String sCode, String sType) {
		this.scriptCode = sCode;
		this.scriptType = sType;
	}

	public String getScriptCode() {
		return scriptCode;
	}

	public void setScriptCode(String scriptCode) {
		this.scriptCode = scriptCode;
	}

	public String getScriptType() {
		return scriptType;
	}

	public void setScriptType(String scriptType) {
		this.scriptType = scriptType;
	}
	
	
	
}
