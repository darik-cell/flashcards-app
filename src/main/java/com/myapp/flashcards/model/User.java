package com.myapp.flashcards.model;

import jakarta.persistence.*;
        import lombok.*;
import org.aspectj.weaver.patterns.TypePatternQuestions;

import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private Set<Collection> collections;
}
