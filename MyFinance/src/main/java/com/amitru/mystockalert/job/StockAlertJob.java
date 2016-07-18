package com.amitru.mystockalert.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class StockAlertJob extends QuartzJobBean {

	@Autowired
	StockAlertTask stockAlertTask;
	
	protected void executeInternal(JobExecutionContext context)
		throws JobExecutionException {
		stockAlertTask.runTask();
	}

	public StockAlertTask getStockAlertTask() {
		return stockAlertTask;
	}

	public void setStockAlertTask(StockAlertTask stockAlertTask) {
		this.stockAlertTask = stockAlertTask;
	}
	
	
	
}
