
-- Method call error

class Main {
	x : A;
	y : Int;

	getVar() : Int { y };

	main() : Int {
		{
			y <- x@Main.getSelf();    -- error
			y <- 1;
		}
	};
};

class A inherits Main {

	getSelf(num : Int) : A { self };
	
};