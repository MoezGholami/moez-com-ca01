import java.security.KeyException;
import java.util.ArrayList;

public class AnalizerSemantic {
	private ArrayList<CoolClass> classList;
	private ArrayList<CoolClass> expectingClassList;
	private boolean SemanticErrorFound;
	private int PassedTimes;
	private static AnalizerSemantic Instance = new AnalizerSemantic();
	private static String KeyWords[] = {"class", "else", "fi", "if", "in", "inherits", "isvoid", "let", "loop", "pool", "then", "while", "case", "esac", "new", "of", "not", "true", "false"};
	
	public static AnalizerSemantic getInstance() {
		return Instance;
	}

	private AnalizerSemantic() {
		classList = new ArrayList<CoolClass>();
		addPrimitiveClasses(classList);
		expectingClassList = new ArrayList<CoolClass>();
		SemanticErrorFound=false;
		PassedTimes=0;
	}

	public boolean hasMain() {
		return hasClassWithName("Main", classList);
	}

	public boolean hasSemanticError()
	{
		return SemanticErrorFound;
	}

	public void addClass(String Cname, String Pname) throws LoopException,DuplicateClassX, KeyWordName {
		if(PassedTimes!=0)
			return ;
		if(Pname==null)
			Pname = CoolClass.coolObject.name;
		if(isKeyWord(Pname))
			throw new KeyWordName(Pname, this);
		if(isKeyWord(Cname))
			throw new KeyWordName(Cname, this);
		if (hasClassWithName(Cname, classList))
			throw new DuplicateClassX(Cname, this);
		if (hasClassWithName(Cname, expectingClassList)) {
			migrate2ClassList(Cname, Pname);
			return;
		}
		CoolClass incomming = new CoolClass(Cname,getPossibleClass(Pname));
		if (CoolClass.hasLoopWith(incomming.Ancestor, incomming.name))
			throw new LoopException(incomming.name, this);
		classList.add(incomming);
	}

	public ArrayList<Integer> addMethod(String OwnerClassName, String MethodName, String MethodTypeName, ArrayList<UnrecognizedTypeVar> RawArgs)
			throws DuplicateVariableName, DuplicateMethodName, KeyWordName
	{
		if(PassedTimes!=0)
			return null;
		assert !isKeyWord(OwnerClassName);
		assert hasClassWithName(OwnerClassName, classList);
		if(isKeyWord(MethodName))
			throw new KeyWordName(MethodName, this);
		ArrayList<Variable> args=convertRawVars2Vars(RawArgs);
		CoolClass OwnerCoolClass=getPossibleClass(OwnerClassName);
		Method inComingMethod= new Method(MethodName, getPossibleClass(MethodTypeName), OwnerCoolClass, args);
		if(hasMethodWithName(inComingMethod.Name, OwnerCoolClass.MethodList)
				|| duplicateAndNotOverrided(inComingMethod, inComingMethod.OwnerClass.Ancestor))
			throw new DuplicateMethodName(MethodName, this);
		OwnerCoolClass.MethodList.add(inComingMethod);
		return inComingMethod.MainScope.ScopeKey;
	}

	public void addField(String ownerClassName, UnrecognizedTypeVar rawfield) throws DuplicateVariableName, KeyWordName
	{
		if(PassedTimes!=0)
			return ;
		assert !isKeyWord(ownerClassName);
		assert hasClassWithName(ownerClassName, classList);
		Variable inComingField=new Variable(rawfield, this);
		CoolClass owner=getPossibleClass(ownerClassName);
		if(hasFieldOrInherited(inComingField.Name, owner))
			throw new DuplicateVariableName(inComingField.Name, this);
		owner.Fields.add(inComingField);
	}

	public void addScope(String ownerClassName, String motherMethodName, ArrayList<Integer> currentScopeKey)
	{
		if(PassedTimes!=0)
			return ;

		assert !isKeyWord(ownerClassName);
		assert !isKeyWord(motherMethodName);
		CoolClass owner=findCoolClassByName(ownerClassName, classList);
		assert owner!=null;
		Method mother=findMethodByName(motherMethodName, owner.MethodList);
		assert mother!=null;
		Scope parent=getScopeInMethod(mother, currentScopeKey);
		assert parent!=null;

		Scope inComingScope=Scope.generateChildScope4Scope_NotChildLinking(parent);
		assert !haveScopeWithKeyInMethod(mother, inComingScope.ScopeKey);
		currentScopeKey.add(currentScopeKey.size()-1, parent.Children.size());
		parent.Children.add(inComingScope);
		return ;
	}

