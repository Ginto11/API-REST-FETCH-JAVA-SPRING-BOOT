package com.proyecto.API_REST_FETCH.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "universidad", uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre"})})
public class Universidad {
        
        @Id
        @Setter @Getter
        @GeneratedValue(strategy = IDENTITY)
        private Integer idUniversidad;
        
        @Getter @Setter
        @Column(name = "nombre", nullable = false, length = 100)
        private String nombre;
        
        @Getter @Setter
        @OneToMany(fetch = FetchType.LAZY, mappedBy = "universidad", orphanRemoval = true, cascade = CascadeType.ALL )
        private List<Estudiante> estudiantes;
        
}
