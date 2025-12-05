package com.examen.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HuEmplsId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "NUM_CIA", nullable = false)
    private Integer numCia;

    @Column(name = "NUM_EMP", nullable = false)
    private Integer numEmp;
}