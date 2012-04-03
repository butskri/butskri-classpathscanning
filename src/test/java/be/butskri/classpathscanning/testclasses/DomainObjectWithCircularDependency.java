package be.butskri.classpathscanning.testclasses;

public class DomainObjectWithCircularDependency {

	@SuppressWarnings("unused")
	private NestedDomainObjectWithCircularDependency nestedObject;
	@SuppressWarnings("unused")
	private Integer myInteger;
}
