import java.util.ArrayList;

public class AnalizerSemantic {
	private ArrayList<CoolClass> classList;
	private ArrayList<CoolClass> expectingClassList;
	private boolean SemanticErrorFound;
	private int PassedTimes;
	private static AnalizerSemantic Instance = new AnalizerSemantic();
	private static String KeyWords[] = {"class", "else", "fi", "if", "in", "inherits", "isvoid", "let", "loop", "pool", "then", "while", "case", "esac", "new", "of", "not", "true", "false"};
	public static final int addingToLetScope	=	1;
	public static final int addingToCaseScope	=	2;
	public static final String Self_token		=	"self";

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
		CoolClass MainClass=findCoolClassByName("Main", classList);
		if(MainClass==null)
			return false;
		return hasMethodWithName("main", MainClass.MethodList);
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
		if(Pname.equals(CoolClass.coolSelf_TYPE.name))
			throw new LoopException(Cname, this);
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
			throws DuplicateVariableName, DuplicateMethodName, KeyWordName, SelfNameEx, IllegalSelfType
	{
		if(PassedTimes!=0)
			return null;
		assert !isKeyWord(OwnerClassName);
		assert hasClassWithName(OwnerClassName, classList);
		if(isKeyWord(MethodName))
			throw new KeyWordName(MethodName, this);
		if(MethodName==Self_token)
			throw new SelfNameEx(this);
		CoolClass OwnerCoolClass=getPossibleClass(OwnerClassName);
		ArrayList<Variable> args=convertRawVars2Vars(RawArgs, OwnerCoolClass);
		Method inComingMethod= new Method(MethodName, getPossibleClass(MethodTypeName), OwnerCoolClass, args);
		if(hasMethodWithName(inComingMethod.Name, OwnerCoolClass.MethodList)
				|| duplicateAndNotOverrided(inComingMethod, inComingMethod.OwnerClass.Ancestor))
			throw new DuplicateMethodName(MethodName, this);
		OwnerCoolClass.MethodList.add(inComingMethod);
		return inComingMethod.MainScope.ScopeKey;
	}

	public void addField(String ownerClassName, UnrecognizedTypeVar rawfield) throws DuplicateVariableName, KeyWordName, SelfNameEx
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
		Scope parent=Scope.getScopeInMethod(mother, currentScopeKey);
		assert parent!=null;

