package com.onairpay.myandroidappforlayouttesting;

public class StockInfo {
	
	private String name = "";
	private String daysLow = "";
	private String daysHigh = "";
	private String yearLow = "";
	private String yearHigh = "";
	private String lastTradePriceonly = "";
	private String change = "";
	private String daysRange = "";
	
	
	/**
	 * @param daysLow
	 * @param daysHigh
	 * @param yearLow
	 * @param yearHigh
	 * @param lastTradePriceonly
	 * @param change
	 * @param daysRange
	 */
	
	public StockInfo(String name, String daysLow, String daysHigh, String yearLow,
			String yearHigh, String lastTradePriceonly, String change,
			String daysRange) {
		
		super();
		this.name = name;
		this.daysLow = daysLow;
		this.daysHigh = daysHigh;
		this.yearLow = yearLow;
		this.yearHigh = yearHigh;
		this.lastTradePriceonly = lastTradePriceonly;
		this.change = change;
		this.daysRange = daysRange;
	}
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the daysLow
	 */
	public String getDaysLow() {
		return daysLow;
	}
	
	/**
	 * @param daysLow the daysLow to set
	 */
	public void setDaysLow(String daysLow) {
		this.daysLow = daysLow;
	}
	
	/**
	 * @return the daysHigh
	 */
	public String getDaysHigh() {
		return daysHigh;
	}
	
	/**
	 * @param daysHigh the daysHigh to set
	 */
	public void setDaysHigh(String daysHigh) {
		this.daysHigh = daysHigh;
	}
	
	/**
	 * @return the yearLow
	 */
	public String getYearLow() {
		return yearLow;
	}
	
	/**
	 * @param yearLow the yearLow to set
	 */
	public void setYearLow(String yearLow) {
		this.yearLow = yearLow;
	}
	
	/**
	 * @return the yearHigh
	 */
	public String getYearHigh() {
		return yearHigh;
	}
	
	/**
	 * @param yearHigh the yearHigh to set
	 */
	public void setYearHigh(String yearHigh) {
		this.yearHigh = yearHigh;
	}
	
	/**
	 * @return the lastTradePriceonly
	 */
	public String getLastTradePriceonly() {
		return lastTradePriceonly;
	}
	
	/**
	 * @param lastTradePriceonly the lastTradePriceonly to set
	 */
	public void setLastTradePriceonly(String lastTradePriceonly) {
		this.lastTradePriceonly = lastTradePriceonly;
	}
	
	/**
	 * @return the change
	 */
	public String getChange() {
		return change;
	}
	
	/**
	 * @param change the change to set
	 */
	public void setChange(String change) {
		this.change = change;
	}
	
	/**
	 * @return the daysRange
	 */
	public String getDaysRange() {
		return daysRange;
	}
	
	/**
	 * @param daysRange the daysRange to set
	 */
	public void setDaysRange(String daysRange) {
		this.daysRange = daysRange;
	}
}
