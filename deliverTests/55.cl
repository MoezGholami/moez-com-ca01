
-- Error in assignment (the identifier id introduced by a branch of a case hides attribute definition)

class Main {
	x : A;
	y : SELF_TYPE;

	main() : Int {
		case x of
			y : B => y <- (new A);
			z : A => 1;
		esac
	};
};

class A inherits Main {

};

class B {

};