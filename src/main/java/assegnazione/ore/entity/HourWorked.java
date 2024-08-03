package assegnazione.ore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class HourWorked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hour_worked")
    private int idHourWorked;

    private String month;

    private Double holiday; //ore di vacanza

    private String day;

    private String number;

    private String year;

    private double hour;

    private String note;

    @Enumerated(EnumType.STRING)
    private Location place;

    private Double illness; // ore di malattia

    private Double hoursOff; // oer di permesso

    private Double extraWork; // ore di straordinario

    @ManyToOne()
    @JoinColumn(name = "id_company")
    private Company company;

    @ManyToOne()
    @JoinColumn(name = "id_user")
    private User user;

}
