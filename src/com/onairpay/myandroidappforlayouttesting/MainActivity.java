package com.onairpay.myandroidappforlayouttesting;

import java.util.Arrays;

import android.net.Uri;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements GlobalParameters {

	
	// This is where the XML data from yahoo 
	// will be store and manipulated as and when necessary
	
	private SharedPreferences stockSymbolsEntered;

	// This will be updated dynamically
	private TableLayout stockTableScrollView;
	private EditText stockSymbolEditText;
	Button enterStockSymbolButton;
	Button deleteStocksButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		stockSymbolsEntered = getSharedPreferences("stocklist", MODE_PRIVATE);

		// initialize UI components
		initializeUIcomponents();

		// adding Listeners
		addingListeners();

		// populating stock data into scrollView
		updateSavedStockList(null);

	}

	private void updateSavedStockList(String newStockSymbol) {

		String[] stocks = stockSymbolsEntered.getAll().keySet()
				.toArray(new String[0]);
		
		Arrays.sort(stocks, String.CASE_INSENSITIVE_ORDER);

		if (newStockSymbol != null) {

			insertStockInScrollView(newStockSymbol,
					Arrays.binarySearch(stocks, newStockSymbol));

		} else {

			for (int i = 0; i < stocks.length; i++) {

				insertStockInScrollView(stocks[i], i);

			}
		}

	}

	private void insertStockInScrollView(String stock, int arrayIndex) {

		LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View newStockRow = inflator.inflate(R.layout.stock_quote_row, null);

		TextView newStockTextView = (TextView) newStockRow
				.findViewById(R.id.stockSymbolTextView);

		newStockTextView.setText(stock);

		Button stockQuoteButton = (Button) newStockRow
				.findViewById(R.id.stockQuoteButton);
		Button quoteFromWebButton = (Button) newStockRow
				.findViewById(R.id.quoteFromWebButton);

		stockQuoteButton
				.setOnClickListener(getStockActivityListener);
		quoteFromWebButton
				.setOnClickListener(getStockFromWebsiteListener);

		stockTableScrollView.addView(newStockRow, arrayIndex);
	}

	private void saveStockSymbol(String newStock) {

		String isTheStockNew = stockSymbolsEntered.getString(newStock, null);
		SharedPreferences.Editor preferenceEditor = stockSymbolsEntered.edit();

		preferenceEditor.putString(newStock, newStock);
		preferenceEditor.apply();

		if (isTheStockNew == null) {

			updateSavedStockList(newStock);
		}
	}

	public android.view.View.OnClickListener enterStockButtonListener = new android.view.View.OnClickListener() {

		@Override
		public void onClick(View v) {
			
			if (stockSymbolEditText.getText().length() > 0) {

				saveStockSymbol(stockSymbolEditText.getText().toString());
				stockSymbolEditText.setText("");

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(
						stockSymbolEditText.getWindowToken(), 0);

			} else {

				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this);
				builder.setTitle("Error");
				builder.setPositiveButton("OK", null);

				builder.setMessage("Empty Field");

				AlertDialog theAlertDialog = builder.create();
				theAlertDialog.show();
			}

		}

	};

	private void deleteAllStocks() {

		stockTableScrollView.removeAllViews();

	}

	private void addingListeners() {

		enterStockSymbolButton
				.setOnClickListener(enterStockButtonListener);
		deleteStocksButton
				.setOnClickListener(deleteStockButtonListener);

	}

	@SuppressLint("NewApi")
	public android.view.View.OnClickListener deleteStockButtonListener = new android.view.View.OnClickListener() {

		@Override
		public void onClick(View arg0) {

			deleteAllStocks();

			SharedPreferences.Editor preferencesEditor = stockSymbolsEntered
					.edit();
			preferencesEditor.clear();
			preferencesEditor.apply();

		}
	};

	public android.view.View.OnClickListener getStockActivityListener = new android.view.View.OnClickListener() {

		@Override
		public void onClick(View arg0) {

			TableRow tableRow = (TableRow) arg0.getParent();
			TextView stockTextView = (TextView) tableRow
					.findViewById(R.id.stockSymbolTextView);

			String stockSymbol = stockTextView.getText().toString();

			Intent intent = new Intent(MainActivity.this,
					StockInfoActivity.class);
			
			intent.putExtra(STOCK_SYMBOL, stockSymbol);
			
			startActivity(intent);

		}

	};

	public android.view.View.OnClickListener getStockFromWebsiteListener = new android.view.View.OnClickListener() {

		@Override
		public void onClick(View arg0) {

			TableRow tableRow = (TableRow) arg0.getParent();
			TextView stockTextView = (TextView) tableRow
					.findViewById(R.id.stockSymbolTextView);

			String stockSymbol = stockTextView.getText().toString();

			String stockURL = getString(R.string.yahoo_stock_url) + stockSymbol;

			Intent getStockWebPage = new Intent(Intent.ACTION_VIEW,
					Uri.parse(stockURL));

			startActivity(getStockWebPage);

		}
	};

	private void initializeUIcomponents() {

		stockTableScrollView = (TableLayout) findViewById(R.id.stockTableScrollView);
		stockSymbolEditText = (EditText) findViewById(R.id.stockSymbolEditText);
		enterStockSymbolButton = (Button) findViewById(R.id.enterStockSymbolButton);
		deleteStocksButton = (Button) findViewById(R.id.deleteStocksButton);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
