package com.example.postgresdemo.controller

import com.example.postgresdemo.exception.ResourceNotFoundException
import com.example.postgresdemo.model.Answer
import com.example.postgresdemo.repository.AnswerRepository
import com.example.postgresdemo.repository.QuestionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
class AnswerController(@Autowired val answerRepository: AnswerRepository, @Autowired val questionRepository: QuestionRepository) {
    @GetMapping("/questions/{questionId}/answers")
    fun getAnswers(@PathVariable questionId: Long): List<Answer> {
        return answerRepository.findByQuestionId(questionId)
    }

    @PostMapping("/questions/{questionId}/answers")
    fun addAnswer(@PathVariable questionId: Long, @Valid @RequestBody answer: Answer): Answer {
        return questionRepository.findById(questionId)
                .map { question ->
                    answer.question = question
                    answerRepository.save(answer)
                }.orElseThrow { ResourceNotFoundException("Question not found with id $questionId") }
    }

    @PutMapping("/questions/{questionId}/answers/{answerId}")
    fun updateAnswer(@PathVariable questionId: Long, @PathVariable answerId: Long, @Valid @RequestBody answerRequest: Answer): Answer {
        if (!questionRepository.existsById(questionId)) {
            throw ResourceNotFoundException("Question not found with id $questionId")
        }

        return answerRepository.findById(answerId)
                .map { answer ->
                    answer.text = answerRequest.text
                    answerRepository.save(answer)
                }.orElseThrow { ResourceNotFoundException("Answer not found with id $answerId") }
    }

    @DeleteMapping("/questions/{questionId}/answers/{answerId}")
    fun deleteAnswer(@PathVariable questionId: Long, @PathVariable answerId: Long): ResponseEntity<Unit> {
        if (!questionRepository.existsById(questionId)) {
            throw ResourceNotFoundException("Question not found with id $questionId")
        }

        return answerRepository.findById(answerId)
                .map { answer ->
                    answerRepository.delete(answer)
                    ResponseEntity.ok().build<Unit>()
                }.orElseThrow { ResourceNotFoundException("Answer not found with id $answerId") }
    }
}