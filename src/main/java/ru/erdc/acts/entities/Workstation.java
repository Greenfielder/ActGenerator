package ru.erdc.acts.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.erdc.acts.services.WorkstationsService;


@Entity
@Table(name = "workstations")
@Data
@NoArgsConstructor
public class Workstation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "owner")
    private String owner;

    @Column(name = "computer")
    private String computer;

    @Column(name = "serial_number")
    private String serial_number;

    @Column(name = "location")
    private String location;

    @Column(name = "start_date")
    private String start_date;

    @Column(name = "file_path")
    private String file_Path;
    public Workstation(Long id, String owner, String computer, String serial_number, String location, String start_date, String file_Path) {
        this.id = id;
        this.owner = owner;
        this.computer = computer;
        this.serial_number = serial_number;
        this.location = location;
        this.start_date = start_date;
        this.file_Path = file_Path;
    }
}
