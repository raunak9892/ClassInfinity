package com.mpr.classinfinity.Model;

import java.util.ArrayList;

public class Courses {

    private int id;
    private String url;
    private boolean isPaid;
    private String price;
    private String tittle;
    private String description;  // headline

    private ArrayList<instructors> instructorsList;
    private String courseThumbnail;

    public Courses(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<instructors> getInstructorsList() {
        return instructorsList;
    }

    public void setInstructorsList(ArrayList<instructors> instructorsList) {
        this.instructorsList = instructorsList;
    }

    public String getCourseThumbnail() {
        return courseThumbnail;
    }

    public void setCourseThumbnail(String courseThumbnail) {
        this.courseThumbnail = courseThumbnail;
    }

    public static class instructors{
        private String name;
        private String photo;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }


}
