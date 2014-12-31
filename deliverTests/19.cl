
-- Wrong return type for setX method

class Main {
	x : String;

	setX(s : String) : SELF_TYPE { new A };	
	main() : Int { 1 };
};
class A {

};