	public void addVariable2Scope(String ownerClassName, String motherMethodName,
			ArrayList<Integer> currentScopeKey, UnrecognizedTypeVar rawfield) throws DuplicateVariableName, KeyWordName
	{
		if(PassedTimes!=0)
			return ;

		assert !isKeyWord(ownerClassName);
		assert !isKeyWord(motherMethodName);
		CoolClass owner=findCoolClassByName(ownerClassName, classList);
		assert owner!=null;
		Method mother=findMethodByName(motherMethodName, owner.MethodList);
		assert mother!=null;
		Scope s=getScopeInMethod(mother, currentScopeKey);
		assert s!=null;

		Variable inComingVariable=new Variable(rawfield, this);
		if(hasVariableWithName(inComingVariable.Name, mother.args))
			throw new DuplicateVariableName(inComingVariable.Name, this);
		Variable duplicateScopeVar=findVariableByName(inComingVariable.Name, s.VarList);
		if(duplicateScopeVar!=null)
			s.VarList.remove(duplicateScopeVar);//see page 11, let, paragraph 3
		s.VarList.add(inComingVariable);
	}

	public ArrayList<Integer> generateNewMainScopeKey_AfterPass0()
	{
		if(PassedTimes==0)
			return null;
		Method temp=new Method(null, null, null, null);
		return (new ArrayList<Integer>(temp.MainScope.ScopeKey));
	}

	public void updateKeyWhenOpeningScope(String ownerClassName, String motherMethodName, ArrayList<Integer> currentScopeKey)
	{
		if(PassedTimes==0)
			return ;

		assert !isKeyWord(ownerClassName);
		assert !isKeyWord(motherMethodName);
		CoolClass owner=findCoolClassByName(ownerClassName, classList);
		assert owner!=null;
		Method mother=findMethodByName(motherMethodName, owner.MethodList);
		assert mother!=null;
		Scope parent=getScopeInMethod(mother, currentScopeKey);
		assert parent!=null;

		currentScopeKey.add(currentScopeKey.size()-1, element);//TODO: FINISH THIS
	}

	public void updateKeyWhenClosingScope(ArrayList<Integer> ScopeKey)
	{
		assert !(ScopeKey==null || ScopeKey.size()==0);
		if(ScopeKey.size()==1)//Main scope is closing
		{
			assert ScopeKey.get(0)==Scope.EndOfScopeSearchIdentifier;
			ScopeKey.remove(0);
			return ;
		}
		ScopeKey.remove(ScopeKey.size()-2);
	}
	
	public void commitFirstPass()
			throws NoMainException, DanglingClassException, DuplicateMethodName, FirstPassUnsuccessful
	{
		if(PassedTimes!=0)
			return ;
	
		if(!hasMain())
			throw new NoMainException(this);
		if(expectingClassList.size()!=0)
			throw new DanglingClassException(expectingClassList, this);
		for(int i=0; i<classList.size(); ++i)
			checkDuplicateMethods(classList.get(i));
		if(SemanticErrorFound)
			throw new FirstPassUnsuccessful(this);
		PassedTimes=1;
	}

	public String joinOf_TypeName(String typename1, String typename2)
	{
		CoolClass a=findCoolClassByName(typename1, classList);
		CoolClass b=findCoolClassByName(typename2, classList);
		assert a!=null;
		assert b!=null;
		return CoolClass.FirstCommonFatherClass(a, b).name;
	}

	public String joinOf_TypeName(ArrayList<String> types)
	{
		if(types==null || types.size()==0)
			return null;
		String result=types.get(0);
		for(int i=1; i<types.size(); ++i)
			result=joinOf_TypeName(result, types.get(i));
		return result;
	}

	public String LookUpVarType(String ownerClassName, String motherMethodName, ArrayList<Integer> currentScopeKey,
			String searchingID) throws UndefinedVar
	{
		CoolClass owner=findCoolClassByName(ownerClassName, classList);
		assert owner!=null;
		Method mother=findMethodByName(motherMethodName, owner.MethodList);
		assert mother!=null;
		Scope s=getScopeInMethod(mother, currentScopeKey);
		assert s!=null;

		String result=null;
		Variable var;

		result=searchVarTypeInScope(searchingID, s);
		if(result!=null)
			return result;
		var=findVariableByName(searchingID, mother.args);
		if(var!=null)
			return var.Type.name;
		var=findVariableByName(searchingID, owner.Fields);
		if(var!=null)
			return var.Type.name;

		throw new UndefinedVar(searchingID, this);
	}
	
	public Scope getScopeByMethod(Method m, ArrayList<Integer> key){
		if(m==null || key==null || key.size()==0)
			return null;
		return getScopeInScope(m.MainScope, key);
	}
	
