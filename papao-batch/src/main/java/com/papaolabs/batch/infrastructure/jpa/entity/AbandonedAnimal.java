package com.papaolabs.batch.infrastructure.jpa.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "abandoned_animal_tb")
public class AbandonedAnimal extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Integer age;
    private Float weight;
    @Enumerated(EnumType.ORDINAL)
    private GenderType genderType;
    @Enumerated(EnumType.ORDINAL)
    private NeuterType neuterType;
    @Enumerated(EnumType.STRING)
    private StateType stateType;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private AnimalKind kind;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "abandonedAnimal")
    private AnimalPost post;

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
