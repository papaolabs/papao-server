package com.papaolabs.batch.infrastructure.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "abandoned_animal_tb")
public class AbandonedAnimal extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long kindId;
    private String desertionId;
    private Integer age;
    private Float weight;
    @Enumerated(EnumType.ORDINAL)
    private GenderType genderType;
    @Enumerated(EnumType.ORDINAL)
    private NeuterType neuterType;
    @Enumerated(EnumType.STRING)
    private StateType stateType;

    public enum GenderType {
        M(0), F(1), U(2);
        private Integer code;

        GenderType(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return this.code;
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
    }

    public enum StateType {
        PROCESS("보호중"), RETURN("종료(반환)"), NATURALDEATH("종료(자연사)"), EUTHANASIA("종료(안락사)"), ADOPTION("종료(입양)");
        private final String code;

        StateType(String code) {
            this.code = code;
        }
    }
}
