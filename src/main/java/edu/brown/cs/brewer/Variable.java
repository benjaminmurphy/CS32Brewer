package edu.brown.cs.brewer;

public class Variable {
  private Object value;
  private Class<?> type;

  public Variable(Object o, Class<?> t) {
    if (!t.isAssignableFrom(o.getClass())) {
      throw new IllegalArgumentException("Variable data is of type \""
          + o.getClass() + "\" while the given type is \"" + t + "\".");
    }
    this.value = o;
    this.type = t;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object newval) {
    this.value = type.cast(newval);
  }

  public Class<?> getType() {
    return type;
  }

  @Override
  public boolean equals(Object obj) {
    return value.equals(obj);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }
}
