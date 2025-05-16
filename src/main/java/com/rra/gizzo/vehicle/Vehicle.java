package com.rra.gizzo.vehicle;


import com.rra.gizzo.owner.Owner;
import com.rra.gizzo.platenumbers.PlateNumber;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Vehicle {
    @Id
    @GeneratedValue
    private UUID id;

    private String chassisNumber;
    private String manufacturerCompany;
    private int manufactureYear;
    private double price;
    private String modelName;

    @ManyToOne
    @JoinColumn(name = "current_owner_id", nullable = false)
    private Owner currentOwner;

    @OneToOne
    @JoinColumn(name = "plate_number_id", unique = true)
    private PlateNumber plateNumber;
}
