package be.butskri.classpathscanning;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import java.util.Collection;

public class RegExClassFilterTest {

	@Test
	public void isAllowedGeeftTrueTerugIndienClassMatcht() {
		assertTrue(new RegExClassFilter(".*ing").isAllowed(String.class));
		assertTrue(new RegExClassFilter("java\\.lang.*").isAllowed(String.class));
	}

	@Test
	public void isAllowedGeeftFalseTerugIndienClassNietMatcht() {
		assertFalse(new RegExClassFilter(".*ing").isAllowed(Collection.class));
	}
}
