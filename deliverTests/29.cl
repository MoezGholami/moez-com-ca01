
-- Formal parameters of a method must be distinct

class Main {
	x : Int;

	main() : Int { 1 };
	setX(num : Int, num : String) : SELF_TYPE { self };
};