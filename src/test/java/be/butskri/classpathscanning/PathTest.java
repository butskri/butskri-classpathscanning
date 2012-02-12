package be.butskri.classpathscanning;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

public class PathTest {

	private static final String PATH1 = "path1";
	private static final String PATH2 = "path2";

	@Test
	public void toStringGeeftPathTerug() {
		assertEquals(PATH1, new Path(PATH1).toString());
		assertEquals(PATH2, new Path(PATH2).toString());
	}

	@Test
	public void hashCodeGeeftGelijkeWaardenAlsPathsGelijkZijn() {
		assertEquals(new Path(PATH1).hashCode(), new Path(PATH1).hashCode());
		assertEquals(new Path(PATH2).hashCode(), new Path(PATH2).hashCode());
	}

	@Test
	public void equalsReturnsTrueAlsPathsGelijkZijn() {
		assertTrue(new Path(PATH1).equals(new Path(PATH1)));
		assertTrue(new Path(PATH2).equals(new Path(PATH2)));
	}

	@Test
	public void equalsReturnsFalseAlsArgumentNullIs() {
		assertFalse(new Path(PATH1).equals(null));
		assertFalse(new Path(PATH2).equals(null));
	}

	@Test
	public void equalsReturnsFalseAlsPathsNietGelijkZijn() {
		assertFalse(new Path(PATH1).equals(new Path(PATH2)));
		assertFalse(new Path(PATH2).equals(new Path(PATH1)));
	}

}
