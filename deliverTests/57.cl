
-- Multiple definition of a same class (if pass 2 is executed, also there is a return type error)

class Main {

	main() : Int { true };
};

class A {
	y : Int;
};

class A {
	z : Bool;
};
