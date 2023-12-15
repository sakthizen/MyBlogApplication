package com.blog.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;


@Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name = "comments")
    public class Comment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column
        private String body;

        @Column
        private String email;

        @Column
        private String name;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "post_id", nullable = false)
        private Post post;
    }
