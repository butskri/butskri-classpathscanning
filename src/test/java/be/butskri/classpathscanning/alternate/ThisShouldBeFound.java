package be.butskri.classpathscanning.alternate;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.Set;

import net.sf.extcos.ComponentQuery;
import net.sf.extcos.ComponentScanner;

import org.junit.Test;
import org.junit.runner.RunWith;

public class ThisShouldBeFound {

	@Test
	public void test() {
		Set<Class<?>> classes = new ComponentScanner().getClasses(new ComponentQuery() {

			@Override
			protected void query() {
				select().from("be.butskri.classpathscanning.alternate").returning(allAnnotatedWith(Test.class));
			}
		});
		assertEquals(2, classes.size());
		assertTrue(classes.contains(ThisShouldBeFound.class));
		assertTrue(classes.contains(SomeOtherClassToBeFound.class));
	}

	@Test
	public void test2() {
		Set<Class<?>> classes = new ComponentScanner().getClasses(new ComponentQuery() {

			@Override
			protected void query() {
				select().from("be.butskri.classpathscanning.alternate").returning(allAnnotatedWith(RunWith.class));
			}
		});
		assertEquals(1, classes.size());
		assertTrue(classes.contains(FindAnnotatedClassesTest.class));
	}
}
