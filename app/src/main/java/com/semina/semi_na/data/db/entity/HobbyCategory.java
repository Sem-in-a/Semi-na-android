package com.semina.semi_na.data.db.entity;

public enum HobbyCategory {

    EXERCISE("운동"), FOOD("음식"), MUSIC("음악"), BOOK("책"), NULL("해당사항없음");

    private String hobbyCategory;

    HobbyCategory(String hobbyCategory) {
        this.hobbyCategory = hobbyCategory;
    }

    public String getHobbyCategory() {
        return hobbyCategory;
    }

}
