package googleplacesandmaps;

import java.io.Serializable;
import java.sql.Time;

import com.google.android.gms.maps.model.LatLng;
import com.google.api.client.util.Key;



public class Place2  implements Serializable
{
	
	@Key
	public String idKey;
	
	@Key
	public String nameKey;
	
	  private int id;
	  private String name;
	  private String adress ;
	  private Time startH;
	  private Time endH;
	  private Time stay ;
	  private double lat;
	  private double lang;
		int serialNum;
	 // private LatLng latlang;
	  
	  
	    public Place2(){}
	     ///////////////////////////////////////////////////////////test
		Place2(String _name,String _add,Time SH,Time EH,Time _stay,int _serialNum){
			name=_name;
			adress=_add;
			startH=SH;
			endH=EH;
			stay=_stay;
			serialNum=_serialNum;
		
		}
		Place2(Place2 k){
			this.adress=k.adress;
			this.endH=new Time(k.endH.getTime());
			this.name=k.name;
			this.serialNum=k.serialNum;
			this.stay=k.stay;
			this.startH=new Time (k.startH.getTime());
			
		}
	    
	    //////////////////////////////////////////////////////////////test
	    public Place2(int id, String name ,String Adress,String StartH ,String EndH,String Stay,double lat ,double lang){
	        this.id = id;
	        this.name = name;
	        this.adress=Adress;
	        this.startH=Time.valueOf(StartH);
	        this.endH=Time.valueOf(EndH);
	        this.stay=Time.valueOf(Stay);
	        this.lat=lat;
	        this.lang=lang;
	       // this.latlang = new LatLng(lat, lang) ;
	        
	    }
	    public Place2(int id, String name ,String Adress,double lat ,double lang){
	        this.id = id;
	        this.name = name;
	        this.adress=Adress;
	        this.lat=lat;
	        this.lang=lang;
	       // this.latlang = new LatLng(lat, lang) ;
	        
	    }
	    public Place2(int id, String name ,String Adress){
	        this.id = id;
	        this.name = name;
	        this.adress=Adress;
	  
	      
	        
	    }
	    public Place2(int id, String name ){
	        this.id = id;
	        this.name = name;

	    }
	     
	    public void setId(int id){
	        this.id = id;
	    }
	     
	    public void setName(String name){
	        this.name = name;
	    }
	     
	    public int getId(){
	        return this.id;
	    }
	     
	    public String getName(){
	        return this.name;
	    }

		public String getAdress() {
			return adress;
		}

		public void setAdress(String adress) {
			this.adress = adress;
		}

		public Time getStartH() {
			return startH;
		}

		public void setStartH(Time startH) {
			this.startH = startH;
		}

		public Time getEndH() {
			return endH;
		}

		public void setEndH(Time endH) {
			this.endH = endH;
		}

		public Time getStay() {
			return stay;
		}

		public void setStay(Time stay) {
			this.stay = stay;
		}

		public double getLat() {
			return lat;
		}

		public void setLat(double lat) {
			this.lat = lat;
		}

		public double getLang() {
			return lang;
		}

		public void setLang(double lang) {
			this.lang = lang;
		}

//		public LatLng getLatlang() {
//			return latlang;
//		}

//		public void setLatlang(LatLng latlang) {
//			this.latlang = latlang;
//		}
}
