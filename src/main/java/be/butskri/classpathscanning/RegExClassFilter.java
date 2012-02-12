package be.butskri.classpathscanning;

public class RegExClassFilter implements ClassFilter {

	private final String regex;

	public RegExClassFilter(String regex) {
		this.regex = regex;
	}

	@Override
	public boolean isAllowed(Class<?> clazz) {
		return clazz.getName().matches(regex);
	}

}
