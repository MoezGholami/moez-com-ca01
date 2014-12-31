
-- Loop in the inheritance graph

class Main inherits A {
	main() : Int { 1 };
};
class A inherits C {
};
class C inherits D{
};
class D inherits A {
};