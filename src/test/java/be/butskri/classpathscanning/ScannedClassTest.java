package be.butskri.classpathscanning;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import be.butskri.classpathscanning.testclasses.DomainObject;
import be.butskri.classpathscanning.testclasses.DomainObjectWithCircularDependency;
import be.butskri.classpathscanning.testclasses.DomainObjectWithReferenceToAnInterface;
import be.butskri.classpathscanning.testclasses.DomainObjectWithSpecificField;
import be.butskri.classpathscanning.testclasses.SubDomainObject;

public class ScannedClassTest {

	@Mock
	private ClassFilter classFilterMock;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getDependenciesBevatGenesteProperties() {
		when(classFilterMock.isAllowed(Integer.class)).thenReturn(true);

		ScannedClass scannedClass = new ScannedClass(DomainObject.class);
		assertDependencies(scannedClass.getDependencies(classFilterMock), "myInt: java.lang.Integer", "someOtherDomainObject.myOtherInt: java.lang.Integer");
	}

	@Test
	public void getDependenciesBevatSuperClassProperties() {
		when(classFilterMock.isAllowed(Integer.class)).thenReturn(true);

		ScannedClass scannedClass = new ScannedClass(SubDomainObject.class);
		assertDependencies(scannedClass.getDependencies(classFilterMock), "<super>.myInt: java.lang.Integer",
				"<super>.someOtherDomainObject.myOtherInt: java.lang.Integer");
	}

	@Test
	public void getDependenciesKanCirculaireDependenciesAan() {
		when(classFilterMock.isAllowed(Integer.class)).thenReturn(true);

		ScannedClass scannedClass = new ScannedClass(DomainObjectWithCircularDependency.class);
		assertDependencies(scannedClass.getDependencies(classFilterMock), //
				"myInteger: java.lang.Integer", // t
				"nestedObject.myNestedInteger: java.lang.Integer");

	}

	@Test
	public void getDependenciesBevatImplementorProperties() {
		when(classFilterMock.isAllowed(Integer.class)).thenReturn(true);

		ScannedClass scannedClass = new ScannedClass(DomainObjectWithReferenceToAnInterface.class);
		assertDependencies(scannedClass.getDependencies(classFilterMock),
				"someObject.<implementor be.butskri.classpathscanning.testclasses.SomeImplementor>.someInteger: java.lang.Integer",
				"someObject.<implementor be.butskri.classpathscanning.testclasses.SomeOtherImplementor>.someOtherInteger: java.lang.Integer");
	}

	@Test
	public void getDependenciesGeeftSuperClassImplementorsNietTerug() {
		when(classFilterMock.isAllowed(Integer.class)).thenReturn(true);

		ScannedClass scannedClass = new ScannedClass(DomainObjectWithSpecificField.class);
		assertDependencies(scannedClass.getDependencies(classFilterMock), //
				"someImplementor.someInteger: java.lang.Integer", //
				"subclass1.integer1: java.lang.Integer");

	}

	private void assertDependencies(Set<Dependency> dependencies, String... expectedDependencies) {
		Object[] expectedValues = expectedDependencies;
		assertThat(toStringSet(dependencies)).containsOnly(expectedValues);
	}

	private Set<String> toStringSet(Set<Dependency> dependencies) {
		Set<String> result = new HashSet<String>();
		for (Dependency dependency : dependencies) {
			result.add(dependency.toString());
		}
		return result;
	}
}
