package com.example.taskmanagersystemapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Comments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "taskId")
    private Task task;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "authorId")
    private User author;
}
