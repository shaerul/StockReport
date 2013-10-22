package com.onairpay.myandroidappforlayouttesting;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class StockInfoActivity extends Activity implements GlobalParameters {

	TextView stockCompanyName;
	TextView stockYearLow;
	TextView stockYearHigh;
	TextView stockDayLow;
	TextView stockDayHigh;
	TextView stockLastPrice;
	TextView stockChange;
	TextView stockDaysRange;

	String name = "";
	String yearLow = "";
	String yearHigh = "";
	String dayLow = "";
	String dayHigh = "";
	String lLastPrice = "";
	String change = "";
	String daysRange = "";

	// Used to make the URL to call for XML data
	String yahooURL1 = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quote%20where%20symbol%20in%20(%22";
	String yahooURL2 = "%22)&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_stock_info);

		String stockSymbol = getIntent().getStringExtra(STOCK_SYMBOL);

		// initialize UI components
		initializeUIcomponents();

		Log.d(DTAG, "Before URL creation " + stockSymbol);

		final String yql = yahooURL1 + stockSymbol + yahooURL2;

		new MyAsyncTask().execute(yql);

	}

	public class MyAsyncTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... args) {

			try {

				URL url = new URL(args[0]);
				URLConnection connection;
				connection = url.openConnection();

				HttpURLConnection httpConnection = (HttpURLConnection) connection;

				int responseCode = httpConnection.getResponseCode();

				if (responseCode == HttpURLConnection.HTTP_OK) {

					InputStream in = httpConnection.getInputStream();

					DocumentBuilderFactory dbf = DocumentBuilderFactory
							.newInstance();

					DocumentBuilder db = dbf.newDocumentBuilder();

					Document dom = db.parse(in);

					// import org.w3c.dom.Element
					Element docEle = dom.getDocumentElement();

					NodeList nl = docEle.getElementsByTagName(KEY_ITEM);

					if (nl != null && nl.getLength() > 0) {

						for (int i = 0; i < nl.getLength(); i++) {

							StockInfo theStock = getStockInformation(docEle);

							name = theStock.getName();
							yearLow = theStock.getYearLow();
							yearHigh = theStock.getYearHigh();
							dayLow = theStock.getDaysLow();
							dayHigh = theStock.getDaysHigh();
							lLastPrice = theStock.getLastTradePriceonly();
							change = theStock.getChange();
							daysRange = theStock.getDaysRange();

						}

					}

				}

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			stockCompanyName.setText(stockCompanyName.getText().toString() + " " + name);
			stockYearLow.setText(stockYearLow.getText().toString() + " " + yearLow);
			stockYearHigh.setText(stockYearHigh.getText().toString() + " " + yearHigh);
			stockDayLow.setText(stockDayLow.getText().toString() + " " + dayLow);
			stockDayHigh.setText(stockDayHigh.getText().toString() + " " + dayHigh);
			stockLastPrice.setText(stockLastPrice.getText().toString() + " " + lLastPrice);
			stockChange.setText(stockChange.getText().toString() + " " + change);
			stockDaysRange.setText(stockDaysRange.getText().toString() + " " + daysRange);

		}

		private StockInfo getStockInformation(Element entry) {

			String stockName = getTextValue(entry, KEY_NAME);
			String stockYearLow = getTextValue(entry, KEY_YEAR_LOW);
			String stockYearHigh = getTextValue(entry, KEY_YEAR_HIGH);
			String stockDaysLow = getTextValue(entry, KEY_DAYS_LOW);
			String stockDaysHigh = getTextValue(entry, KEY_DAYS_HIGH);
			String stockLastTradePriceOnly = getTextValue(entry,
					KEY_LAST_TRADE_PRICE);
			String stockChange = getTextValue(entry, KEY_CHANGE);
			String stockDaysRange = getTextValue(entry, KEY_DAYS_RANGE);

			StockInfo theStock = new StockInfo(stockName, stockDaysLow,
					stockDaysHigh, stockYearLow, stockYearHigh,
					stockLastTradePriceOnly, stockChange, stockDaysRange);

			return theStock;

		}

		private String getTextValue(Element entry, String tagName) {

			String tagValueToReturn = null;
			NodeList nl = entry.getElementsByTagName(tagName);

			if (nl != null && nl.getLength() > 0) {

				Element element = (Element) nl.item(0);

				tagValueToReturn = element.getFirstChild().getNodeValue();

			}

			return tagValueToReturn;

		}

	}

	private void initializeUIcomponents() {

		stockCompanyName = (TextView) findViewById(R.id.stockCompanyName);
		stockYearLow = (TextView) findViewById(R.id.stockYearLow);
		stockYearHigh = (TextView) findViewById(R.id.stockYearHigh);
		stockDayLow = (TextView) findViewById(R.id.stockDayLow);
		stockDayHigh = (TextView) findViewById(R.id.stockDayHigh);
		stockLastPrice = (TextView) findViewById(R.id.stockLastPrice);
		stockChange = (TextView) findViewById(R.id.stockChange);
		stockDaysRange = (TextView) findViewById(R.id.stockDaysRange);

	}

}
