package com.trimv.crudgeneric.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "dynamic_apis")
@Getter
@Setter
@ToString
public class DynamicApi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;
    private String method;

    @Column(columnDefinition = "TEXT")
    private String requestSchema; // JSON Schema (Optional for validation)

    @Column(columnDefinition = "TEXT")
    private String response; // Static response or template

    // Getters and Setters
}
