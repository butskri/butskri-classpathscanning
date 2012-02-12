package be.butskri.classpathscanning;

public interface ClassFilter {

	boolean isAllowed(Class<?> clazz);
}
