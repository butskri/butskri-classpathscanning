package be.butskri.classpathscanning;

import java.util.Collection;
import java.util.HashSet;

public class Dependency {

	private ScannedClass scannedClass;
	private Path path;

	public Dependency(Path path, ScannedClass scannedClass) {
		this.path = path;
		this.scannedClass = scannedClass;
	}

	@Override
	public String toString() {
		return path.toString().concat(": ").concat(getType().getName());
	}

	public Class<?> getType() {
		return scannedClass.getType();
	}

	public Collection<? extends Dependency> findDependencies(ClassFilter classFilter) {
		Collection<Dependency> result = new HashSet<Dependency>();
		for (Dependency dependency : scannedClass.getDependencies(classFilter)) {
			result.add(dependency.prepend(path));
		}
		return result;
	}

	private Dependency prepend(Path basePath) {
		return new Dependency(new Path(basePath, this.path), scannedClass);
	}
}
