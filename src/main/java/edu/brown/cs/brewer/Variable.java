package edu.brown.cs.brewer;

public class Variable<T> {
  private T value;
  private Class<T> type;

  public Variable(T o, Class<T> t){
    this.value = o;
    this.type = t;
  }

  public T getValue(){
    return value;
  }

  public void setValue(Object newval){
    this.value = type.cast(newval);
  }

  public Class<T> getType(){
    return type;
  }

  @Override
  public boolean equals(Object obj){
    return value.equals(obj);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }
}
