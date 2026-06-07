package org.example.lesson2.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "\"User\"")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String email;

    @Column(nullable = false)
    private Integer age;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

}
