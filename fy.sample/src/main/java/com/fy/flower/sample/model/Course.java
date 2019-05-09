package com.fy.flower.sample.model;


import java.io.Serializable;

public class Course implements Serializable{

  private int id;
  private String courseName;
  private int credit;
  private int period;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }


  public int getCredit() {
    return credit;
  }

  public void setCredit(int credit) {
    this.credit = credit;
  }


  public int getPeriod() {
    return period;
  }

  public void setPeriod(int period) {
    this.period = period;
  }

}
