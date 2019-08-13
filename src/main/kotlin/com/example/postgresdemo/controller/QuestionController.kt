package com.example.postgresdemo.controller

import com.example.postgresdemo.exception.ResourceNotFoundException
import com.example.postgresdemo.model.Question
import com.example.postgresdemo.repository.QuestionRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.DeleteMapping

@RestController
class QuestionController(@Autowired var questionRepository: QuestionRepository) {

    @GetMapping("/questions")
    fun questions(pageable: Pageable): Page<Question> {
        return questionRepository.findAll(pageable)
    }

    @PostMapping("/questions")
    fun createQuestion(@RequestBody question: Question): Question {
        LoggerFactory.getLogger(QuestionController::class.java).error("${question.title} ${question.description}")
        return questionRepository.save(question)
    }

    @PutMapping("/questions/{questionId}")
    fun updateQuestion(@PathVariable questionId: Long, @Valid @RequestBody questionRequest: Question): Question {
        return questionRepository.findById(questionId)
                .map { question ->
                    question.title = questionRequest.title
                    question.description = questionRequest.description
                    questionRepository.save(question)
                }.orElseThrow { ResourceNotFoundException("Question not found with id $questionId") }
    }

    @DeleteMapping("/questions/{questionId}")
    fun deleteQuestion(@PathVariable questionId: Long): ResponseEntity<*> {
        return questionRepository.findById(questionId)
                .map { question ->
                    questionRepository.delete(question)
                    ResponseEntity.ok().build<Unit>()
                }.orElseThrow{ ResourceNotFoundException("Question not found with id $questionId") }
    }
}