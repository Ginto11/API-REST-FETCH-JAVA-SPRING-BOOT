package com.proyecto.API_REST_FETCH.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "facultad")
public class Facultad {
        
        @Id
        @Getter @Setter
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        
        
        @Getter @Setter
        @Column(name = "nombre_facultad", nullable = false, length = 200)
        private String nombre_facultad;
        
        
        @Getter @Setter
        @JsonIgnore
        @OneToOne(fetch = FetchType.LAZY, mappedBy = "facultad", cascade = CascadeType.ALL)
        private Estudiante estudiante;
        
}
