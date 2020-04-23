public class ValueContainer {
	private Object object;
	private Type type;

	public ValueContainer(Type type, Object object) {
		this.type = type;
		this.object = object;
	}

	public boolean getAsBoolean() {
		return (boolean) object;
	}

	public String getAsString() {
		return (String) object;
	}

	public int getAsInteger() {
		return (int) object;
	}

	@Override
	public String toString() {
		switch (type) {
		case INT:
			return String.valueOf((int) object);
		case BOOLEAN:
			return String.valueOf((boolean) object);
		case STRING:
			return String.valueOf((String) object);
		default:
			return "No data type available to parse";
		}
	}
}
