package com.fy.flower.sample.model;

import java.io.Serializable;

public class Clazz implements Serializable{

  private static final long serialVersionUID = 1L;
  private int id;
  private String className;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

}
