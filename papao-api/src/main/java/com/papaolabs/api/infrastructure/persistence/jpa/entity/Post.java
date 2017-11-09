package com.papaolabs.api.infrastructure.persistence.jpa.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "post_tb")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(EnumType.STRING)
    private PostType postType;
    @Enumerated(EnumType.STRING)
    private GenderType genderType;
    @Enumerated(EnumType.STRING)
    private NeuterType neuterType;
    @Enumerated(EnumType.STRING)
    private StateType stateType;
    private String desertionId;
    private String noticeId;
    private Date noticeBeginDate;
    private Date noticeEndDate;
    private Date happenDate;
    private String happenPlace;
    private String feature;
    private String helperName;
    private String helperContact;
    private Integer age;
    private Float weight;
    private Long hitCount;
    private Long breedCode;
    private Long sidoCode;
    private Long gunguCode;
    private Long shelterCode;
    private String shelterContact;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "postId")
    private Collection<Image> images;
    private Boolean isDisplay;

    public enum PostType {
        SYSTEM, PROTECTING, ROADREPORT, MISSING;

        public static PostType getType(String name) {
            if (StringUtils.isEmpty(name)) {
                return null;
            }
            for (PostType type : PostType.values()) {
                if (type.name()
                        .equals(name)) {
                    return type;
                }
            }
            return SYSTEM;
        }
    }

    public enum GenderType {
        M, F, U;

        public static GenderType getType(String name) {
            if (StringUtils.isEmpty(name)) {
                return null;
            }
            for (GenderType type : GenderType.values()) {
                if (type.name()
                        .equals(name)) {
                    return type;
                }
            }
            return U;
        }
    }

    public enum NeuterType {
        Y, N, U;

        public static NeuterType getType(String name) {
            if (StringUtils.isEmpty(name)) {
                return null;
            }
            for (NeuterType type : NeuterType.values()) {
                if (type.name()
                        .equals(name)) {
                    return type;
                }
            }
            return U;
        }
    }

    public enum StateType {
        PROCESS("보호중"), RETURN("종료(반환)"), NATURALDEATH("종료(자연사)"), EUTHANASIA("종료(안락사)"), ADOPTION("종료(입양)"), UNKNOWN("알수없음");
        private final String code;

        StateType(String code) {
            this.code = code;
        }

        public static StateType getType(String name) {
            if (StringUtils.isEmpty(name)) {
                return null;
            }
            for (StateType type : StateType.values()) {
                if (type.code.equals(name)) {
                    return type;
                }
            }
            return UNKNOWN;
        }
    }
}
