package com.examen.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "HU_CAT_MONEDA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HuCatMoneda {
	
	@EmbeddedId
    private HuCatMonedaId id;

    @Column(name = "DESCRIPCION", length = 50)
    private String descripcion;

    @Column(name = "SIMBOLO", length = 10)
    private String simbolo;

    @Column(name = "ESTATUS", length = 1)
    private String estatus;

}