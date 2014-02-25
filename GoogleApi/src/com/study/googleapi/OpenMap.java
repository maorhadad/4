package com.study.googleapi;

import googleplacesandmaps.AddItemizedOverlay;
import googleplacesandmaps.GPSTracker;
import googleplacesandmaps.MainActivity2;
import googleplacesandmaps.Path;
import googleplacesandmaps.Place;
import googleplacesandmaps.Place2;
import googleplacesandmaps.PlacesList;
import googleplacesandmaps.AlertDialogManager;
import googleplacesandmaps.GMapV2GetRouteDirection;
import googleplacesandmaps.PlacesList2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import android.os.AsyncTask;
import org.w3c.dom.Document;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.wallet.Address;
//////////////////////////////////////////////////////////////////////
import com.google.android.gms.maps.CameraUpdateFactory;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
//////////////////////////////////////////////////////////////////////


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.app.ActionBar;


public class OpenMap extends FragmentActivity {
	MarkerOptions [] markerOptions;
	   //MarkerOptions  markerOptions;
	final int P = 20;
	   int placeSize;
	   int placeIndex=0;
	   GeoPoint point1, point2;
	   LocationManager locManager;
	   Drawable drawable;
	   Document  document;
	   Document [] docu;
	   Document [] docuTest;
	   LatLng fromPosition;
	   LatLng myPosition;
	   LatLng toPosition;
	   MarkerOptions marker1;
	   MarkerOptions marker2;
	   Location location ;
	// Google Map
	   GoogleMap googleMap;
	   PlacesList nearPlaces;
		// Map view
		//MapView mapView;
	   GPSTracker gps2;
	   AlertDialogManager alert = new AlertDialogManager();
		// Map overlay items
	   List<Overlay> mapOverlays;

	   AddItemizedOverlay itemizedOverlay;
	   GeoPoint geoPoint;
		// Map controllers
	   MapController mc;
	   double latitude;
	   double longitude;
	   double latitude2;
	   double longitude2;
	   OverlayItem overlayitem;
	   int color;
	   int W=35;
	   boolean once=false;
	  static boolean sima=false;
	   PolylineOptions [] rectLine ;
	   PolylineOptions [] rectLineCopy ;
	   
	   GetRouteTask [] getRoute;
	   GetRouteTask [] getRouteTest;
	   GMapV2GetRouteDirection [] v2GetRouteDirection;
	   //Place [] ppp = new Place[3];
	  // Place kanion,postOffice,home,ramot;
	   int tempN=3;
	   int  line  ;
	   Intent i;
	   private PlacesList2 placesListToFindRoute;
	   int status;
	   
	   int SIZE;
	   
