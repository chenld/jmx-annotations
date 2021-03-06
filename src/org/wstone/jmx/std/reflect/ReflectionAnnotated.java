package org.wstone.jmx.std.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.enterprise.inject.spi.Annotated;

/**
 * Annotated object based only on reflection.
 */
public class ReflectionAnnotated implements Annotated, BaseTypeAnnotated
{
  private static final LinkedHashSet<Annotation> _emptyAnnSet
    = new  LinkedHashSet<Annotation>();

  private static final Annotation []_emptyAnnArray = new Annotation[0];

  private BaseType _type;

  private Set<Type> _typeSet;

  private LinkedHashSet<Annotation> _annSet;

  private Annotation []_annArray;

  protected ReflectionAnnotated(BaseType type,
                                Set<Type> typeClosure,
                                Annotation []annList)
  {
    _type = type;
    _typeSet = typeClosure;

    if (annList != null && annList.length > 0) {
      _annSet = new LinkedHashSet<Annotation>();

      for (Annotation ann : annList) {
        if (ann != null) {
          _annSet.add(ann);
        }
      }

      _annArray = new Annotation[_annSet.size()];
      _annSet.toArray(_annArray);
    }
    else {
      _annSet = _emptyAnnSet;
      _annArray = _emptyAnnArray;
    }
  }

  /**
   * Returns the base type of the annotated member.
   */
  @Override
  public Type getBaseType()
  {
    return _type.toType();
  }
  
  @Override
  public BaseType getBaseTypeImpl()
  {
    return _type;
  }
  
  @Override
  public Set<VarType<?>> getTypeVariables()
  {
    HashSet<VarType<?>> typeVariables = new HashSet<VarType<?>>();
    
    fillTypeVariables(typeVariables);
    
    return typeVariables;
  }

  protected void fillTypeVariables(Set<VarType<?>> typeVariables)
  {
    getBaseTypeImpl().fillSyntheticTypes(typeVariables);
  }

  @Override
  public HashMap<String,BaseType> getBaseTypeParamMap()
  {
    return _type.getParamMap();
  }

  /**
   * Returns all the types implemented by the member.
   */
  public Set<Type> getTypeClosure()
  {
    return _typeSet;
  }

  /**
   * Returns the introspected annotations
   */
  public Set<Annotation> getAnnotations()
  {
    return _annSet;
  }

  /**
   * Returns the matching annotation
   */
  public <T extends Annotation> T getAnnotation(Class<T> annType)
  {
    for (Annotation ann : _annArray) {
      if (annType.equals(ann.annotationType()))
        return (T) ann;
    }

    return null;
  }

  protected void addAnnotation(Annotation ann)
  {
    if (ann == null) {
      return;
    }
    
    if (_annSet == _emptyAnnSet)
      _annSet = new LinkedHashSet<Annotation>();

    _annSet.add(ann);
    _annArray = new Annotation[_annSet.size()];
    _annSet.toArray(_annArray);
  }

  /**
   * Returns true if the annotation is present)
   */
  @Override
  public boolean isAnnotationPresent(Class<? extends Annotation> annType)
  {
    for (Annotation ann : _annArray) {
      if (annType.equals(ann.annotationType()))
        return true;
    }

    return false;
  }
  
  @Override
  public void addOverrideAnnotation(Annotation ann)
  {
    addAnnotation(ann);
  }

  @Override
  public String toString()
  {
    return getClass().getSimpleName() + "[" + _type + "]";
  }
}