	private Scope getScopeInMethod(Method m, ArrayList<Integer> key)
	{
		if(m==null || key==null || key.size()==0)
			return null;
		return getScopeInScope(m.MainScope, key);
	}

	private boolean haveScopeWithKeyInMethod(Method m, ArrayList<Integer> key)
	{
		return getScopeInMethod(m, key)!=null;
	}

	private boolean haveScopeWithKeyInScope(Scope s, ArrayList<Integer> key)
	{
		return getScopeInScope(s, key)!=null;
	}

	private Scope getScopeInScope(Scope s, ArrayList<Integer> key)
	{
		ArrayList<Integer> terminatingKey=new ArrayList<Integer>(key);
		return getScopeInScope_TerminatingKey(s, terminatingKey);
	}

	private Scope getScopeInScope_TerminatingKey(Scope s, ArrayList<Integer> key)
	{
		if(key==null || key.size()==0)
			return null;
		if(key.size()==1)
			if(key.get(0)==Scope.EndOfScopeSearchIdentifier)
				return s;
			else
				return null;
		int nextBranch=key.get(0);
		if(nextBranch >= s.Children.size())
			return null;
		key.remove(0);
		return getScopeInScope(s.Children.get(nextBranch), key);
	}
	
	private void migrate2ClassList(String Cname, String Pname) throws LoopException {
		CoolClass migrating = findCoolClassByName(Cname, expectingClassList);
		expectingClassList.remove(migrating);
		try {
			migrating.Ancestor = getPossibleClass(Pname);
		} catch (KeyWordName e) {
			assert false;
		}
		if (CoolClass.hasLoopWith(migrating.Ancestor, migrating.name))
			throw new LoopException(migrating.name, this);
		classList.add(migrating);
	}

	private void addExpectingClass(String Cname) {
		assert !hasClassWithName(Cname, classList);
		if (hasClassWithName(Cname, expectingClassList))
			return;
		expectingClassList.add(new CoolClass(Cname, null));
	}

	private CoolClass getPossibleClass(String cname) throws KeyWordName
	{
		if(isKeyWord(cname))
			throw new KeyWordName(cname, this);
		if(hasClassWithName(cname, classList))
			return findCoolClassByName(cname, classList);
		addExpectingClass(cname);
		return findCoolClassByName(cname, expectingClassList);
	}

	private CoolClass findCoolClassByName(String n, ArrayList<CoolClass> list) {
		for (int i = 0; i < list.size(); i++)
			if (list.get(i).name.equals(n))
				return list.get(i);
		return null;
	}

	private Method findMethodByName(String n,ArrayList<Method> list){
		for(int i=0;i<list.size();i++)
			if(list.get(i).Name.equals(n))
				return list.get(i);
		return null;
	}
	
	private boolean hasClassWithName(String n, ArrayList<CoolClass> list) {
		return findCoolClassByName(n, list) != null;
	}

	private boolean hasMethodWithName(String n, ArrayList<Method> list){
		return findMethodByName(n,list) != null;
	}

	private boolean duplicateAndNotOverrided(Method m, CoolClass C)
	{
		if(C==null)
			return false;
		if(hasMethodWithName(m.Name, C.MethodList))
			if(OverridedFrom(m,findMethodByName(m.Name, C.MethodList)))
				return false;
			else
				return true;
		return duplicateAndNotOverrided(m, C.Ancestor);
	}

	private boolean OverridedFrom(Method m, Method n)
	{
		if(m.OwnerClass==n.OwnerClass)
			return false;
		if(m.ReturnType!=n.ReturnType)
			return false;
		if(m.args.size()!=n.args.size())
			return false;
		for(int i=0; i<m.args.size(); ++i)
			if(!Variable.sameType(m.args.get(i), n.args.get(i)))
				return false;
		return true;
	}

	private void checkDuplicateMethods(CoolClass c) throws DuplicateMethodName
	{
		for(int i=0; i<c.MethodList.size(); ++i)
			if(duplicateAndNotOverrided(c.MethodList.get(i), c.Ancestor))
				throw new DuplicateMethodName(c.MethodList.get(i).Name, this);
	}

	private ArrayList<Variable> convertRawVars2Vars(ArrayList<UnrecognizedTypeVar> rawVars)
		throws DuplicateVariableName, KeyWordName
	{
		ArrayList<Variable> result=new ArrayList<Variable>();
		for(int i=0; i<rawVars.size(); ++i)
			if(hasVariableWithName(rawVars.get(i).Name, result))
				throw new DuplicateVariableName(rawVars.get(i).Name, this);
			else
				result.add(new Variable(rawVars.get(i),this));
		return result;
	}

