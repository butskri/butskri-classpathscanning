package be.butskri.classpathscanning.testclasses;

public class NestedDomainObjectWithCircularDependency {

	@SuppressWarnings("unused")
	private DomainObjectWithCircularDependency nestedObject;
	@SuppressWarnings("unused")
	private Integer myNestedInteger;
}
