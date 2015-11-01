package com.androidexample.gridview;

import java.util.ArrayList;

import DataAccess.Product;
import DataAccess.ProductComponent;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnScrollListener;
import android.widget.Toast;

import com.Berries.gridview.R;
import com.androidexample.gridview.adapter.CustomGridAdapter;

public class GridViewExample extends Activity implements AbsListView.OnScrollListener{

	 GridView gridView;
	 private ProgressDialog pDialog;
	 ArrayList<Product> products;
	 CustomGridAdapter adapter;
	 int myLastVisiblePos;// global variable of activity
	 
	 public static int i;
	 


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		i=1;
		products=new ArrayList<Product>();
		
		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setOnScrollListener(this);
	    new GetProducts().execute("http://grapesnberries.getsandbox.com/products?count=10&from="+i);
	
	  //  gridView.setOnScrollListener(this);
  }
	
	
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastItem = firstVisibleItem + visibleItemCount - 1;
        if(lastItem>=9*i)
        {
        	i++;
    	    new GetProducts().execute("http://grapesnberries.getsandbox.com/products?count=10&from="+i);
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {        
    }

/**
 * Async task class to get json by making HTTP call
 * */
private class GetProducts extends AsyncTask<String, Void, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(GridViewExample.this);
        pDialog.setMessage("loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        
        

    }


    @Override
    protected String doInBackground(String... arg0) {
        // Creating service handler class instance
    	
    	ProductComponent productComponent=new ProductComponent();
    	products=productComponent.getProducts(arg0[0]);
    	 adapter = new CustomGridAdapter(GridViewExample.this,products);
    	
   
    	    // 2. Get ListView from activity_main.xml
    	   
        return "";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (pDialog.isShowing())
            pDialog.dismiss();
        
    	
		
        //Get gridview object from xml file
  		

  		// Set custom adapter (GridAdapter) to gridview
  		gridView.setAdapter(adapter);
  	

  		//save first value when you create GridView
  		myLastVisiblePos = gridView.getFirstVisiblePosition();
	 
	 
	 
    }

	    
    }







}