	   Path path;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_map);
			line=0;

			rectLine= new PolylineOptions[100];
			gps2=new GPSTracker(this);
			try {
				// Loading map
				initilizeMap();
				// Changing map type
				googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				// googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
				// googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
				// googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
				// googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

				// Showing / hiding your current location
				googleMap.setMyLocationEnabled(true);

				// Enable / Disable zooming controls
				googleMap.getUiSettings().setZoomControlsEnabled(true);

				// Enable / Disable my location button
				googleMap.getUiSettings().setMyLocationButtonEnabled(true);

				// Enable / Disable Compass icon
				googleMap.getUiSettings().setCompassEnabled(true);

				// Enable / Disable Rotate gesture
				googleMap.getUiSettings().setRotateGesturesEnabled(true);

				// Enable / Disable zooming functionality
				googleMap.getUiSettings().setZoomGesturesEnabled(true);
				//googleMap.setTrafficEnabled(true);
			///leonid.olevfky@gmail.com
				/////////////////////אתר ניסיונות//////////////////////////////////////////////////////////////////////				
				// Getting intent data
				 i = getIntent();
				status= i.getIntExtra("status", -1);
				
				placeIndex=0;
				if(status==0)
				{
					nearPlaces = (PlacesList) i.getSerializableExtra("near_places");
					SIZE=nearPlaces.results.size();
					findRouteFromGoolglePlaces();
				}
				else if(status==1)
				{
					placesListToFindRoute=(PlacesList2)i.getSerializableExtra("placesListToFindRoute");
					SIZE=placesListToFindRoute.results.size();
					findRoutesFromUserLisr();
					
				}
				else if(status==-1)
				{
					//SIZE=placesListToFindRoute.results.size();
					initMapLayres2();
					Toast.makeText(getApplicationContext(),
							"Sorry! Cant pass list", Toast.LENGTH_SHORT)
							.show();
				}
				
				for(int T =0 ;T<SIZE;T++)
				{
					line=T;
				  ArrayList<LatLng> directionPoint = v2GetRouteDirection[line].getDirection( docu[T]);

	                 for (int y = 0;y < directionPoint.size(); y++) 
	                 {
	                       rectLine[T].add(directionPoint.get(y));    
	                 }
	                 googleMap.addPolyline(rectLine[T]);
				}
//				for(int y=0;y<line;y++) 
//					googleMap.addPolyline(rectLine[y]);
				
				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(new LatLng(latitude,
								longitude)).zoom(15).build();

				googleMap.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		protected void onResume() {
			super.onResume();
			initilizeMap();
			
		}
		

	     public void updateRoute(ArrayList<LatLng> dp )
	     {
	    		
             for (int i = 0; i < dp.size(); i++) 
             {
                   rectLine[line].add(dp.get(i));
             }
             googleMap.addPolyline(rectLine[line]);
             //line++;
	     }


		/**
		 * function to load map If map is not created it will create it for you
		 * */
		private void initilizeMap() {
			if (googleMap == null) {
				googleMap = ((MapFragment) getFragmentManager().findFragmentById(
						R.id.map)).getMap();

				// check if map is created successfully or not
				if (googleMap == null) {
					Toast.makeText(getApplicationContext(),
							"Sorry! unable to create maps", Toast.LENGTH_SHORT)
							.show();
				}
			}
		}
	private void initMapLayres2()
	{
		
		 getRoute = new GetRouteTask[SIZE];
		 getRouteTest = new GetRouteTask[(SIZE*SIZE)/2];
		 docu = new Document[SIZE];
		 docuTest= new Document[(SIZE*SIZE)/2];
		 v2GetRouteDirection = new GMapV2GetRouteDirection[SIZE];
		 markerOptions = new MarkerOptions[SIZE+1];
		rectLine = new PolylineOptions[SIZE];
		
		if (gps2.canGetLocation()) {
			latitude=gps2.getLatitude();
			longitude=gps2.getLongitude();
		} else {
			// Can't get user's current location
			alert.showAlertDialog(OpenMap.this, "GPS Status",
					"Couldn't get location information. Please enable GPS",//activate GPS and find location
					false);
			// stop executing code by return
			return;
		}
	//	
//		 getRoute = new GetRouteTask[nearPlaces.results.size()-1];
//		 
//		 v2GetRouteDirection = new GMapV2GetRouteDirection[nearPlaces.results.size()-1];
		//v2GetRouteDirection = new GMapV2GetRouteDirection();
	//
		 for(int y=0 ;y<SIZE;y++)
			 v2GetRouteDirection[y] = new GMapV2GetRouteDirection();
		 
//			geoPoint = new GeoPoint( (int) (longitude* 1E6), (int) (longitude* 1E6) );
//			
//			Drawable drawable_user = this.getResources()
//					.getDrawable(R.drawable.mark_red);
//			
//			itemizedOverlay = new AddItemizedOverlay(drawable_user, this);
//			
//			// Map overlay item
//			overlayitem = new OverlayItem(geoPoint, "Your Location",
//					"That is you!");
//
//			itemizedOverlay.addOverlay(overlayitem);
//			
//			mapOverlays.add(itemizedOverlay);
//			itemizedOverlay.populateNow();
		
		
	}
		private void initMapLayers()
		{
			 getRoute = new GetRouteTask[SIZE];
			 getRouteTest = new GetRouteTask[(SIZE*SIZE)/2];
			 docu = new Document[SIZE];
			 docuTest= new Document[(SIZE*SIZE)/2];
			 v2GetRouteDirection = new GMapV2GetRouteDirection[SIZE];
			 markerOptions = new MarkerOptions[SIZE+1];
			rectLine = new PolylineOptions[SIZE];
			
		//	
//			 getRoute = new GetRouteTask[nearPlaces.results.size()-1];
//			 
//			 v2GetRouteDirection = new GMapV2GetRouteDirection[nearPlaces.results.size()-1];
			//v2GetRouteDirection = new GMapV2GetRouteDirection();
		//
			 for(int y=0 ;y<SIZE;y++)
				 v2GetRouteDirection[y] = new GMapV2GetRouteDirection();
			 
//			 for(int y=0 ;y<nearPlaces.results.size();y++)
//				 	docu[y] = new Document();
			 
//			 
//			 for(int y=0 ;y<tempN-1;y++)
//			 v2GetRouteDirection[y] = new GMapV2GetRouteDirection();
			 
			for(int y=0 ;y<SIZE+1;y++)
			{
				markerOptions[y]=new MarkerOptions();
				if(y==0)
					markerOptions[y].icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
				else if(y==1)
					markerOptions[y].icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
				else if(y==2)
					markerOptions[y].icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
				else if(y==3)
					markerOptions[y].icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
				else if(y==4)
					markerOptions[y].icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
				else if(y==5)
					markerOptions[y].icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_RED));
				else if(y==6)
					markerOptions[y].icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
				else if(y==7)
					markerOptions[y].icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
				else if(y==8)
					markerOptions[y].icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
				else if(y==9)
					markerOptions[y].icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
				else if(y==11)
					markerOptions[y].icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
				else if(y==12)
					markerOptions[y].icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
				else if(y==13)
					markerOptions[y].icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
				else if(y==14)
					markerOptions[y].icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
				else if(y==15)
					markerOptions[y].icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
			}
			
				for(int y=0 ;y<SIZE;y++)
				{
					int temp = 7;
					rectLine[y] = new PolylineOptions().width(30-temp);
					if(y==0)
						rectLine[y].color(Color.BLUE);
					else if(y==1){
						rectLine[y].color(Color.rgb(238,130,238));//VIOLET
					}
					else if(y==2)
						rectLine[y].color(Color.CYAN);
					else if(y==3)
						rectLine[y].color(Color.YELLOW);
					else if(y==4)
						rectLine[y].color(Color.GREEN);
					else if(y==5)
						rectLine[y].color(Color.RED);
					else if(y==6)
						rectLine[y].color(Color.rgb(255,0,127));//ROSE
					else if(y==7)
						rectLine[y].color(Color.MAGENTA);
					else if(y==8)
						rectLine[y].color(Color.rgb(255,165,0));//ORANGE);
					else if(y==9)
						rectLine[y].color(Color.rgb(0,127,255));//HUE_AZURE;
					else if(y==11)
						rectLine[y].color(Color.rgb(153,0,204));
					else if(y==12)
						rectLine[y].color(Color.rgb(153,0,204));
					else if(y==13)
						rectLine[y].color(Color.rgb(153,0,204));
					else if(y==14)
						rectLine[y].color(Color.rgb(157,1,208));
					else if(y==15)
						rectLine[y].color(Color.rgb(150,2,214));

				}
				if (gps2.canGetLocation()) {
					latitude=gps2.getLatitude();
					longitude=gps2.getLongitude();
				} else {
					// Can't get user's current location
					alert.showAlertDialog(OpenMap.this, "GPS Status",
							"Couldn't get location information. Please enable GPS",//activate GPS and find location
							false);
					// stop executing code by return
					return;
				}

			
		}
		
		
