package com.papaolabs.batch.infrastructure.jpa.entity;

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
import javax.persistence.OneToOne;
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
    private PostType postType;
    private String desertionId;
    private String contact;
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
    @Enumerated(EnumType.ORDINAL)
    private GenderType genderType;
    @Enumerated(EnumType.ORDINAL)
    private NeuterType neuterType;
    @Enumerated(EnumType.STRING)
    private StateType stateType;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "postId")
    private Collection<Image> image;
    @OneToOne
    private Breed breed;
    @OneToOne
    private Region region;
    @OneToOne
    private Shelter shelter;

    public enum PostType {
        SYSTEM("01"), ABSENCE("02"), PROTECT("03");
        private String code;

        PostType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public enum GenderType {
        M(0), F(1), U(2);
        private Integer code;

        GenderType(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return this.code;
        }

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
        Y(0), N(1), U(2);
        private Integer code;

        NeuterType(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return this.code;
        }

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