	public Variable getVariableFromScope(String n,Scope s){
		if(s==null)
			return null;
		for(int i=0;i<s.VarList.size();i++)
			if(s.VarList.get(i).Name.equals(n))
				return s.VarList.get(i);
		return getVariableFromScope(n,s.Parent);
	}
	
	private Variable findVariableByName(String n,ArrayList<Variable> list){
		for(int i=0;i<list.size();i++)
			if(list.get(i).Name.equals(n))
				return list.get(i);
		return null;
	}

	private boolean hasVariableWithName(String n, ArrayList<Variable> list){
		return findVariableByName(n,list) != null;
	}

	private boolean hasFieldOrInherited(String vname, CoolClass c)
	{
		if(c==null)
			return false;
		if(hasVariableWithName(vname, c.Fields))
			return true;
		return hasFieldOrInherited(vname, c.Ancestor);
	}

	private void addPrimitiveClasses(ArrayList<CoolClass> clist)
	{
		clist.add(CoolClass.coolObject);
		clist.add(CoolClass.coolInt);
		clist.add(CoolClass.coolBool);
		clist.add(CoolClass.coolString);
		clist.add(CoolClass.coolIO);
	}

	private String searchVarTypeInScope(String varName, Scope s)
	{
		if(s==null)
			return null;
		Variable v=findVariableByName(varName,s.VarList);
		if(v!=null)
			return v.Type.name;
		return searchVarTypeInScope(varName, s.Parent);
	}

	private static boolean isKeyWord(String s)
	{
		if(s==null)
			return false;
		String sl=s.toLowerCase();
		if(sl.equals("true") || sl.equals("false"))
			if(s.charAt(0)=='T' || s.charAt(0)=='F')
				return false;
		for(int i=0; i<KeyWords.length; ++i)
			if(sl.equals(KeyWords[i].toLowerCase()))
				return true;
		return false;
	}
	
	public static class UnrecognizedTypeVar
	{
		String Name;
		String TypeName;
		public UnrecognizedTypeVar(String n, String tn)	{ Name=n; TypeName=tn; }
	}

	private static class Variable
	{
		String Name;
		CoolClass Type;
		public Variable(String n, CoolClass t)
		{
			Name=n;
			Type=t;
		}
		public Variable(UnrecognizedTypeVar rv, AnalizerSemantic as) throws KeyWordName
		{
			if(AnalizerSemantic.isKeyWord(rv.Name))
				throw new KeyWordName(rv.Name, as);
			Name=rv.Name;
			Type=as.getPossibleClass(rv.TypeName);
		}
		public String toString()	{ return Name+":"+Type.toString(); }
		public static boolean sameType(Variable x, Variable y)	{ return x.Type==y.Type; }
	}

	private static class Method
	{
		String Name;
		ArrayList<Variable> args;
		CoolClass OwnerClass;
		CoolClass ReturnType;
		Scope MainScope;
		public Method(String name, CoolClass returnT, CoolClass owner, ArrayList<Variable> arguments)
		{
			Name=name;
			ReturnType = returnT;
			OwnerClass=owner;
			args=arguments;
			MainScope=Scope.generateMainScopeOfMethod(this);
		}
	}

	private static class Scope
	{
		public static final int EndOfScopeSearchIdentifier	=	-10;
		Scope Parent;
		Method MotherMethod;
		ArrayList<Variable> VarList;
		ArrayList<Integer> ScopeKey;
		ArrayList<Scope> Children;
		private Scope(Scope p, Method mother, ArrayList<Variable> varlist)
		{
			Parent=p;
			MotherMethod=mother;
			VarList=varlist;
			ScopeKey=new ArrayList<Integer>();
			ScopeKey.add(EndOfScopeSearchIdentifier);
			Children=new ArrayList<Scope>();
		}
		public static Scope generateMainScopeOfMethod(Method m)
		{
			Scope result = new Scope(null, m, new ArrayList<Variable>());
			m.MainScope=result;
			return result;
		}
		public static Scope generateChildScope4Scope_NotChildLinking(Scope s)
		{
			Scope result=new Scope(s, s.MotherMethod, new ArrayList<Variable>());
			result.ScopeKey=new ArrayList<Integer>(s.ScopeKey);
			result.ScopeKey.add(result.ScopeKey.size()-1, s.Children.size());
			return result;
		}
	}
	
	private static class CoolClass {

		CoolClass Ancestor;
		String name;
		public ArrayList<Method> MethodList;
		public ArrayList<Variable> Fields;
		
