package be.butskri.classpathscanning;

public class ClassFilterUtils {

	public static ClassFilter not(final ClassFilter classFilter) {
		return new ClassFilter() {

			@Override
			public boolean isAllowed(Class<?> clazz) {
				return !classFilter.isAllowed(clazz);
			}
		};
	}
}
