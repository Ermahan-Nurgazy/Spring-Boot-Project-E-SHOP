package Hometask7.Entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "t_comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "addedDate")
    private Date addedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private ShopItems shopItem;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users author;

}
