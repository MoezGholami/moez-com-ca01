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
		expectingClassList = new ArrayList<CoolClass>();
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
		if(Pname==null)
			Pname = CoolClass.coolObject.name;
		if (hasClassWithName(Cname, classList))
			throw new DuplicateClassX(Cname);
		if (hasClassWithName(Cname, expectingClassList)) {
			migrate2ClassList(Cname, Pname);
			return;
		}
		CoolClass incomming = null;
		if (hasClassWithName(Pname, classList))
			incomming = new CoolClass(Cname, findCoolClassByName(Pname,classList));
		else {
			addExpectingClass(Pname);
			incomming = new CoolClass(Cname, findCoolClassByName(Pname,expectingClassList));
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

	public void commitFirstPass() throws NoMainException, DanglingClassException{
		if(!hasMain())
			throw new NoMainException();
		if(expectingClassList.size()!=0)
			throw new DanglingClassException(expectingClassList);
	}

	public static abstract class SemanticError extends Throwable {
		private static final long serialVersionUID = 100L;
		}

	public static class LoopException extends SemanticError{
		private static final long serialVersionUID = 101L;
		private String className;
		public LoopException(String n){
			className = n;
		}
		public String toString(){
			return "The following class caused a loop: "+className+"\n";
		}
	}
	
	public static class DuplicateClassX extends SemanticError{
		private static final long serialVersionUID = 102L;
		private String className;
		public DuplicateClassX(String n) {
			className = n;
		}
		public String toString(){
			return "The following class was already decleared : "+className+"\n";
		}
	}
	
	public static class DanglingClassException extends SemanticError{
		private static final long serialVersionUID = 103L;
		ArrayList<CoolClass> list;
		public DanglingClassException(ArrayList<CoolClass> l)
		{
			list=l;
		}
		public String toString(){
			String result;
			result="The classes with these name are expected:\n";
			for(int i=0; i<list.size(); ++i)
				result=result+list.get(i).name+", ";
			return result;
		}
	}
	
	public static class NoMainException extends SemanticError{
		private static final long serialVersionUID = 104L;
		public String toString(){
			return "No Main class was found.\n";
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
