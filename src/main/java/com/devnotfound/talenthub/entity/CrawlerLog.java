package com.devnotfound.talenthub.entity;

import com.devnotfound.talenthub.entity.enums.*;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "crawlerlogs")
public class CrawlerLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String title;

    @ManyToOne
    @JoinColumn(name = "tech_id")
    private Tech tech;

//    @ManyToOne
//    @JoinColumn(name = "position_id")
//    private Position position;

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

    @Column(length = 30)
    private String plataform;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Tech getTech() {
        return tech;
    }

    public void setTech(Tech tech) {
        this.tech = tech;
    }

//    public Position getPosition() {
//        return position;
//    }
//
//    public void setPosition(Position position) {
//        this.position = position;
//    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUfName() {
        return ufName;
    }

    public void setUfName(String ufName) {
        this.ufName = ufName;
    }

    public String getUfAbrev() {
        return ufAbrev;
    }

    public void setUfAbrev(String ufAbrev) {
        this.ufAbrev = ufAbrev;
    }

    public String getTechLevel() {
        return techLevel;
    }

    public void setTechLevel(String techLevel) {
        this.techLevel = techLevel;
    }

    public String getHiringType() {
        return hiringType;
    }

    public void setHiringType(String hiringType) {
        this.hiringType = hiringType;
    }

    public BigDecimal getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(BigDecimal salaryRange) {
        this.salaryRange = salaryRange;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPostingLink() {
        return postingLink;
    }

    public void setPostingLink(String postingLink) {
        this.postingLink = postingLink;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public String getPhoneContact() {
        return phoneContact;
    }

    public void setPhoneContact(String phoneContact) {
        this.phoneContact = phoneContact;
    }

    public String getNameContact() {
        return nameContact;
    }

    public void setNameContact(String nameContact) {
        this.nameContact = nameContact;
    }

    public WorkMode getWorkMode() {
        return workMode;
    }

    public void setWorkMode(WorkMode workMode) {
        this.workMode = workMode;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public CreationUser getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(CreationUser creationUser) {
        this.creationUser = creationUser;
    }

    public String getPlataform() {
        return plataform;
    }

    public void setPlataform(String plataform) {
        this.plataform = plataform;
    }
}
