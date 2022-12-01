package com.proyecto.API_REST_FETCH.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "estudiante")
public class Estudiante {
        
        @Id
        @GeneratedValue(strategy = IDENTITY)
        @Getter @Setter
        private Integer id;
        
        @Getter @Setter
        @Column(name = "nombre", nullable = false, length = 50)
        private String nombre;
        
        @Getter @Setter
        @Column(name = "apellido", nullable = false, length = 50)
        private String apellido;
        
        @Getter @Setter
        @Column(name = "edad", nullable = false, length = 2)
        private Integer edad;
       
        @Getter @Setter
        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "id_universidad")
        private Universidad universidad;  
        
}