		public static final CoolClass coolObject	= new CoolClass("Object");
		public static final CoolClass coolInt		= new CoolClass("Int", coolObject);
		public static final CoolClass coolString	= new CoolClass("String", coolObject);
		public static final CoolClass coolBool		= new CoolClass("Bool", coolObject);
		public static final CoolClass coolIO		= new CoolClass("IO", coolObject);
	
		private CoolClass(String n){
			Ancestor = null;
			name = n;
			MethodList=new ArrayList<Method>();
			Fields= new ArrayList<Variable>();
		}
		CoolClass(String n,CoolClass p){
			name = n;
			if(p == null)
				Ancestor = coolObject;
			else 
				Ancestor = p;
			MethodList=new ArrayList<Method>();
			Fields= new ArrayList<Variable>();
		}
		public String toString()	{ return name; }
		public static CoolClass FirstCommonFatherClass(CoolClass a, CoolClass b)
		{
			assert !(a==null || b==null);

			if(a==b)
				return a;
			if(a.someFatherOf(b))
				return a;
			if(b.someFatherOf(a))
				return b;
			return FirstCommonFatherClass(a.Ancestor, b);
		}
		public static boolean hasLoopWith(CoolClass c, String Cname) {
			if (c == null)
				return false;
			if (c.name == Cname)
				return true;
			return hasLoopWith(c.Ancestor, Cname);
		}
		public boolean someFatherOf(CoolClass c)//father is not brother!!!
		{
			if(c==coolObject)
				return false;
			if(this==c.Ancestor)
				return true;
			return someFatherOf(c.Ancestor);
		}
	}

	public static abstract class SemanticError extends Throwable {
		public static final long serialVersionUID = 100L;
		public SemanticError(AnalizerSemantic as)	{ as.SemanticErrorFound=true; }
	}
	public static class LoopException extends SemanticError{
		public static final long serialVersionUID = 101L;
		private String className;
		public LoopException(String n, AnalizerSemantic as){
			super(as);
			className = n;
		}
		public String toString(){
			return "The following class caused a loop: "+className+"\n";
		}
	}
	public static class DuplicateClassX extends SemanticError{
		public static final long serialVersionUID = 102L;
		private String className;
		public DuplicateClassX(String n, AnalizerSemantic as) {
			super(as);
			className = n;
		}
		public String toString()
		{
			return "The following class was already decleared : "+className+"\n";
		}
	}
	public static class DanglingClassException extends SemanticError{
		public static final long serialVersionUID = 103L;
		ArrayList<CoolClass> list;
		public DanglingClassException(ArrayList<CoolClass> l, AnalizerSemantic as)
		{
			super(as);
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
		public static final long serialVersionUID = 104L;
		public NoMainException(AnalizerSemantic as)	{ super(as); }
		public String toString()	{ return "No Main class was found.\n"; }
	}
	public static class DuplicateVariableName extends SemanticError
	{
		public static final long serialVersionUID = 105L;
		String dupname;
		public DuplicateVariableName(String n, AnalizerSemantic as)
		{
			super(as);
			dupname=n;
		}
		public String toString()
		{
			return ("A variable with name "+dupname+" was already declared in this scope.");
		}
	}
	public static class DuplicateMethodName extends SemanticError
	{
		public static final long serialVersionUID = 106L;
		String DupMethodName;
		public DuplicateMethodName(String n, AnalizerSemantic as)
		{
			super(as);
			DupMethodName=n;
		}
		public String toString()
		{
			return ("redefinition of method with name: "+DupMethodName+" . not override.");
		}
	}
	public static class KeyWordName extends SemanticError
	{
		public static final long serialVersionUID = 107L;
		String InvalidName;
		public KeyWordName(String n, AnalizerSemantic as)
		{
			super(as);
			InvalidName=n;
		}
		public String toString()
		{
			return ("The name: "+InvalidName+" is a keyword.");
		}
	}
	public static class FirstPassUnsuccessful extends SemanticError
	{
		public static final long serialVersionUID = 108L;
		public FirstPassUnsuccessful(AnalizerSemantic as)
		{
			super(as);
		}
		public String toString()	{return "First commit unsuccessfull because of the mentioned errors.";}
	}
	public static class UndefinedVar extends SemanticError
	{
		public static final long serialVersionUID = 109L;
		String UndefinedID;
		public UndefinedVar(String varName, AnalizerSemantic as)
		{
			super(as);
			UndefinedID=varName;
		}
		public String toString()
		{
			return ("The variable with name: "+UndefinedID+" has not been declared in this scope.");
		}
	}
}