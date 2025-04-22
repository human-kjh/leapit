package com.example.leapit.common.techstack;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "tech_stack_tb")
@Entity
public class TechStack {
    @Id
    private String code;
}
