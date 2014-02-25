package googleplacesandmaps;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import library.DatabaseHandler;
import library.ServiceHandler;

import userActivity.RegisterActivity;

import com.study.googleapi.OpenMap;
import com.study.googleapi.R;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SpinnerList extends Activity implements
OnItemSelectedListener,OnClickListener{
final int N =12;
int notVisiable=0 ;
// Spinner element
Spinner [] spinner;

// Add button
Button btnAddSpinner;
Button btnRemoveSpinner;
Button btnShowRoute;
// Input text
EditText inputLabel;

PlacesList2 places2List2;
private List<Place2> place2List;
private List<Place2> placesListToFindRoute;
ProgressDialog pDialog;

boolean firsTime=true; 

private String URL_PLACES2 = "http://80.179.219.4/~elior/teachme/tsp/getPlaces.php";



@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.spinner_places);

spinner =new Spinner[N];
// Spinner element
spinner[0] = (Spinner) findViewById(R.id.spinner0);
spinner[1] = (Spinner) findViewById(R.id.spinner1);
spinner[2] = (Spinner) findViewById(R.id.spinner2);
spinner[3] = (Spinner) findViewById(R.id.spinner3);
spinner[4] = (Spinner) findViewById(R.id.spinner4);
spinner[5] = (Spinner) findViewById(R.id.spinner5);
spinner[6] = (Spinner) findViewById(R.id.spinner6);
spinner[7] = (Spinner) findViewById(R.id.spinner7);
spinner[8] = (Spinner) findViewById(R.id.spinner8);
spinner[9] = (Spinner) findViewById(R.id.spinner9);
spinner[10] = (Spinner) findViewById(R.id.spinner10);
spinner[11] = (Spinner) findViewById(R.id.spinner11);

// add button

btnAddSpinner = (Button) findViewById(R.id.btnAddSpinner);
btnRemoveSpinner = (Button) findViewById(R.id.btnRemoveSpinner);
btnShowRoute=(Button) findViewById(R.id.btnShowRoute);

btnAddSpinner.setOnClickListener(this);
btnRemoveSpinner.setOnClickListener(this);
btnShowRoute.setOnClickListener(this);
// new label input field
//inputLabel = (EditText) findViewById(R.id.input_label);

// Spinner click listener
for(int i = 0 ; i < N ; i++)
{
	spinner[i].setOnItemSelectedListener(this);
}

placesListToFindRoute= new ArrayList<Place2>();
place2List = new ArrayList<Place2>();
place2List.add(new Place2(0,"select place",""));

	new GetPlaces().execute();
//
//// Loading spinner data from database
//loadSpinnerData();
//
///**
// * Add new label button click listener
// * */
//btnAdd.setOnClickListener(new View.OnClickListener() {
//
//    @Override
//    public void onClick(View arg0) {
////        String label = inputLabel.getText().toString();
////
////        if (label.trim().length() > 0) {
////            // database handler
////            DatabaseHandler db = new DatabaseHandler(
////                    getApplicationContext());
////
////            // inserting new label into database
////            db.insertLabel(label);
////
////            // making input filed text to blank
////            inputLabel.setText("");
////
////            // Hiding the keyboard
////            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
////            imm.hideSoftInputFromWindow(inputLabel.getWindowToken(), 0);
////
////            // loading spinner with newly added data
////            loadSpinnerData();
////        } else {
////            Toast.makeText(getApplicationContext(), "Please enter label name",
////                    Toast.LENGTH_SHORT).show();
////        }
////
//	   }
//	});
	}



	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
	    long id) {
		String label = parent.getItemAtPosition(position).toString();
	// On selecting a spinner item

		if(position!=0)
		{
			placesListToFindRoute.add(place2List.get(position));
	
//			// Showing selected spinner item
//			Toast.makeText(parent.getContext(), "You selected: " + placesListToFindRoute.get(position).getName(),
//				       2).show();
		}
	
	}
	
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
	// TODO Auto-generated method stub
		
	
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
		case R.id.btnAddSpinner:
			if(notVisiable<N-1)
			{
				notVisiable++;
				spinner[notVisiable].setVisibility(1);
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Limit Occurred", 3).show();
			
			}
			break;
		case R.id.btnRemoveSpinner:
			if(notVisiable>0)
			{
				spinner[notVisiable].setVisibility(8);
				notVisiable--;
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Limit Occurred", 3).show();
			
			}
			
			break;
		case R.id.btnShowRoute:
			Intent i = new Intent(getApplicationContext(),
					com.study.googleapi.OpenMap.class);
			
			// passing near places to map activity
			
			places2List2= new PlacesList2( placesListToFindRoute);
			i.putExtra("placesListToFindRoute", places2List2);
			i.putExtra("status", 1);
			// staring activity
			startActivity(i);
			break;

		}		
	} 
	
	   private void populateSpinner() {
	        List<String> lables = new ArrayList<String>();
	         
	        //txtCategory.setText("");
	        
	        for (int i = 0; i < place2List.size(); i++) {
	            lables.add(place2List.get(i).getName());
	        }
	 
	        // Creating adapter for spinner
	        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
	                android.R.layout.simple_spinner_item, lables);
	 
	        // Drop down layout style - list view with radio button
	        spinnerAdapter
	                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	 
	        // attaching data adapter to spinner
	        for(int q=0 ;q< N ; q++)
	        {
	        	spinner[q].setAdapter(spinnerAdapter);
	        	spinner[q].setPrompt("Select your place");
	        	
	        }
	    }
	
	
	private class GetPlaces extends AsyncTask<Void, Void, List<Place2>> {
		 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SpinnerList.this);
            pDialog.setMessage("Fetching Places..");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected  List<Place2> doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_PLACES2, ServiceHandler.GET);
 
            Log.e("Response: ", "> " + json);
 
            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray places2 = jsonObj
                                .getJSONArray("place");                        
 
                        for (int i = 0; i < places2.length(); i++) {
                            JSONObject plObj = (JSONObject) places2.get(i);
                            Place2 pl2 = new Place2(plObj.getInt("id"),plObj.getString("name")
                            ,plObj.getString("adress")
                            ,plObj.getDouble("lat"),plObj.getDouble("lang"));
                            place2List.add(pl2);
                        }
                    }
 
                } catch (JSONException e) {
                    e.printStackTrace();
                }
 
            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }
 
            return place2List;
        }
 
        @Override
        protected void onPostExecute(List<Place2> result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            populateSpinner();
        }
 
    }
	
	
	
}




