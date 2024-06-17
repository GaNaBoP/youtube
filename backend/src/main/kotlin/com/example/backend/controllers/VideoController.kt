package com.example.backend.controllers

import com.example.backend.dtos.CreateReviewDto
import com.example.backend.dtos.FindVideosDto
import com.example.backend.dtos.UpdateVideoDto
import com.example.backend.dtos.VideoDto
import com.example.backend.models.VideoModel
import com.example.backend.services.VideoService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Controller
@RequestMapping("/videos")
class VideoController(
    @Autowired val videoService: VideoService
) {

    @GetMapping("/getMany/{page}")
    fun getVideos(@PathVariable page: Long): ResponseEntity<List<VideoModel>> {
        return videoService.getVideos(page)
    }

    @GetMapping("/getOne/{id}")
    fun getVideo(@PathVariable id: Long): ResponseEntity<VideoDto> {
        return try {
            videoService.getVideo(id)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteVideo(
        @PathVariable id: Long,
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String
    ): ResponseEntity<String> {
        return videoService.deleteVideo(id, token)
    }

    @PostMapping("/addReview")
    fun addReview(
        @RequestBody dto: CreateReviewDto,
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String
    ): ResponseEntity<String> {
        return videoService.addReview(dto, token)
    }

    @PutMapping("/")
    fun updateVideo(
        @Valid @RequestBody dto: UpdateVideoDto,
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String
    ): ResponseEntity<String> {
        return try {
            videoService.updateVideo(dto, token)
        } catch (e: Exception) {
            ResponseEntity("An error occurred", HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/find")
    fun findVideo(@Valid @RequestBody dto: FindVideosDto): ResponseEntity<List<VideoModel>> {
        return videoService.findVideos(dto)
    }

    @PostMapping("/create")
    fun createVideo(
        @RequestParam preview: MultipartFile,
        @RequestParam video: MultipartFile,
        @RequestParam title: String,
        @RequestParam description: String,
        @RequestHeader(HttpHeaders.AUTHORIZATION) token: String
    ): ResponseEntity<String> {
        return try {
            videoService.createVideo(preview, video, title, description, token)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }
}