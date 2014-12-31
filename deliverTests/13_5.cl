
-- Error in assignment of z

class Main {
	x : Int <- 0;
	z : A <- (new B);

	main() : Bool { true };
};

class A inherits B {
	
};

class B {

};