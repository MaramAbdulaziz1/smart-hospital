package com.team10.smarthospital.model.enums;

import lombok.Getter;

@Getter
public enum Relationship {
    PARENT(1, "Parent"),
    SPOUSE(2, "Spouse"),
    CHILD(3, "Child"),
    SIBLING(4, "Sibling"),
    OTHER_RELATIVE(5, "Other Relative"),
    FRIEND(6, "Friend"),
    OTHER(7, "Other");

    private final Integer code;
    private final String relationship;

    Relationship(Integer code, String relationship) {
        this.code = code;
        this.relationship = relationship;
    }

    public static String getRelationshipName(Integer relationshipCode) {
        for (Relationship relationship : Relationship.values()) {
            if (relationship.getCode().equals(relationshipCode)) {
                return relationship.getRelationship();
            }
        }
        return null;
    }
}
