package googleplacesandmaps;
import java.lang.Boolean;
import java.sql.Time;
import java.util.Date;
//static final SIZE=6;
import java.util.List;

import org.w3c.dom.Document;
public class Path {
	public final static int size =6;
	public final static int pop =300;
	 public  Boolean priority,feasible;
	private Time langht;
	private Place2 [] path ;
	private Time [] [] dis;//=new Time [size][size];
	 GMapV2GetRouteDirection  v2GetRouteDirection;
	
	
	public Path(List<Place2> place2List,Document [] docu)
	{
		priority=false;
		feasible=false;
		langht=new Time(0,0,0);
		path=new Place2[place2List.size()];
		
		v2GetRouteDirection = new GMapV2GetRouteDirection();
		
		dis = new Time[place2List.size()][place2List.size()]; 
		
		for(int i = 0; i<place2List.size();i++)
		{			
			path[i]=new Place2(place2List.get(i));
			//dis[i]=v2GetRouteDirection.getDistanceValue(docu[i]);
		}		
		
		int rand = ( (int)( 1 + (Math.random() * (10 - 1))));
		
		//int d = 0 ;
		for(int i =0 ; i < place2List.size();i++)
		{
			dis[i] = new Time[place2List.size() - i + 1 ];///only upper triangle of the matrix
			
			for(int j = i+1;j<place2List.size(); j++)
			{
				dis[i][j]=new Time(0,rand,0);
			}
				
		}
	}
	
	@SuppressWarnings("deprecation")
	Path(){
		
		priority=false;
	//	necessity=false;
		feasible=false;
		langht=new Time(0,0,0);
		path=new Place2[size];
			path[2]=new Place2("1", "jhjjkh ",new Time(10,0,0), new Time(14,0,0), new Time(0,30,0),1);
			path[4]=new Place2("2", "jhjjkh ",new Time(10,0,0), new Time(17,0,0), new Time(0,30,0),2);
			path[3]=new Place2("3", "jhjjkh ",new Time(11,0,0), new Time(15,0,0), new Time(0,30,0),3);
			path[0]=new Place2("4", "jhjjkh ",new Time(10,0,0), new Time(11,0,0), new Time(0,30,0),4);
			path[1]=new Place2("5", "jhjjkh ",new Time(15,0,0), new Time(17,0,0),new Time(0,30,0),5);
			path[5]=new Place2("6", "jhjjkh ",new Time(9,30,0), new Time(10,30,0), new Time(0,30,0),6);
			
		//	dis=new Time[6];
		dis[0]=new Time [6];
		dis[1]=new Time [6];
		dis[2]=new Time [6];
		dis[3]=new Time [6];
		dis[4]=new Time [6];
		dis[5]=new Time [6];
			dis[0][0]=null;	
			dis[0][1]=new Time(0,7,0);
			dis[0][2]=new Time(0,7,0);
			dis[0][3]=new Time(0,9,0);
			dis[0][4]=new Time(0,8,0);
			dis[0][5]=new Time(0,10,0);
			
			dis[1][1]=null;	
			dis[1][2]=new Time(0,7,0);
			dis[1][3]=new Time(0,3,0);
			dis[1][4]=new Time(0,7,0);
			dis[1][5]=new Time(0,4,0);
			dis[1][0]=new Time(0,7,0);
			
			dis[2][2]=null;
			dis[2][0]=new Time(0,7,0);
			dis[2][1]=new Time(0,7,0);
			dis[2][3]=new Time(0,10,0);
			dis[2][4]=new Time(0,9,0);
			dis[2][5]=new Time(0,10,0);
			
			dis[3][3]=null;
			dis[3][0]=new Time(0,9,0);
			dis[3][1]=new Time(0,3,0);
			dis[3][2]=new Time(0,10,0);
			dis[3][4]=new Time(0,7,0);
			dis[3][5]=new Time(0,3,0);
			
			dis[4][4]=null;	
			dis[4][0]=new Time(0,8,0);
			dis[4][1]=new Time(0,7,0);
			dis[4][2]=new Time(0,9,0);
			dis[4][3]=new Time(0,7,0);
			dis[4][5]=new Time(0,6,0);
			
			dis[5][5]=null;
			dis[5][0]=new Time(0,10,0);
			dis[5][1]=new Time(0,4,0);
			dis[5][2]=new Time(0,10,0);
			dis[5][3]=new Time(0,3,0);
			dis[5][4]=new Time(0,6,0);
		
	}
	Path(Path p){
		this.feasible=p.feasible;
		this.langht=new Time(p.langht.getTime());
	//	this.necessity=p.necessity;
		this.priority=p.priority;
		
		
		this.dis= new Time[p.dis.length][p.dis[0].length];
		for(int i=0; i<p.dis.length; i++){
			this.dis[i]=new Time [p.dis[i].length];	
			for(int j=0; j<p.dis[i].length; j++){
				if (i!=j)
				this.dis[i][j] = new Time(p.dis[i][j].getTime());
			}
		}
		this.path=new Place2[p.path.length];
		for(int i=0; i<p.path.length; i++){
			this.path[i]=new Place2 (p.path[i]);	
		}	
	}
	public void setPriority(boolean TF){
	
		priority=TF;
	}
	
