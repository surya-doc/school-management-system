package com.project.SchoolManagementSystem.notice.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity(name = "notice")
@Table(name = "notice")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "heading")
    private String heading;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private Date start_date;

    @Column(name = "expiry_date")
    private Date expiry_date;

    @Column(name = "notice_image")
    private String notice_image;

    @Column(name = "status")
    private boolean status;
}
