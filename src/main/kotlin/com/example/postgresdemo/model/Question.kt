package com.example.postgresdemo.model

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
@Table(name = "questions")
class Question: AuditModel() {
    @Id
    @GeneratedValue(generator = "question_generator")
    @SequenceGenerator(
            name = "question_generator",
            sequenceName = "question_sequence",
            initialValue = 1000
    )
    var id: Long? = null

    @NotBlank
    @Size(min = 2, max = 100)
    var title: String? = null

    @Column(columnDefinition = "text")
    var description: String? = null
}