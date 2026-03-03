package com.devnotfound.talenthub.entity;

import com.devnotfound.talenthub.entity.enums.*;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "crawlerlogs")
@Data
public class CrawlerLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String title;

    /*@ManyToOne
    @JoinColumn(name = "tech_id")
    private Tech tech;

<<<<<<< HEAD
//    @ManyToOne
//    @JoinColumn(name = "position_id")
//    private Position position;
=======
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;*/
>>>>>>> d93066a (feat: excecao positionId tratada e expondo endpoints consulta de vaga)

    @Column(name = "city_name", length = 100)
    private String cityName;

    @Column(name = "uf_name", length = 50)
    private String ufName;

    @Column(name = "uf_abrev", length = 2)
    private String ufAbrev;

    @Column(name = "tech_level", length = 30)
    private String techLevel;

    @Column(name = "hiring_type", length = 15)
    private String hiringType;

    @Column(name = "salary_range", precision = 10, scale = 2)
    private BigDecimal salaryRange;

    @Column(columnDefinition = "TEXT")
    private String benefits;

    @Column(columnDefinition = "TEXT")
    private String requirements;

    @Column(name = "company_name", length = 100)
    private String companyName;

    @Column(name = "posting_link", length = 250)
    private String postingLink;

    @Column(name = "email_contact", length = 100)
    private String emailContact;

    @Column(name = "phone_contact", length = 20)
    private String phoneContact;

    @Column(name = "name_contact", length = 100)
    private String nameContact;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_mode")
    private WorkMode workMode;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "creation_user")
    private CreationUser creationUser;

    @Column(name = "plataform", length = 30)
    private String plataform;

}
