
-- Type of predicate must be Bool

class Main {
	x : Int <- 2;
	y : Int <- 1;

	getVar() : Int { y };

	main() : Int {
		{
			if x then y + 1 else y * 2 fi;
			y <- y + 1;
		}
	};
};