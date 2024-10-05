package ar.edu.utn.frc.tup.lciii.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "countries")
public class CountryEntity {

    //c. Guardar en la base de datos: Luego, se debe guardar en la base de datos los países seleccionados, incluyendo
    // los campos de nombre, código, población (population) y área (Ademas de un id autoincremental).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String code;
    @Column
    private long population;
    @Column
    private double area;

    //   private String name;
    //    private long population;
    //    private double area;
    //    private String code;
    //    private String region;
    //    private List<String> borders;
    //    private Map<String, String> languages;


}
