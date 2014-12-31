import java.util.ArrayList;

import javax.sound.sampled.Line;

import org.antlr.runtime.MismatchedNotSetException;

public class AnalizerSemantic {
	private ArrayList<CoolClass> classList;
	private ArrayList<CoolClass> expectingClassList;
	private boolean SemanticErrorFound;
	private int PassedTimes;
	private int LineNumber;

	private static AnalizerSemantic Instance = new AnalizerSemantic();
	private static String KeyWords[] = {"class", "else", "fi", "if", "in", "inherits", "isvoid", "let", "loop", "pool", "then", "while", "case", "esac", "new", "of", "not", "true", "false"};
	public static final int addingToLetScope	=	1;
	public static final int addingToCaseScope	=	2;
	public static final String Self_token		=	"self";
	public static final int EOF_LineNumber		=	-1;

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

	public String toString()
	{
		String result="";
		result+="The program has the following classes:\n";
		for(int i=0; i<classList.size(); ++i)
			result+=(classList.get(i).name+", ");
		result+="\nTheClasses:\n\n";
		for(int i=0; i<classList.size(); ++i)
		{
			result+="\n\n\n############################################\n";
			result+=classList.get(i).toString();
		}
		return result;
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

	public void addClass(String Cname, String Pname, int ln) throws LoopException,DuplicateClassX, KeyWordName {
		if(PassedTimes!=0)
			return ;
		LineNumber=ln;
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

	public ArrayList<Integer> addMethod(String OwnerClassName, String MethodName, String MethodTypeName, ArrayList<UnrecognizedTypeVar> RawArgs, int ln)
			throws DuplicateVariableName, DuplicateMethodName, KeyWordName, SelfNameEx, IllegalSelfType
	{
		if(PassedTimes!=0)
			return null;
		LineNumber=ln;
		assert !isKeyWord(OwnerClassName);
		assert hasClassWithName(OwnerClassName, classList);
		if(isKeyWord(MethodName))
			throw new KeyWordName(MethodName, this);
		if(MethodName.equals(Self_token))
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

	public void addField(String ownerClassName, UnrecognizedTypeVar rawfield, int ln) throws DuplicateVariableName, KeyWordName, SelfNameEx
	{
		if(PassedTimes!=0)
			return ;
		LineNumber=ln;
		assert !isKeyWord(ownerClassName);
		assert hasClassWithName(ownerClassName, classList);
		Variable inComingField=new Variable(rawfield, this);
		CoolClass owner=getPossibleClass(ownerClassName);
		if(hasFieldOrInherited(inComingField.Name, owner))
			throw new DuplicateVariableName(inComingField.Name, this);
		owner.Fields.add(inComingField);
	}

	public void addScope(String ownerClassName, String motherMethodName, ArrayList<Integer> currentScopeKey, int ln)
	{
		if(PassedTimes!=0)
			return ;
		LineNumber=ln;
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
			ArrayList<Integer> currentScopeKey, UnrecognizedTypeVar rawfield, int whichScope, int ln)
					throws DuplicateVariableName, KeyWordName, CaseDuplicateType, SelfNameEx, IllegalSelfType
	{
		if(PassedTimes!=0)
			return ;
		LineNumber=ln;
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
		if(Variable.hasVariableWithName(rawfield.Name, owner.Fields))
			throw new DuplicateVariableName(rawfield.Name, this);
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
		//System.err.println(temp.MainScope.ScopeKey);
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
		//System.err.println("in updateKey pass2 key: "+currentScopeKey);
		assert parent!=null;

		currentScopeKey.add(currentScopeKey.size()-1, parent.ChildCount);
	}

	public void updateKeyWhenClosingScope(String ownerClassName, String motherMethodName, ArrayList<Integer> ScopeKey)
	{

		CoolClass owner=findCoolClassByName(ownerClassName, classList);
		assert owner!=null;
		Method mother=findMethodByName(motherMethodName, owner.MethodList);
		assert mother!=null;

		assert !(ScopeKey==null || ScopeKey.size()==0);
		Scope.closeScope(mother, ScopeKey);
		
	}
	
	public void commitFirstPass()
			throws NoMainException, DanglingClassException, DuplicateMethodName, FirstPassUnsuccessful
	{
		if(PassedTimes!=0)
			return ;

		LineNumber=EOF_LineNumber;
	
		if(!hasMain())
			throw new NoMainException(this);
		if(expectingClassList.size()!=0)
			throw new DanglingClassException(expectingClassList, this);
		for(int i=0; i<classList.size(); ++i)
			checkDuplicateMethods(classList.get(i));
		if(SemanticErrorFound)
			throw new FirstPassUnsuccessful(this);
		for(int i=0; i<classList.size(); ++i)
			for(int j=0; j<classList.get(i).MethodList.size(); ++j)
				classList.get(i).MethodList.get(j).MainScope.resetChildCountOfAll();
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
			String searchingID, int ln) throws UndefinedVar
	{
		LineNumber=ln;
		CoolClass owner=findCoolClassByName(ownerClassName, classList);
		assert owner!=null;
		Variable var;
		if(motherMethodName!=null)
		{
			Method mother=findMethodInClass(motherMethodName, owner);
			assert mother!=null;
			Scope s=Scope.getScopeInMethod(mother, currentScopeKey);
			assert s!=null;

			String result=null;

			result=searchVarTypeInScope(searchingID, s, owner);
			if(result!=null)
				return result;
			var=Variable.findVariableByName(searchingID, mother.args);
			if(var!=null)
				return FinalTypeName(var, owner);
		}
		var=findFieldInClass(searchingID, owner);
		if(var!=null)
			return FinalTypeName(var, owner);

		throw new UndefinedVar(searchingID, this);
	}

	public String TypeOfOperation(String arg1,String Operator,String arg2,String ClassName, int ln) throws KeyWordName, TypeConflict, NoClassFound_Name{
		LineNumber=ln;
		if(Operator.equals("<-")){
			assert(arg1!=null && arg2!=null);
			if(arg1.equals(arg2))
				return arg1;
			if(getPossibleClass(arg1).someFatherOf(getPossibleClass(arg2))){
				//System.err.printf
				return arg1;
			}
			//System.err.printf("arg1 = %s\n",arg1);
			//System.err.printf("arg2 = %s\n",arg2);
			//System.err.printf("Operator = %s\n",Operator);
			
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
				//throw new TypeConflict("f","s",this);
				throw new TypeConflict("Int",arg2,this);
			//throw new TypeConflict("s","f",this);
			throw new TypeConflict("Int",arg1,this);
		}
		else if(Operator.equals("<") || Operator.equals("<=")){
			assert(arg1!=null && arg2!=null);
			if(arg1.equals("Int") && arg2.equals("Int"))
				return "Bool";
			if(arg1.equals("Int"))
				//throw new TypeConflict("w","q",this);
				throw new TypeConflict("Int",arg2,this);
			//throw new TypeConflict("qwe","shq",this);
			throw new TypeConflict("Int",arg1,this);
		}
		else if(Operator.equals("=")){
			//System.err.println("Equality test"+arg1+" "+arg2);
			assert(arg1!=null && arg2!=null);
			if(arg1.equals("Int")){
				if(arg2.equals("Int"))
					return "Bool";
				//throw new TypeConflict("123","2rr2",this);
				throw new TypeConflict("Int",arg2,this);
			}
			if(arg2.equals("Int"))
				//throw new TypeConflict("rty","yuuio",this);
				throw new TypeConflict("Int",arg1,this);
			return "Bool";
		}
			
		return "F";
}
	
	public void checkMethodReturnType(String ReturnType,String TID, int ln) throws KeyWordName, ReturnTypeMismatch{
		LineNumber=ln;
		if(TID.equals(ReturnType))
			return ;
		if(getPossibleClass(ReturnType).someFatherOf(getPossibleClass(TID)))
			return ;
		throw new ReturnTypeMismatch(ReturnType, TID, this);
	}

	public String TypeNameOfMethod(String TypeNameOfExpr0, String FatherTypeName, String MethodName,
			ArrayList<String> argTypeNames, String CurrentClassName, int ln)
					throws NoClassFound_Name, InvalidInheritance, NoMethodFoundInClass, IllegalArguments, UndefinedVar, IllegalSelfType
	{
		LineNumber=ln;
		if(TypeNameOfExpr0==null)
			TypeNameOfExpr0=CurrentClassName;
		CoolClass CalleeClass = determineCalleeClass(TypeNameOfExpr0, FatherTypeName, MethodName);
		Method m=findMethodInClass(MethodName, CalleeClass);
		if(m==null)
			throw new NoMethodFoundInClass(CalleeClass.name, MethodName, this);
		if(!MatchArgs(m, argTypeNames))
			throw new IllegalArguments(m, argTypeNames, this);
		if(m.ReturnType==CoolClass.coolSelf_TYPE)
			if(FatherTypeName!=null)
				if(findMethodInClass(MethodName, findCoolClassByName(FatherTypeName, classList))!=null)
					return FatherTypeName;
				else
					throw new NoMethodFoundInClass(FatherTypeName, MethodName, this);
			else
				return TypeNameOfExpr0;
		else
			return m.ReturnType.name;
	}

	public int getLineNumber() { return LineNumber; }

	public void CheckBoolPredicate(String PredType, int ln) throws NotBoolPredicate
	{
		LineNumber=ln;
		if(!PredType.equals(CoolClass.coolBool.name))
			throw new NotBoolPredicate(PredType, this);
		return ;
	}

	private CoolClass determineCalleeClass(String Cname, String pname, String MethodName)
			throws NoClassFound_Name, InvalidInheritance, IllegalSelfType
	{
		CoolClass currentClass=findCoolClassByName(Cname, classList);
		assert currentClass!=null;
		if(pname==null || Cname.equals(pname))
			return currentClass;
		if(pname.equals(CoolClass.coolSelf_TYPE.name))
			throw new IllegalSelfType(MethodName, this);
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

	private boolean MatchArgs(Method m, ArrayList<String> argTypeNames) throws UndefinedVar
	{
		if(argTypeNames==null)
			return false;
		if(argTypeNames.size()!=m.args.size())
			return false;
		for(int i=0; i<argTypeNames.size(); ++i)
			if(m.args.get(i).Type!=findCoolClassByName(argTypeNames.get(i), classList))
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
			if(n.equals(AnalizerSemantic.Self_token))
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
			if(rv.Name.equals(AnalizerSemantic.Self_token))
				throw new SelfNameEx(as);
			Name=rv.Name;
			Type=as.getPossibleClass(rv.TypeName);
		}

		private Variable(String n, CoolClass t)
		{
			Name=n;
			Type=t;
		}
		public String toString()	{ return Name+" : "+Type.name; }
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

		public String toString()
		{
			String result="";
			result+=("Name: "+Name+"\tType: "+ReturnType.name+"\n");
			result+=("OwnerClass: "+OwnerClass.name+"\n");
			result+=("Arguments:\n");
			for(int i=0; i<args.size(); ++i)
				result+=(args.get(i).toString()+"\n");
			result+="\n\nMainScope:\n";
			result+=MainScope.toString();
			result+="\n\n";
			return result;
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
		int ChildCount;
		private Scope(Scope p, Method mother, ArrayList<Variable> varlist)
		{
			Parent=p;
			MotherMethod=mother;
			VarList=varlist;
			ScopeKey=new ArrayList<Integer>();
			ScopeKey.add(EndOfScopeSearchIdentifier);
			Children=new ArrayList<Scope>();
			ChildCount=0;
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

		public void resetChildCountOfAll()
		{
			ChildCount=0;
			for(int i=0; i<Children.size(); ++i)
				Children.get(i).resetChildCountOfAll();
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

		public static void closeScope(Method m, ArrayList<Integer> ScopeKey)
		{
			if(ScopeKey.size()==1)//Main scope is closing
			{
				assert ScopeKey.get(0)==Scope.EndOfScopeSearchIdentifier;
				ScopeKey.remove(0);
				return ;
			}
			ScopeKey.remove(ScopeKey.size()-2);
			Scope parent=getScopeInMethod(m, ScopeKey);
			parent.ChildCount=parent.ChildCount+1;
		}

		public String toString()
		{
			String result="";
			result+=("key: "+ScopeKey.toString());
			result+=("parentKey: "+(Parent==null?"null":Parent.ScopeKey.toString())+"\n");
			result+=("Vars:\n");
			for(int i=0; i<VarList.size(); ++i)
				result+=(VarList.get(i).toString()+"\n");
			result+="\n\nChildren:\n";
			for(int i=0; i<Children.size();++i)
				result+=(Children.get(i).toString()+"\n");
			result+="\n\n";
			return result;
		}
	}
	
	private static class CoolClass {

		CoolClass Ancestor;
		String name;
		public ArrayList<Method> MethodList;
		public ArrayList<Variable> Fields;
		
		public static final CoolClass coolSelf_TYPE = new CoolClass("SELF_TYPE");
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
			if(c==null)
				return false;
			if(c==coolObject)
				return false;
			if(this==c.Ancestor)
				return true;
			return someFatherOf(c.Ancestor);
		}

		public String toString()
		{
			String result="";
			result+=("name= "+name+"\n");
			result+=("FatherName: "+(Ancestor==null?"null":Ancestor.name)+"\n");
			result+="\nFileds:\n";
			for(int i=0; i<Fields.size(); ++i)
				result+=(Fields.get(i).toString()+"\n");
			result+="\nMethods:\n";
			for(int i=0; i<MethodList.size(); ++i)
				result+=(MethodList.get(i).toString()+"\n");
			return result;
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
		private Integer LineNumber;
		public SemanticError(AnalizerSemantic as)	{ as.SemanticErrorFound=true; LineNumber=as.LineNumber; }
		public String toString()
		{
			if(LineNumber!=AnalizerSemantic.EOF_LineNumber)
				return ("Error at line: "+LineNumber.toString()+"\n"+declaration()+"\n");
			else
				return ("Error at end of file: \n"+declaration()+"\n");
		}
		public abstract String declaration();
	}
	public static class LoopException extends SemanticError{
		public static final long serialVersionUID = 101L;
		private String className;
		public LoopException(String n, AnalizerSemantic as){
			super(as);
			className = n;
		}
		public String declaration(){
			return "The following class caused a loop: "+className+"\n";
		}
		public String toString()	{ return super.toString(); }
	}
	public static class DuplicateClassX extends SemanticError{
		public static final long serialVersionUID = 102L;
		private String className;
		public DuplicateClassX(String n, AnalizerSemantic as) {
			super(as);
			className = n;
		}
		public String declaration()
		{
			return "The following class was already decleared : "+className+"\n";
		}
		public String toString()	{ return super.toString(); }
	}
	public static class DanglingClassException extends SemanticError{
		public static final long serialVersionUID = 103L;
		ArrayList<CoolClass> list;
		public DanglingClassException(ArrayList<CoolClass> l, AnalizerSemantic as)
		{
			super(as);
			list=l;
		}
		public String declaration(){
			String result;
			result="The classes with these name are expected:\n";
			for(int i=0; i<list.size(); ++i)
				result=result+list.get(i).name+", ";
			return result;
		}
		public String toString()	{ return super.toString(); }
	}
	public static class NoMainException extends SemanticError{
		public static final long serialVersionUID = 104L;
		public NoMainException(AnalizerSemantic as)	{ super(as); }
		public String declaration()	{ return "No Main class with main Method was found.\n"; }
		public String toString()	{ return super.toString(); }
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
		public String declaration()
		{
			return ("Variable with name "+dupname+" was already declared in this scope.");
		}
		public String toString()	{ return super.toString(); }
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
		public String declaration()
		{
			return ("redefinition of method with name: "+DupMethodName+" . not override.");
		}
		public String toString()	{ return super.toString(); }
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
		public String declaration()
		{
			return ("The name: "+InvalidName+" is a keyword.");
		}
		public String toString()	{ return super.toString(); }
	}
	public static class FirstPassUnsuccessful extends SemanticError
	{
		public static final long serialVersionUID = 108L;
		public FirstPassUnsuccessful(AnalizerSemantic as)
		{
			super(as);
		}
		public String declaration()	{return "First commit unsuccessfull because of the mentioned errors.";}
		public String toString()	{ return super.toString(); }
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
		public String declaration()
		{
			return ("The variable with name: "+UndefinedID+" has not been declared in this scope.");
		}
		public String toString()	{ return super.toString(); }
	}
	public static class SelfNameEx extends SemanticError
	{
		public static final long serialVersionUID = 110L;
		public SelfNameEx(AnalizerSemantic as)
		{
			super(as);
		}
		public String declaration()
		{
			return "The name cannot be self.";
		}
		public String toString()	{ return super.toString(); }
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
		public String declaration()
		{
			return ("In case statement we cannot have duplication in type. the following variable caused error: "+varName+" : "+varTypeName+" .");
		}
		public String toString()	{ return super.toString(); }
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
		public String declaration()
		{
			return ("The followin type names have conflict: "+Tname1+" & "+Tname2+" .");
		}
		public String toString()	{ return super.toString(); }
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
		public String declaration()
		{
			return ("The Class "+Name1+" is not a child of "+Name2+" .");
		}
		public String toString()	{ return super.toString(); }
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
		public String declaration()
		{
			return ("The class with name: "+Cname+" has not been defined.");
		}
		public String toString()	{ return super.toString(); }
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
		public String toString()	{ return super.toString(); }
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
		public String declaration()
		{
			return ("This Name cannot be declared as SELF_TYPE: "+Name+" .");
		}
		public String toString()	{ return super.toString(); }
	}
	public static class IllegalArguments extends SemanticError
	{
		public static final long serialVersionUID = 117L;
		Method M;
		ArrayList<String> ArgTypeNames;
		public IllegalArguments(Method m, ArrayList<String> argTnames, AnalizerSemantic as)
		{
			super(as);
			M=m;
			ArgTypeNames=argTnames;
		}
		public String declaration()
		{
			String argTypes=M.args.size()==0?"[]":"";
			for(int i=0; i<M.args.size(); ++i)
				argTypes+=(M.args.get(i).Type.name+", ");
			return ("The method "+M.Name+" needs arguments of following list:\n"+argTypes+"\nBut provided: "+ArgTypeNames.toString());
		}
		public String toString()
		{
			return super.toString();
		}
	}

	public static class NotBoolPredicate extends SemanticError
	{
		public static final long serialVersionUID = 118L;
		String IllegalType;
		public NotBoolPredicate(String tname, AnalizerSemantic as)
		{
			super(as);
			IllegalType=tname;
		}
		public String declaration()
		{
			return ("Type of predicate most be boolean but "+IllegalType+" provided.");
		}
		public String toString()	{ return super.toString(); }
	}

	public static class ReturnTypeMismatch extends SemanticError
	{
		public static final long serialVersionUID = 119L;
		String ReturnType;
		String Provided;
		public ReturnTypeMismatch(String returnType, String provided, AnalizerSemantic as)
		{
			super(as);
			ReturnType=returnType;
			Provided=provided;
		}
		public String declaration()
		{
			return ("Type of defining method most be "+ReturnType+" but "+Provided+" provided.");
		}
		public String toString()	{ return super.toString(); }
	}
}
