package com.semina.semi_na.data.db.entity;

public enum HobbyCategory {


    EXERCISE("운동"), FOOD("요리"), MUSIC("음악"), BOOK("독서"), NULL("분류없음");
    private String hobbyName;

    HobbyCategory(String hobbyName) {
        this.hobbyName = hobbyName;
    }

    public String getHobbyName() {
        return hobbyName;
    }

}
