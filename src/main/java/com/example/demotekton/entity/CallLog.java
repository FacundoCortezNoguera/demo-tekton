package com.example.demotekton.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "call_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CallLog {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    private String endpoint;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String parameters;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String response;

    private boolean error;
}
