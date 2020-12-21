package Hometask7.Entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "t_pictures")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pictures {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "date")
    private Date addedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private ShopItems shopItem;

}
