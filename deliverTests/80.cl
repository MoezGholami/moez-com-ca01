
-- No error

class Main inherits A  {
	x : Int <- 0;

	main() : Int {
		{
			let y : Int <- 1, z : Int <- y + 1 in
				{
					y <- y * z;
				};
			x <- 2;
		}
	};
};

class A {
	y : String;
	getVar(num1 : Int, num2 : Int ) :String { y	} ;
};