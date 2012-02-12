package be.butskri.classpathscanning;

import org.clapper.util.classutil.ClassFinder;
import org.clapper.util.classutil.ClassInfo;
import org.clapper.util.classutil.SubclassClassFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClassRepository {

	public List<Class<?>> findAllSubclasses(Class<?> type) {
		return findClasses(new SubclassClassFilter(type));
	}

	private List<Class<?>> findClasses(SubclassClassFilter filter) {
		ClassFinder finder = new ClassFinder();
		finder.addClassPath();

		Collection<ClassInfo> foundClasses = new ArrayList<ClassInfo>();
		finder.findClasses(foundClasses, filter);
		return toClasses(foundClasses);
	}

	private List<Class<?>> toClasses(Collection<ClassInfo> foundClasses) {
		List<Class<?>> result = new ArrayList<Class<?>>();
		for (ClassInfo classInfo : foundClasses) {
			result.add(getClass(classInfo));
		}
		return result;
	}

	private Class<?> getClass(ClassInfo classInfo) {
		try {
			return Class.forName(classInfo.getClassName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
