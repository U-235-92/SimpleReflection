package sr;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StringOperator so = new StringOperator("Learn java");
//		so.reverseStr();
//		so.drop();
//		so.fieldReflection();
//		so.annotationReflection();
		so.fieldHandler(so.getClass(), "Learn java");
	}
}