	public void setFeasible(boolean TF){
		
		this.feasible=TF;
	}
	
	public void setlanght(Time NewLanght){
		
		langht=new Time(NewLanght.getTime());;
	}
	public void setPath(Place2 [] NewPath){
		
		path=NewPath;
	}
	public boolean getFeasible()
	{
		return this.feasible;
	}

	public boolean getPriority()
	{
		return priority;
	}
	public Time [][] getDis()
	{
		return dis;
	}
	public Time getLenght()
	{
		return langht;
	}
	public Place2 [] getPath()
	{
		return path;
	}
	public boolean CheckFeasible()
	{
		int i;
		Time time=new Time(0,0,0);
		time=path[0].getStartH();
		for(i=0;i<size-1;i++)
		{
			time=addTime(addTime(time,path[i].getStay()),dis[path[i].serialNum-1][path[i+1].serialNum-1]);
			if (path[i+1].getStartH().after(time))
				time=path[i+1].getStartH();
			
			if(time.after(path[i+1].getEndH()))
				return false;
			 
		}

		return true;
		
	}
	public boolean checkPriority(int [][] p)
	{
		int i=0,k=0,j=0;
		boolean flag=false;
		while(p[i][j]!=0)
		{
			
				
				while(this.path[k].serialNum!=p[i][0])
					k++;
				if(k==size-1)
				return false;
				
				k++;
				while(this.path[k].serialNum!=p[i][1] && k<=size-1)
				{
						if (k==size-1)
						{
						if(this.path[k].serialNum!=p[i][1])	
						 return false;
						else break;
						}
					else k++;
				}
				if (k==size)
					return false;

			i++;k=0;	
		}
		
	return true;
	}
	@SuppressWarnings("deprecation")
	public Time addTime(Time a,Time b)
	{@SuppressWarnings("deprecation")
		int a_h=a.getHours(),a_m=a.getMinutes(),a_s=a.getSeconds();
		int b_h=b.getHours(),b_m=b.getMinutes(),b_s=b.getSeconds();
		Time result = new Time(0,0,0);
		if (a_s+b_s>59)
		{
			result.setSeconds((a_s+b_s)%60);
			a_m+=1;
		}
		else result.setSeconds(a_s+b_s);
		if  (a_m+b_m>59)
		{
			result.setMinutes((a_m+b_m)%60);
			a_h+=1;
		}
		else result.setMinutes(a_m+b_m);
		if (a_h+b_h>59){
			result.setHours((a_m+b_m)%60);
			result.setDate(1);
		}
		else 	result.setHours(a_h+b_h);
		return result;
	}
	public  void SRTF(){
		Place2 temp;
		this.SortByStartH();
		int j=0,n,n2;
		while(j!=size-1)
		{
		while(path[j].getStartH().equals(path[j+1].getStartH()) && j<size-2)
			j++;
		n=j;
		while(n!=0){
			n2=0;
			for(int i=0;i<j;i++){
			if (path[i].getStartH().equals(path[i+1].getStartH()))
				if (path[i].getEndH().after(path[i+1].getEndH())){
						temp=path[i+1];
						path[i+1]=path[i];
						path[i]=temp;	
						n2=i;
				}
			}
			n=n2;
		}
		
		j++;
		}
		
	}
	public Place2 getBySerialNumber(char c)
	{
		int number2=Character.getNumericValue(c);
		for(int i=0;i<size;i++)	
		{
			if (this.path[i].serialNum==number2)
				return this.path[i];
		}
		return null;
	}
	public void computeLenght()
	{
		int i;
		//Time time=new Time(0,0,0);
		this.langht=path[0].getStartH();
		for(i=0;i<size-1;i++)
		{
			this.langht=addTime(addTime(this.langht,path[i].getStay()),dis[path[i].serialNum-1][path[i+1].serialNum-1]);
			if (path[i+1].getStartH().after(this.langht))
				this.langht=path[i+1].getStartH();
		}
		this.langht=addTime(this.langht,path[i].getStay());
	}
	public  void InitPopulation(Path [] pop,int[][] priority)
	{
	for(int i=0;i<pop.length;i++)
	{
		pop[i].priority=checkPriority(priority);
		if(pop[i].CheckFeasible()==true)
		
		pop[i].feasible=true;
		
		pop[i].computeLenght();
		
	}
		
	}
	 public  Path [] MakeRandomPopulation(){
		
		String [] ans;
		int k=0;
		Path [] p=new Path [pop];
		ans=Path.permString(new String("123456"));
			for(int i=0;i<pop-1;i++)
				{int m= 1 + (int)(Math.random()*700);
					Path temp=new Path();
					while(k<size)
					{
						temp.path[k]=new Place2(getBySerialNumber(ans[m].charAt(k)));
						k++;
					}
						
				p[i]=new Path(temp);
				k=0;
				}
		Path temp=new Path();
		temp.SRTF();
		p[pop-1]=new Path(temp);
return p;
	}
	public String getPathString()
	{
		String p=new String ();
		for(int i=0;i<size;i++)
			p+=" "+this.path[i].serialNum;
		return p;
	}
	public void SortByStartH()
	{
		Place2 temp;
		int n=size,n2;
		while(n!=0)
		{
			n2=0;
				for(int j=1;j<size;j++)
				{
					if (path[j-1].getStartH().after(path[j].getStartH()))
					{
						temp=path[j];
						path[j]=path[j-1];
						path[j-1]=temp;
						n2=j;
					}
				}
			n=n2;
		}
	}
	public static String[] permString(String s) {
		String [] ans = {""};
		if (s.length()>0) {
		String[] tmp = permString(s.substring(1));
		ans = new String[s.length()*tmp.length];
		for (int i=0; i<tmp.length; i=i+1)
		for (int j=0; j<s.length(); j=j+1) 
		ans[i*s.length()+j] = 
		tmp[i].substring(0,j) + s.charAt(0) + tmp[i].substring(j);
		}
		return ans;
	}
	
static public Path []  SortPopulationByLenghtPath(Path [] pop)
{
	Path temp;
	int n=pop.length;
	boolean flag;
	do
	{
		flag=false ;
		n=n-1;
		//n2=0;
			for(int j=0;j<pop.length-1;j++)
			{
				if (pop[j].langht.before((pop[j+1].langht)))
				{
					temp=new Path(pop[j+1]);
					pop[j+1]=pop[j];
					pop[j]=temp;
					flag=true;
				}
			}
		
	}while(flag);
	return pop;
}
Place2 [] Duplicate(Path input)
{
	Place2 [] NotExist=new Place2[size];
	boolean flag=false;
	int k=0;
	for(int i=0;i<size;i++)
	{
		for(int j=0;j<size;j++)
			
			if (input.path[i].serialNum==this.path[j].serialNum)
				flag=true;
		if (!flag)
		{
			NotExist[k]=new Place2(input.path[i]);
			k++;
		}
			flag=false;
		}
	return NotExist;
}
void changeDuplicate(Place2 [] NotExist)
{int k=0;
	for(int i=0;i<size;i++)
	{
		for(int j=i+1;j<size;j++)
		{
			if (this.path[i].serialNum==this.path[j].serialNum){
				this.path[i]=new Place2(NotExist[k]);
				k++;
			}
		}
	}
			
}
Path [] MakeCrossover(Path [] pop){
	Place2 [] NotExist=new Place2 [size];
	Path [] Generation1=new Path [pop.length];
	int count1=0,count2=0;
	for(int i=0;i<pop.length;i++)
		Generation1[i]=new Path();
	int k=0;
	for(int i=0;i<pop.length-2;i+=2){
		
		for(int j=0;j<size;j++)
			{
				if (j==2 || j==3)
				{
					Generation1[k].path[j]=pop[i+1].path[j];
					
					Generation1[k+1].path[j]=pop[i].path[j];
					
				}
				else
				{
					Generation1[k].path[j]=pop[i].path[j];
					Generation1[k+1].path[j]=pop[i+1].path[j];
				}
			}
	NotExist=Generation1[k].Duplicate(pop[i]);
	Generation1[k].changeDuplicate(NotExist);
	NotExist=Generation1[k+1].Duplicate(pop[i]);
	Generation1[k+1].changeDuplicate(NotExist);
	Generation1[k].feasible=Generation1[k].CheckFeasible();
//	Generation1[k+1].feasible=Generation1[k+1].CheckFeasible();
	Generation1[k].computeLenght();
	Generation1[k+1].feasible=Generation1[k+1].CheckFeasible();
	Generation1[k+1].computeLenght();
	
if(pop[i+1].feasible.equals(true))
	count1++;
	if (pop[i].feasible.equals(true) )
		count1++;
	if( Generation1[k].feasible.equals(true))
		count2++;
	if (Generation1[k+1].feasible.equals(true))
	count2++;
	//{
	//	System.out.println(pop[i].getPathString());
	//	System.out.println(pop[i+1].getPathString());
	//	System.out.println(son1.getPathString());
	//	System.out.println(son2.getPathString());
//	}
	k+=2;
	}
System.out.println("---------------------------------------");
System.out.println(count1);	
System.out.println(count2);
System.out.println("---------------------------------------");
return Generation1;
}
}