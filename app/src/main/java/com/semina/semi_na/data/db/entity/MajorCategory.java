package com.semina.semi_na.data.db.entity;

public enum MajorCategory {
    IT("IT대학"), ENGINEERING("공과대학"), BUSINESS("경영대학"), SCIENCE("자연과학대학"), SOCIAL("사회과학대학"), HUMANITY("인문대학"), LAW("법과대학"), ECONOMIC("경제통상대학"), NULL("분류없음");
    private String majorName;

    MajorCategory(String majorName) {
        this.majorName = majorName;
    }

    public String getMajorName() {
        return majorName;
    }
}
