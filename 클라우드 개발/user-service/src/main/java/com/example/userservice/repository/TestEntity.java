package com.example.userservice.repository;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "test")
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "no")
    private Long no;

    @Column(name = "class")
    private Integer cls;

    @Column(name = "grade")
    private Integer grade;

    @Column(name = "admission")
    private LocalDateTime addmission;

    @Column(name = "school")
    private String school;

    public static TestEntity makeTest(Long no,Integer cls, Integer grade, LocalDateTime addmission,String school) {
        return TestEntity.builder()
                .no(no)
                .cls(cls)
                .grade(grade)
                .addmission(addmission)
                .school(school)
                .build();
    }


}
