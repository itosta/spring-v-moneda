package com.examen.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "HU_EMPLS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HuEmpls {

	@EmbeddedId	
	private HuEmplsId id;

    @Column(name = "CLAVE_MONEDA", length = 10)
    private String claveMoneda;

    @Column(name = "NOMBRE", length = 50)
    private String nombre;

    @Column(name = "APELLIDO_PATERNO", length = 50)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", length = 50)
    private String apellidoMaterno;

    @Column(name = "PUESTO", length = 50)
    private String puesto;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "NUM_CIA", referencedColumnName = "NUM_CIA", insertable = false, updatable = false),
        @JoinColumn(name = "CLAVE_MONEDA", referencedColumnName = "CLAVE_MONEDA", insertable = false, updatable = false)
    })
    private HuCatMoneda moneda;

}