		Scope inComingScope=Scope.generateChildScope4Scope_NotChildLinking(parent);
		assert !Scope.haveScopeWithKeyInMethod(mother, inComingScope.ScopeKey);
		Scope.Point2NewChild(currentScopeKey, parent);
		parent.Children.add(inComingScope);
		return ;
	}

	public void addVariable2Scope(String ownerClassName, String motherMethodName,
			ArrayList<Integer> currentScopeKey, UnrecognizedTypeVar rawfield, int whichScope)
					throws DuplicateVariableName, KeyWordName, CaseDuplicateType, SelfNameEx, IllegalSelfType
	{
		if(PassedTimes!=0)
			return ;

		assert !isKeyWord(ownerClassName);
		assert !isKeyWord(motherMethodName);
		CoolClass owner=findCoolClassByName(ownerClassName, classList);
		assert owner!=null;
		Method mother=findMethodByName(motherMethodName, owner.MethodList);
		assert mother!=null;
		Scope s=Scope.getScopeInMethod(mother, currentScopeKey);
		assert s!=null;

		if(whichScope!=addingToLetScope && rawfield.TypeName.equalsIgnoreCase(CoolClass.coolSelf_TYPE.name))
			throw new IllegalSelfType(rawfield.Name, this);
		Variable inComingVariable=new Variable(rawfield, this);
		if(Variable.hasVariableWithName(inComingVariable.Name, mother.args))
			throw new DuplicateVariableName(inComingVariable.Name, this);
		Variable duplicateScopeVar=Variable.findVariableByName(inComingVariable.Name, s.VarList);
		if(duplicateScopeVar!=null)
			if(whichScope==addingToLetScope)
				s.VarList.remove(duplicateScopeVar);//see page 11, let, paragraph 3
			else if(whichScope==addingToLetScope)
				throw new DuplicateVariableName(inComingVariable.Name, this);
		duplicateScopeVar=Variable.findVariableByType(inComingVariable.Type, s.VarList);
		if(duplicateScopeVar!=null && whichScope==addingToCaseScope)
			throw new CaseDuplicateType(inComingVariable.Name, inComingVariable.Type.name, this);
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
		Scope parent=Scope.getScopeInMethod(mother, currentScopeKey);
		assert parent!=null;

		Scope.Point2NewChild(currentScopeKey, parent);
	}

	public void updateKeyWhenClosingScope(ArrayList<Integer> ScopeKey)
	{
		assert !(ScopeKey==null || ScopeKey.size()==0);
		Scope.closeScope(ScopeKey);
		
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
		Scope s=Scope.getScopeInMethod(mother, currentScopeKey);
		assert s!=null;

		String result=null;
		Variable var;

		result=searchVarTypeInScope(searchingID, s, owner);
		if(result!=null)
			return result;
		var=Variable.findVariableByName(searchingID, mother.args);
		if(var!=null)
			return FinalTypeName(var, owner);
		var=findFieldInClass(searchingID, owner);
		if(var!=null)
			return FinalTypeName(var, owner);

		throw new UndefinedVar(searchingID, this);
	}

	public String TypeOfOperation(String arg1,String Operator,String arg2,String ClassName) throws KeyWordName, TypeConflict, NoClassFound_Name{
		if(Operator.equals("<-")){
			assert(arg1!=null && arg2!=null);
			if(arg1.equals(arg2))
				return arg1;
			if(getPossibleClass(arg1).someFatherOf(getPossibleClass(arg2))){
				return arg1;
			}
			throw new TypeConflict(arg1,arg2,this);
		}
		else if(Operator.equals("KWNEW") ){
			if(CoolClass.coolSelf_TYPE.name.equals(arg1)){
				return arg1;
			}
			if(hasClassWithName(arg1, classList))
				return arg1;
			throw new NoClassFound_Name(arg1,this);
		}
		else if(Operator.equals("~")){
			if(arg1.equals("Int"))
				return arg1;
			else 
				throw new TypeConflict("Int",arg1,this);
		}
		else if(Operator.equals("KWISVOID")){
			return "Bool";
		}
		else if(Operator.equals("/") || Operator.equals("*") || Operator.equals("+") || Operator.equals("-")){
			assert(arg1!=null && arg2!=null);
			if(arg1.equals("Int") && arg2.equals("Int"))
				return arg1;
			if(arg1.equals("Int"))
				throw new TypeConflict("Int",arg2,this);
			throw new TypeConflict("Int",arg1,this);
		}
		else if(Operator.equals("<") || Operator.equals("<=")){
			assert(arg1!=null && arg2!=null);
			if(arg1.equals("Int") && arg2.equals("Int"))
				return "Bool";
			if(arg1.equals("Int"))
				throw new TypeConflict("Int",arg2,this);
			throw new TypeConflict("Int",arg1,this);
		}
		else if(Operator.equals("=")){
			assert(arg1!=null && arg2!=null);
			if(arg1.equals("Int")){
				if(arg2.equals("Int"))
					return "Bool";
				throw new TypeConflict("Int",arg2,this);
			}
			if(arg2.equals("Int"))
				throw new TypeConflict("Int",arg1,this);
			return "Bool";
		}
			
		return "F";
}

	
	public boolean addMethodReturnTypeCheck(String ReturnType,String TID) throws KeyWordName{
		if(TID.equals(ReturnType))
			return true;
		if(getPossibleClass(TID).someFatherOf(getPossibleClass(ReturnType))){
			return true;
		}
		return false;
	}

	//public String TypeNameOfMethod(String TypeNameOfExp0 canBeNull, TYPE of father can be null, MethodName cannot be null, ArrayList<String> args)
		//throws TypeConflict

	public String TypeNameOfMethod(String TypeNameOfExpr0, String FatherTypeName, String MethodName,
			ArrayList<String> argNames, String CurrentClassName)
					throws NoClassFound_Name, InvalidInheritance, NoMethodFoundInClass, IllegalArguments, UndefinedVar
	{
		if(TypeNameOfExpr0==null)
			TypeNameOfExpr0=CurrentClassName;
		CoolClass CalleeClass = determineCalleeClass(TypeNameOfExpr0, FatherTypeName);
		Method m=findMethodInClass(MethodName, CalleeClass);
		if(m==null)
			throw new NoMethodFoundInClass(CalleeClass.name, MethodName, this);
		if(!MatchArgs(m, argNames))
			throw new IllegalArguments(m, this);
		if(m.OwnerClass==CoolClass.coolSelf_TYPE)
			return CurrentClassName;
		else
			return m.OwnerClass.name;
	}

	private CoolClass determineCalleeClass(String Cname, String pname)
			throws NoClassFound_Name, InvalidInheritance
	{
		CoolClass currentClass=findCoolClassByName(Cname, classList);
		assert currentClass!=null;
		if(pname==null || Cname.equals(pname) || pname.equals(CoolClass.coolSelf_TYPE.name))
			return currentClass;
		CoolClass parent=findCoolClassByName(pname, classList);
		if(parent==null)
			throw new NoClassFound_Name(pname, this);
		if(!parent.someFatherOf(currentClass))
			throw new InvalidInheritance(Cname, pname, this);
		return parent;
	}
	private Method findMethodInClass(String MethodName, CoolClass c)
	{
		Method result;
		result=findMethodByName(MethodName, c.MethodList);
		if (result!=null)
			return result;
		if(c==CoolClass.coolObject)
			return null;
		return findMethodInClass(MethodName, c.Ancestor);
	}

	private boolean MatchArgs(Method m, ArrayList<String> argNames) throws UndefinedVar
	{
		if(argNames==null)
			return false;
		if(argNames.size()!=m.args.size())
			return false;
		for(int i=0; i<argNames.size(); ++i)
			if(!LookUpVarType(m.OwnerClass.name, m.Name, m.MainScope.ScopeKey, argNames.get(i)).equalsIgnoreCase(
					m.args.get(i).Type.name))
				return false;
		return true;
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

	private ArrayList<Variable> convertRawVars2Vars(ArrayList<UnrecognizedTypeVar> rawVars, CoolClass owner)
		throws DuplicateVariableName, KeyWordName, SelfNameEx, IllegalSelfType
	{
		ArrayList<Variable> result=new ArrayList<Variable>();
		for(int i=0; i<rawVars.size(); ++i)
			if(Variable.hasVariableWithName(rawVars.get(i).Name, result))
				throw new DuplicateVariableName(rawVars.get(i).Name, this);
			else if(rawVars.get(i).TypeName.equalsIgnoreCase(CoolClass.coolSelf_TYPE.name))
				throw new IllegalSelfType(rawVars.get(i).Name, this);
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

	private Variable findFieldInClass(String SearchingID, CoolClass owner)
	{
		Variable result=null;
		result=Variable.findVariableByName(SearchingID, owner.Fields);
		if(result!=null)
			return result;
		if(owner==CoolClass.coolObject)
			return null;
		return findFieldInClass(SearchingID, owner.Ancestor);
	}

	private boolean hasFieldOrInherited(String vname, CoolClass c)
	{
		if(c==null)
			return false;
		if(Variable.hasVariableWithName(vname, c.Fields))
			return true;
		return hasFieldOrInherited(vname, c.Ancestor);
	}

	private void addPrimitiveClasses(ArrayList<CoolClass> clist)
	{
		clist.add(CoolClass.coolSelf_TYPE);
		clist.add(CoolClass.coolObject);
		clist.add(CoolClass.coolInt);
		clist.add(CoolClass.coolBool);
		clist.add(CoolClass.coolString);
		clist.add(CoolClass.coolIO);
	}

	private String searchVarTypeInScope(String varName, Scope s, CoolClass owner)
	{
		if(s==null)
			return null;
		Variable v=Variable.findVariableByName(varName,s.VarList);
		if(v!=null)
			return v.Type.name;
		return searchVarTypeInScope(varName, s.Parent, owner);
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

	private static String FinalTypeName(Variable v, CoolClass owner)
	{
		if(v.Type==CoolClass.coolSelf_TYPE)
			return owner.name;
		return v.Type.name;
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
		public Variable(String n, CoolClass t, AnalizerSemantic as) throws SelfNameEx
		{
			if(n==AnalizerSemantic.Self_token)
				throw new SelfNameEx(as);
			Name=n;
			Type=t;
		}
		public static Variable getSelfVariable4Class(CoolClass t)
		{
			if(t==null || hasVariableWithName(Self_token, t.Fields))
				return null;
			return new Variable(Self_token, t);
		}

		public Variable(UnrecognizedTypeVar rv, AnalizerSemantic as) throws KeyWordName, SelfNameEx
		{
			if(AnalizerSemantic.isKeyWord(rv.Name))
				throw new KeyWordName(rv.Name, as);
			if(rv.Name==AnalizerSemantic.Self_token)
				throw new SelfNameEx(as);
			Name=rv.Name;
			Type=as.getPossibleClass(rv.TypeName);
		}

		private Variable(String n, CoolClass t)
		{
			Name=n;
			Type=t;
		}
		public String toString()	{ return Name+":"+Type.toString(); }
		public static boolean sameType(Variable x, Variable y)	{ return x.Type==y.Type; }

		public static boolean hasVariableWithName(String n, ArrayList<Variable> list){
			return findVariableByName(n,list) != null;
		}

		public static Variable findVariableByName(String n, ArrayList<Variable> list){
			for(int i=0;i<list.size();i++)
				if(list.get(i).Name.equals(n))
					return list.get(i);
			return null;
		}

		public static Variable findVariableByType(CoolClass t, ArrayList<Variable> list)
		{
			for(int i=0; i<list.size(); ++i)
				if(list.get(i).Type==t)
					return list.get(i);
			return null;
		}
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
			Point2NewChild(result.ScopeKey, s);
			return result;
		}
		public static void Point2NewChild(ArrayList<Integer> currentScopeKey, Scope parent)
		{
			currentScopeKey.add(currentScopeKey.size()-1, parent.Children.size());
		}
		
		private static Scope getScopeInMethod(Method m, ArrayList<Integer> key)
		{
			if(m==null || key==null || key.size()==0)
				return null;
			return getScopeInScope(m.MainScope, key);
		}

		private static boolean haveScopeWithKeyInMethod(Method m, ArrayList<Integer> key)
		{
			return getScopeInMethod(m, key)!=null;
		}

		private static boolean haveScopeWithKeyInScope(Scope s, ArrayList<Integer> key)
		{
			return getScopeInScope(s, key)!=null;
		}

		private static Scope getScopeInScope(Scope s, ArrayList<Integer> key)
		{
			ArrayList<Integer> terminatingKey=new ArrayList<Integer>(key);
			return getScopeInScope_TerminatingKey(s, terminatingKey);
		}

		private static Scope getScopeInScope_TerminatingKey(Scope s, ArrayList<Integer> key)
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

		public static void closeScope(ArrayList<Integer> ScopeKey)
		{
			if(ScopeKey.size()==1)//Main scope is closing
			{
				assert ScopeKey.get(0)==Scope.EndOfScopeSearchIdentifier;
				ScopeKey.remove(0);
				return ;
			}
			ScopeKey.remove(ScopeKey.size()-2);
		}
	}
	
	private static class CoolClass {

		CoolClass Ancestor;
		String name;
		public ArrayList<Method> MethodList;
		public ArrayList<Variable> Fields;
		
		public static final CoolClass coolSelf_TYPE = new CoolClass("Self_Type");

		public static final CoolClass coolObject	= new CoolClass("Object");
		public static final CoolClass coolInt		= new CoolClass("Int", coolObject);
		public static final CoolClass coolString	= new CoolClass("String", coolObject);
		public static final CoolClass coolBool		= new CoolClass("Bool", coolObject);
		public static final CoolClass coolIO		= new CoolClass("IO", coolObject);

		static
		{
			addMethodAndFields2StaticClasses();
		}
	
		private CoolClass(String n){
			Ancestor = null;
			name = n;
			MethodList=new ArrayList<Method>();
			Fields= new ArrayList<Variable>();
		}
		public CoolClass(String n,CoolClass p){
			name = n;
			if(p == null)
				Ancestor = coolObject;
			else 
				Ancestor = p;
			MethodList=new ArrayList<Method>();
			Fields= new ArrayList<Variable>();
			Fields.add(Variable.getSelfVariable4Class(this));
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

		private static void addMethodAndFields2StaticClasses()
		{
			addMethodsOfObject();
			addMethodsOfString();
			addMethodsOfIO();
		}

		private static void addMethodsOfObject()
		{
			coolObject.MethodList.add(new Method("abort", coolObject, coolObject, new ArrayList<Variable>()));
			coolObject.MethodList.add(new Method("type_name", coolString, coolObject, new ArrayList<Variable>()));
			coolObject.MethodList.add(new Method("copy", coolSelf_TYPE, coolObject, new ArrayList<Variable>()));
		}

		private static void addMethodsOfString()
		{
			coolString.MethodList.add(new Method("length", coolInt, coolString, new ArrayList<Variable>()));
			ArrayList<Variable> concatArgs =  new ArrayList<Variable>();
			concatArgs.add(new Variable("s", coolString));
			coolString.MethodList.add(new Method("concat", coolString, coolString, new ArrayList<Variable>()));
			ArrayList<Variable> SubArgs = new ArrayList<Variable>();
			SubArgs.add(new Variable("i",coolInt));
			SubArgs.add(new Variable("l", coolInt));
			coolString.MethodList.add(new Method("substr", coolString, coolString, SubArgs));
		}

		private static void addMethodsOfIO()
		{
			coolIO.MethodList.add(new Method("in_int", coolInt, coolIO, new ArrayList<Variable>()));
			coolIO.MethodList.add(new Method("in_string", coolString, coolIO, new ArrayList<Variable>()));
			ArrayList<Variable> out_intArgs = new ArrayList<Variable>();
			ArrayList<Variable> out_strArgs	= new ArrayList<Variable>();
			out_intArgs.add(new Variable("x", coolInt));
			out_strArgs.add(new Variable("x", coolString));
			coolIO.MethodList.add(new Method("out_int", coolSelf_TYPE, coolIO, out_intArgs));
			coolIO.MethodList.add(new Method("out_string", coolSelf_TYPE, coolIO, out_strArgs));
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
	public static class SelfNameEx extends SemanticError
	{
		public static final long serialVersionUID = 110L;
		public SelfNameEx(AnalizerSemantic as)
		{
			super(as);
		}
		public String toString()
		{
			return "The name cannot be self.";
		}
	}
	public static class CaseDuplicateType extends SemanticError
	{
		public static final long serialVersionUID = 111L;
		String varName;
		String varTypeName;
		public CaseDuplicateType(String name, String tName, AnalizerSemantic as)
		{
			super(as);
			varName=name;
			varTypeName=tName;
		}
		public String toString()
		{
			return ("In case statement we cannot have duplication in type. the following variable caused error: "+varName+" : "+varTypeName+" .");
		}
	}
	public static class TypeConflict extends SemanticError
	{
		public static final long serialVersionUID = 112L;
		String Tname1;
		String Tname2;
		public TypeConflict(String t1, String t2, AnalizerSemantic as)
		{
			super(as);
			Tname1=t1;
			Tname2=t2;
		}
		public String toString()
		{
			return ("The followin type names have conflict: "+Tname1+" & "+Tname2+" .");
		}
	}
	public static class InvalidInheritance extends SemanticError
	{
		public static final long serialVersionUID = 113L;
		String Name1;
		String Name2;
		public InvalidInheritance(String n1, String n2, AnalizerSemantic as)
		{
			super(as);
			Name1=n1;
			Name2=n2;
		}
		public String toString()
		{
			return ("The Class "+Name1+" is not a child of "+Name2+" .");
		}
	}
	public static class NoClassFound_Name extends SemanticError
	{
		public static final long serialVersionUID = 114L;
		String Cname;
		public NoClassFound_Name(String n, AnalizerSemantic as)
		{
			super(as);
			Cname=n;
		}
		public String toString()
		{
			return ("The class with name: "+Cname+" has not been defined.");
		}
	}
	public static class NoMethodFoundInClass extends SemanticError
	{
		public static final long serialVersionUID = 115L;
		String Cname;
		String Mname;
		public NoMethodFoundInClass(String c, String m, AnalizerSemantic as)
		{
			super(as);
			Cname=c;
			Mname=m;
		}
		public String declaration()
		{
			return ("The class "+Cname+" does not have a method with name: "+Mname+" .");
		}
	}
	public static class IllegalSelfType extends SemanticError
	{
		public static final long serialVersionUID = 116L;
		String Name;
		public IllegalSelfType(String n, AnalizerSemantic as)
		{
			super(as);
			Name=n;
		}
		public String decalaraion()
		{
			return ("Cannot name this variable as SELF_TYPE: "+Name+" .");
		}
	}
	public static class IllegalArguments extends SemanticError
	{
		public static final long serialVersionUID = 117L;
		Method M;
		public IllegalArguments(Method m, AnalizerSemantic as)
		{
			super(as);
			M=m;
		}
		public String declaration()
		{
			String argTypes="";
			for(int i=0; i<M.args.size(); ++i)
				argTypes+=(M.args.get(i).Type.name+", ");
			return ("The method "+M.Name+" needs arguments of following list:\n"+argTypes);
		}
	}
}
