import java.util.ArrayList;


public class AnalizerSemantic {
	private ArrayList<CoolClass> classList;
	private ArrayList<CoolClass> expectingClassList;
	private static AnalizerSemantic Instance = new AnalizerSemantic();
	
	public static AnalizerSemantic getInstance() {
		return Instance;
	}

	private AnalizerSemantic() {
		classList = new ArrayList<CoolClass>();
		classList.add(CoolClass.coolObject);
	}

	public boolean hasMain() {
		return hasClassWithName("Main", classList);
	}

	private void addExpectingClass(String Cname) {
		assert !hasClassWithName(Cname, classList);
		if (hasClassWithName(Cname, expectingClassList))
			return;
		expectingClassList.add(new CoolClass(Cname, null));
	}

	public void addClass(String Cname, String Pname) throws LoopException,DuplicateClassX {
		if (hasClassWithName(Cname, classList))
			throw new DuplicateClassX(Cname);
		if (hasClassWithName(Cname, expectingClassList)) {
			migrate2ClassList(Cname, Pname);
			return;
		}
		CoolClass incomming = null;
		if (hasClassWithName(Pname, classList))
			incomming = new CoolClass(Cname, findCoolClassByName(Pname,
					classList));
		else {
			addExpectingClass(Pname);
			incomming = new CoolClass(Cname, findCoolClassByName(Pname,
					expectingClassList));
		}
		if (hasLoopWith(incomming.parent, incomming.name))
			throw new LoopException(incomming.name);
		classList.add(incomming);
	}

	private void migrate2ClassList(String Cname, String Pname) throws LoopException {
		CoolClass migrating = findCoolClassByName(Cname, expectingClassList);
		expectingClassList.remove(migrating);
		if (hasClassWithName(Pname, classList))
			migrating.parent = findCoolClassByName(Pname, classList);
		else {
			addExpectingClass(Pname);
			migrating.parent = findCoolClassByName(Pname, expectingClassList);
		}
		if (hasLoopWith(migrating.parent, migrating.name))
			throw new LoopException(migrating.name);
		classList.add(migrating);
	}
	
	private boolean hasLoopWith(CoolClass c, String Cname) {
		if (c == null)
			return false;
		if (c.name == Cname)
			return true;
		return hasLoopWith(c.parent, Cname);
	}

	private CoolClass findCoolClassByName(String n, ArrayList<CoolClass> list) {
		for (int i = 0; i < list.size(); i++)
			if (list.get(i).name.equals(n))
				return list.get(i);
		return null;
	}

	private boolean hasClassWithName(String n, ArrayList<CoolClass> list) {
		return findCoolClassByName(n, list) != null;
	}

	public static class LoopException extends Throwable{
		private static final long serialVersionUID = 100L;
		private String className;
		public LoopException(String n){
			className = n;
		}
		public String toString(){
			return "The following class caused a loop: "+className+"\n";
		}
	}
	
	public static class DuplicateClassX extends Throwable{
		private static final long serialVersionUID = 101L;
		private String className;
		public DuplicateClassX(String n) {
			className = n;
		}
		public String toString(){
			return "The following class was already decleared : "+className+"\n";
		}
	}
	
	private static class CoolClass {
		CoolClass parent;
		String name;
		public static final CoolClass coolObject = new CoolClass("Object");
		private CoolClass(String n){
			parent = null;
			name = n;
		}
		CoolClass(String n,CoolClass p){
			name = n;
			if(p == null)
				parent = coolObject;
			else 
				parent = p;
		}
	}
}
