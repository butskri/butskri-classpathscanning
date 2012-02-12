package be.butskri.classpathscanning;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ScannedClass {

	private final Class<?> type;
	private Set<Class<?>> exclusions = new HashSet<Class<?>>();
	private boolean scanSubclasses = true;

	public ScannedClass(Class<?> type) {
		this(type, new HashSet<Class<?>>());
	}

	public ScannedClass(Class<?> type, Set<Class<?>> exclusions) {
		this.type = type;
		this.exclusions.add(type);
		this.exclusions.addAll(exclusions);
	}

	public Class<?> getType() {
		return type;
	}

	public Set<Dependency> getDependencies(ClassFilter classFilter) {
		Set<Dependency> result = new HashSet<Dependency>();
		for (Dependency dependency : getDependencies()) {
			if (classFilter.isAllowed(dependency.getType())) {
				result.add(dependency);
			} else {
				result.addAll(dependency.findDependencies(classFilter));
			}
		}
		return result;
	}

	private Collection<Dependency> getDependencies() {
		Collection<Dependency> dependencies = new HashSet<Dependency>();
		dependencies.addAll(getFieldDependencies());
		dependencies.addAll(getSuperClassDependencies());
		if (scanSubclasses) {
			dependencies.addAll(getSubclassDependencies());
		}
		dependencies.removeAll(findDependenciesToBeExcluded(dependencies));
		return dependencies;
	}

	private Collection<? extends Dependency> findDependenciesToBeExcluded(Collection<Dependency> dependencies) {
		Collection<Dependency> result = new HashSet<Dependency>();
		for (Dependency dependency : dependencies) {
			if (exclusions.contains(dependency.getType())) {
				result.add(dependency);
			}
		}
		return result;
	}

	private Collection<? extends Dependency> getSubclassDependencies() {
		Set<Dependency> result = new HashSet<Dependency>();
		for (Class<?> subtype : new ClassRepository().findAllSubclasses(this.getType())) {
			result.add(new Dependency(new Path("<implementor " + subtype.getName() + ">"), createScannedClass(subtype)));
		}
		return result;
	}

	private Collection<Dependency> getSuperClassDependencies() {
		Collection<Dependency> result = new HashSet<Dependency>();
		if (type.getSuperclass() != null && !Object.class.equals(type.getSuperclass())) {
			ScannedClass scannedClass = createScannedClass(type.getSuperclass());
			scannedClass.scanSubclasses = false;
			result.add(new Dependency(new Path("<super>"), scannedClass));
		}
		return result;
	}

	private Collection<? extends Dependency> getFieldDependencies() {
		Collection<Dependency> result = new HashSet<Dependency>();
		for (Field field : getType().getDeclaredFields()) {
			if (!Modifier.isStatic(field.getModifiers())) {
				result.add(createDependency(field));
			}
		}
		return result;
	}

	private Dependency createDependency(Field field) {
		return new Dependency(new Path(field.getName()), createScannedClass(field.getType()));
	}

	private ScannedClass createScannedClass(Class<?> type) {
		return new ScannedClass(type, exclusions);
	}

}
