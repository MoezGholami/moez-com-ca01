public class Analizer_semantic{
	
	private	ArrayList<Coolclass> classlist;
	public boolean hasMain(){
		for(int i=0;i<classlist.length();i++){
			if(classlist.get(i).name=="Main"){
				return true;
			}
		}
		return false;
	}
	public void addClass(String Cname,String Pname=null){
		
	}
	public 

}


class Coolclass {
	Coolclass parent;
	String name;
	static Coolclass coolobject = new Coolclass(null,"Object");
	Coolclass(Coolclass p,String n){
		name = n;
		parent = p;
	}
}
