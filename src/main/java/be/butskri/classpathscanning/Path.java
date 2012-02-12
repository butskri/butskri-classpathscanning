package be.butskri.classpathscanning;

public class Path {

	private String path;

	public Path(String path) {
		this.path = path;
	}

	public Path(Path basePath, Path path2) {
		this.path = basePath.toString().concat(".").concat(path2.toString());
	}

	@Override
	public String toString() {
		return path;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !Path.class.isInstance(obj)) {
			return false;
		}
		return this.toString().equals(obj.toString());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}
}
