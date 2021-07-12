package sr;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


@MarkerAnnotation
public class StringOperator implements IStringOperation {
	@StringOperatorAnnotation(id = 1, description = "changed description in operator")
	private String string;
	public StringOperator() {
		string = "Not declared field";
	}
	public StringOperator(String string) {
		this.string = string;
	}
	
	/***
	 * This method reverses string gave as parameter of constructor.
	 * @return String in reversed order.
	 */
	@MethodAnnotation(id = 1)
	public void reverseStr() {
		IStringOperation revers = (str) -> {
			String result = "";
			for(int i = str.length() - 1; i >= 0; i--) {
				result += str.charAt(i);
			}
			return result;
		};
		System.out.println(revers.operation(string));
	}
	/***
	 * This method delete all whitespase symbols in string.
	 * @return String without whitespace symbols.
	 */
	@MethodAnnotation(id = 2)
	public void drop() {
		String result = func(StringOperator::deleteSpaces, this.string);
		System.out.println(result);
	}
	/***
	 * This method returns string is given as the parameter of method.
	 * @return String was given as the parameter of method.
	 */
	@Override
	public String operation(String string) {
		return string;
	}
	/***
	 * The method is deigned for handle field string 
	 * This one is using Reflection API
	 */
	public void fieldHandler(Class<?> cl, String string) {
		if(string.equals(this.string)) {
			System.out.println("Before: " + this.string);
			try {
				Field field = cl.getDeclaredField("string");
				if(field.isAnnotationPresent(StringOperatorAnnotation.class)) {
					StringOperatorAnnotation annotation = field.getAnnotation(StringOperatorAnnotation.class);
					this.string = annotation.description();
					System.out.println("After: " + this.string);
				}
			} catch(NoSuchFieldException exc) {
				exc.printStackTrace();
			}
		} else {
			System.out.println(this.string);
		}
	}
	/***
	 * This method is designed for learn Reflection API
	 */
	public void fieldReflection() {
		StringOperator so = new StringOperator();
		Class<? extends StringOperator> stringOperator = so.getClass();
		try {
			Field field = stringOperator.getDeclaredField("string");
			field.setAccessible(true);
			try {
				String str = (String) field.get(new StringOperator());
				System.out.println("Bfore change: " + str);
				field.set(so, "TEST");
				System.out.println("After change: " + field.get(so));
			} catch(IllegalAccessException exc) {
				exc.printStackTrace();
			}
		} catch(NoSuchFieldException exc) {
			exc.printStackTrace();
		}
	}
	/***
	 * This method is designed for learn Reflection API
	 */
	public void methodReflection() {
		Class<StringOperator> stringOperator = StringOperator.class;
		Method[] methods = stringOperator.getDeclaredMethods();
		for(Method method : methods) {
			System.out.println(method);
		}
	}
	/***
	 * This method is designed for learn Reflection API
	 */
	public void annotationReflection() {
		Class<StringOperator> stringOperator = StringOperator.class;
		Method[] methods = stringOperator.getDeclaredMethods();
		for(Method method : methods) {
			Annotation[] annotations = method.getDeclaredAnnotations();
			for(Annotation annotation : annotations) {
				System.out.println(annotation);
			}
//			if(method.getDeclaredAnnotation(MethodAnnotation.class) != null) {
//				System.out.println("Method: " + method + ", Annotation: " + 
//											method.getAnnotation(MethodAnnotation.class));
//			}
		}
	}
	private String func(IStringOperation iso, String string) {
		return iso.operation(string);
	}
	private static String deleteSpaces(String string) {
		String result = "";
		for(int i = 0; i < string.length(); i++) {
			if(string.charAt(i) == ' ') {
				continue;
			}
			result += string.charAt(i);
		}
		return result;
	}
}
