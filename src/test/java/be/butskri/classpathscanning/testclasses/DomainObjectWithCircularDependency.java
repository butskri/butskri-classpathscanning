package be.butskri.classpathscanning.testclasses;

public class DomainObjectWithCircularDependency {

	private NestedDomainObjectWithCircularDependency nestedObject;
	private Integer myInteger;
}
