import org.antlr.v4.runtime.*;

import java.io.IOException;

public class Main{
	public static void main(String []args) throws IOException{
		
		CharStream FirstPass = new ANTLRFileStream(args[0]);
		CharStream SecondPass = new ANTLRFileStream(args[0]);
		
		SemanticLexer lexer=new SemanticLexer(FirstPass);
		TypeCheckerLexer llexer=new TypeCheckerLexer(SecondPass); 
		
		CommonTokenStream pass1=new CommonTokenStream(lexer);
		CommonTokenStream pass2=new CommonTokenStream(llexer);
		
		SemanticParser parser=new SemanticParser(pass1);
		TypeCheckerParser typechecker = new TypeCheckerParser(pass2);
		
		AnalizerSemantic inst = AnalizerSemantic.getInstance();
		
		parser.program();
		//System.out.println(AnalizerSemantic.getInstance().toString());
		if(inst.hasSemanticError()){
			System.err.printf("Semantic Error\n");
			return;
		}
		try {
			typechecker.program();
		} catch (AnalizerSemantic.SemanticError e) {
			System.err.printf("ERROR :%s\n",e.toString());
		}
	}
}