private void findRoutesFromUserLisr()
{
	
	////////////////////////////////////////////////////////////////////////////////test site
	
	int i1=0;
	int j=0;
	int z=0;
	
	Place2 placeStart;
	Place2 placeEnd;
	initMapLayers();
	if (placesListToFindRoute.results != null) {
		// loop through all the places
		//placeSize=placesListToFindRoute.results.size();
//       
		
		 myPosition = new LatLng(latitude, longitude);
         markerOptions[placeIndex].position(myPosition);
         markerOptions[placeIndex].title(String.valueOf("thats u"));
         markerOptions[placeIndex].draggable(true);
         googleMap.addMarker(markerOptions[placeIndex]);
         placeIndex++;
//			
		for(j=0;j<SIZE;j++)
		{
			placeStart=placesListToFindRoute.results.get(j);///put Markers in place
			latitude = placeStart.getLat(); // latitude
			longitude =placeStart.getLang(); // longitude

		    fromPosition = new LatLng(latitude, longitude);

         	markerOptions[placeIndex].position(fromPosition);
         	markerOptions[placeIndex].title(String.valueOf(placeIndex));
         	markerOptions[placeIndex].draggable(true);
         	googleMap.addMarker(markerOptions[placeIndex]);
         	placeIndex++;
		}
//            
//		
		placeEnd=placesListToFindRoute.results.get((0));
		latitude2 = placeEnd.getLat(); // latitude2
		longitude2 =placeEnd.getLang(); // longitude2
		
	    fromPosition = new LatLng(myPosition.latitude,myPosition.longitude);
		toPosition = new LatLng(latitude2, longitude2);
		
     	 getRouteTest[0] = new GetRouteTask();
     	 getRouteTest[0].execute();
     	 
		 try {
			docuTest[0]=getRouteTest[0].get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 int track=1;
		 for(j=0;j<SIZE-1;j++)
		 {
			 placeStart=placesListToFindRoute.results.get(j);
			 latitude = placeStart.getLat(); // latitude
			 longitude =placeStart.getLang(); // longitude
			 
			 for(z=j+1;z<SIZE;z++)
			 {
					placeEnd=placesListToFindRoute.results.get((j+1));
					latitude2 = placeEnd.getLat();// latitude2
					longitude2 =placeEnd.getLang(); // longitude2
					
				    fromPosition = new LatLng(latitude, longitude);
					toPosition = new LatLng(latitude2, longitude2);
					
	                try{
	                getRouteTest[track] = new GetRouteTask();
	                getRouteTest[track].execute();
					docuTest[track]=getRouteTest[track].get();
					track++;
	        		} catch (InterruptedException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		} catch (ExecutionException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
					
			 }
			 
		 }
//			for(j=0;j<SIZE-1;j++)
//			{	
//				
//					//line=j;	
//					line=j+1;
//					placeStart=placesListToFindRoute.results.get(j);
//					latitude = placeStart.getLat(); // latitude
//					longitude =placeStart.getLang(); // longitude
//					
//					placeEnd=placesListToFindRoute.results.get((j+1));
//					latitude2 = placeEnd.getLat();// latitude2
//					longitude2 =placeEnd.getLang(); // longitude2
//					
//		    
//				    fromPosition = new LatLng(latitude, longitude);
//					toPosition = new LatLng(latitude2, longitude2);
//
//	                try{
//                 	 getRoute[j+1] = new GetRouteTask();
//                 	 getRoute[j+1].execute();
//					 docu[j+1]=getRoute[j+1].get();
//	        		} catch (InterruptedException e) {
//	        			// TODO Auto-generated catch block
//	        			e.printStackTrace();
//	        		} catch (ExecutionException e) {
//	        			// TODO Auto-generated catch block
//	        			e.printStackTrace();
//	        		}
//				
////				rectLine[line].add(fromPosition,toPosition);
////             	googleMap.addPolyline(rectLine[line]);
//
//		}
	}
	
	path = new Path(placesListToFindRoute.results,docuTest);
	////////////////////////////////////////////////////////////////////////////////end test site
	
	for(int p =0 ; p<placesListToFindRoute.results.size();p++)
	{
		Toast.makeText(getApplicationContext(),
				String.valueOf(placesListToFindRoute.results.get(p).getLat())+String.valueOf(placesListToFindRoute.results.get(p).getLang())
				, p+1)
				.show();
	}
	
}
private void findRouteFromGoolglePlaces()
{
	
	initMapLayers();
	//placesListToFindRoute=(ArrayList<Place2>) i.getSerializableExtra("placesListToFindRoute");
//	for(int p=0;p<placesListToFindRoute.size();p++)
//	{
//		Toast.makeText(getApplicationContext(),placesListToFindRoute.get(p).getName(), p).show();
//	}
//	markerOptions = new MarkerOptions[nearPlaces.results.size()];
//	 getRoute = new GetRouteTask[nearPlaces.results.size()];
//	 docu = new Document[nearPlaces.results.size()];
//	 v2GetRouteDirection = new GMapV2GetRouteDirection[nearPlaces.results.size()];
//	 markerOptions = new MarkerOptions[nearPlaces.results.size()+1];
//	rectLine = new PolylineOptions[nearPlaces.results.size()];
//	
////	
////	 getRoute = new GetRouteTask[nearPlaces.results.size()-1];
////	 
////	 v2GetRouteDirection = new GMapV2GetRouteDirection[nearPlaces.results.size()-1];
//	//v2GetRouteDirection = new GMapV2GetRouteDirection();
////
//	 for(int y=0 ;y<nearPlaces.results.size();y++)
//		 v2GetRouteDirection[y] = new GMapV2GetRouteDirection();
//	 
////	 for(int y=0 ;y<nearPlaces.results.size();y++)
////		 	docu[y] = new Document();
//	 
////	 
////	 for(int y=0 ;y<tempN-1;y++)
////	 v2GetRouteDirection[y] = new GMapV2GetRouteDirection();
//	 
//	for(int y=0 ;y<nearPlaces.results.size()+1;y++)
//	{
//		markerOptions[y]=new MarkerOptions();
//		if(y==0)
//			markerOptions[y].icon(BitmapDescriptorFactory
//					.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//		else if(y==1)
//			markerOptions[y].icon(BitmapDescriptorFactory
//					.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
//		else if(y==2)
//			markerOptions[y].icon(BitmapDescriptorFactory
//					.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
//		else if(y==3)
//			markerOptions[y].icon(BitmapDescriptorFactory
//					.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
//		else if(y==4)
//			markerOptions[y].icon(BitmapDescriptorFactory
//					.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//		else if(y==5)
//			markerOptions[y].icon(BitmapDescriptorFactory
//					.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//		else if(y==6)
//			markerOptions[y].icon(BitmapDescriptorFactory
//					.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
//		else if(y==7)
//			markerOptions[y].icon(BitmapDescriptorFactory
//					.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//		else if(y==8)
//			markerOptions[y].icon(BitmapDescriptorFactory
//					.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
//		else if(y==9)
//			markerOptions[y].icon(BitmapDescriptorFactory
//					.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
//		else if(y==11)
//			markerOptions[y].icon(BitmapDescriptorFactory
//					.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
//		else if(y==12)
//			markerOptions[y].icon(BitmapDescriptorFactory
//					.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
//		else if(y==13)
//			markerOptions[y].icon(BitmapDescriptorFactory
//					.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
//		else if(y==14)
//			markerOptions[y].icon(BitmapDescriptorFactory
//					.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
//		else if(y==15)
//			markerOptions[y].icon(BitmapDescriptorFactory
//					.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
//	}
//	
//		for(int y=0 ;y<nearPlaces.results.size();y++)
//		{
//			int temp = 7;
//			rectLine[y] = new PolylineOptions().width(30-temp);
//			if(y==0)
//				rectLine[y].color(Color.BLUE);
//			else if(y==1){
//				rectLine[y].color(Color.rgb(238,130,238));//VIOLET
//			}
//			else if(y==2)
//				rectLine[y].color(Color.CYAN);
//			else if(y==3)
//				rectLine[y].color(Color.YELLOW);
//			else if(y==4)
//				rectLine[y].color(Color.GREEN);
//			else if(y==5)
//				rectLine[y].color(Color.RED);
//			else if(y==6)
//				rectLine[y].color(Color.rgb(255,0,127));//ROSE
//			else if(y==7)
//				rectLine[y].color(Color.MAGENTA);
//			else if(y==8)
//				rectLine[y].color(Color.rgb(255,165,0));//ORANGE);
//			else if(y==9)
//				rectLine[y].color(Color.rgb(0,127,255));//HUE_AZURE;
//			else if(y==11)
//				rectLine[y].color(Color.rgb(153,0,204));
//			else if(y==12)
//				rectLine[y].color(Color.rgb(153,0,204));
//			else if(y==13)
//				rectLine[y].color(Color.rgb(153,0,204));
//			else if(y==14)
//				rectLine[y].color(Color.rgb(157,1,208));
//			else if(y==15)
//				rectLine[y].color(Color.rgb(150,2,214));
//
//		}

	int i1=0;
	int j=0;
	
	Place placeStart;
	Place placeEnd;

//	// check for null in case it is null
	if (nearPlaces.results != null) {
		// loop through all the places
		placeSize=nearPlaces.results.size();
//
		myPosition = new LatLng(latitude, longitude);
         markerOptions[placeIndex].position(myPosition);
         markerOptions[placeIndex].title(String.valueOf("thats u"));
         markerOptions[placeIndex].draggable(true);
         googleMap.addMarker(markerOptions[placeIndex]);
         placeIndex++;
//			
		for(j=0;j<SIZE;j++)
		{

             
			placeStart=nearPlaces.results.get(j);
			latitude = placeStart.geometry.location.lat; // latitude
			longitude =placeStart.geometry.location.lng; // longitude

		    fromPosition = new LatLng(latitude, longitude);

         	markerOptions[placeIndex].position(fromPosition);
         	markerOptions[placeIndex].title(String.valueOf(placeIndex));
         	markerOptions[placeIndex].draggable(true);
         	googleMap.addMarker(markerOptions[placeIndex]);
         	placeIndex++;
		}
//            
//		
		placeEnd=nearPlaces.results.get((0));
		latitude2 = placeEnd.geometry.location.lat; // latitude2
		longitude2 =placeEnd.geometry.location.lng; // longitude2
		
	    fromPosition = new LatLng(myPosition.latitude,myPosition.longitude);
		toPosition = new LatLng(latitude2, longitude2);
		
     	 getRoute[0] = new GetRouteTask();
     	 getRoute[0].execute();
		 try {
			docu[0]=getRoute[0].get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
			for(j=0;j<SIZE-1;j++)
			{	
				
					//line=j;	
					line=j+1;
					placeStart=nearPlaces.results.get(j);
					latitude = placeStart.geometry.location.lat; // latitude
					longitude =placeStart.geometry.location.lng; // longitude
					
					placeEnd=nearPlaces.results.get((j+1));
					latitude2 = placeEnd.geometry.location.lat; // latitude2
					longitude2 =placeEnd.geometry.location.lng; // longitude2
					
		    
				    fromPosition = new LatLng(latitude, longitude);
					toPosition = new LatLng(latitude2, longitude2);

	                try{
                 	 getRoute[j+1] = new GetRouteTask();
                 	 getRoute[j+1].execute();
					 docu[j+1]=getRoute[j+1].get();
	        		} catch (InterruptedException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		} catch (ExecutionException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
				
//				rectLine[line].add(fromPosition,toPosition);
//             	googleMap.addPolyline(rectLine[line]);

		}
	}
	
	
	
}
		protected boolean isRouteDisplayed() {
			return false;
		}
		  @Override
		    public boolean onCreateOptionsMenu(Menu menu) {
		        MenuInflater inflater = getMenuInflater();
		        inflater.inflate(R.menu.main, menu);
		 
		        return super.onCreateOptionsMenu(menu);
		    }

		  
		  public class GetRouteTask extends AsyncTask<String, Void, Document> {
	          
	           private ProgressDialog Dialog;
	           String response = "";
	           @Override
	           protected void onPreExecute() {
	        	   
	                 Dialog = new ProgressDialog(OpenMap.this);
	                 Dialog.setMessage("Loading route...");
	                 Dialog.show();
	           }
	           
	           @Override
	           protected Document doInBackground(String... urls) {
	                 //Get All Route values
	        	   		
	        	   		document = v2GetRouteDirection[line].getDocument(fromPosition, toPosition, GMapV2GetRouteDirection.MODE_DRIVING);
	                    response = "Success";
	                 return document;
	           }

	           @Override
	           protected void onPostExecute(Document result) {
	        	 //  googleMap.clear();
//	                 if(response.equalsIgnoreCase("Success"))
//	                 {	                	 
//		                 ArrayList<LatLng> directionPoint = v2GetRouteDirection[line].getDirection(document);
//		                 // rectLine[line] = new PolylineOptions().width(10).color(Color.BLUE);
//		                             
//		                 //W-=7;
//		                 //googleMap.addMarker(marker);
//		                 //updateRoute(directionPoint);
//		                 for (int i = 0; i < directionPoint.size(); i++) 
//		                 {
//		                       rectLine[line].add(directionPoint.get(i));    
//		                 }
//		                 googleMap.addPolyline(rectLine[line]);
//		                
//		                 markerOptions[placeIndex]=new MarkerOptions();
//		                 markerOptions[placeIndex].position(toPosition);
//		                 markerOptions[placeIndex].title(String.valueOf(placeIndex));
//		                  markerOptions[placeIndex].draggable(true);
//		                  googleMap.addMarker(markerOptions[placeIndex]);
//		                  placeIndex++;
		                // sima=!sima;
		               // directionPoint.clear();
	//                 }	    
	                 
	                 Dialog.dismiss();
	                 
	           }
	           
	     }
	     @Override
	     protected void onStop() {
	           super.onStop();
	           finish();
	     }
	     


		
}
