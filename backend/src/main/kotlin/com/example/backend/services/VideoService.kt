package com.example.backend.services

import com.example.backend.dtos.CreateReviewDto
import com.example.backend.dtos.FindVideosDto
import com.example.backend.dtos.UpdateVideoDto
import com.example.backend.dtos.VideoDto
import com.example.backend.models.ReviewModel
import com.example.backend.models.VideoModel
import com.example.backend.repositories.ReviewRepository
import com.example.backend.repositories.UserRepository
import com.example.backend.repositories.VideoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class VideoService(
    @Autowired private val videoRepository: VideoRepository,
    @Autowired private val reviewRepository: ReviewRepository,
    @Autowired private val jwtService: JwtService,
    @Autowired private val userRepository: UserRepository,
    @Autowired private val s3Service: S3Service
) {
    fun getVideos(page: Long): ResponseEntity<List<VideoModel>> {
        val videos = videoRepository.findAll(PageRequest.of(page.toInt(), 15))
        return ResponseEntity(videos.content, HttpStatus.OK)
    }

    fun getVideo(id: Long): ResponseEntity<VideoDto> {
        val video = videoRepository.findById(id).get()
        val response = VideoDto().fromModel(video)
        return ResponseEntity(response, HttpStatus.OK)
    }

    fun addReview(dto: CreateReviewDto, token: String): ResponseEntity<String> {
        val user = userRepository.findById(jwtService.verify(token)).get()
        val video = videoRepository.findById(dto.videoId.toLong()).get()
        val optionalReview = reviewRepository.findOneByUserAndVideo(user, video)
        if (optionalReview.isPresent) {
            val review = optionalReview.get()
            review.reviewType = dto.reviewType
            reviewRepository.save(review)
            return ResponseEntity("Review updated successfully", HttpStatus.OK)
        }

        val newReview = ReviewModel()
        newReview.reviewType = dto.reviewType
        newReview.user = user
        newReview.video = video
        reviewRepository.save(newReview)

        return ResponseEntity("Review has been left", HttpStatus.OK)
    }

    fun deleteVideo(videoId: Long, token: String): ResponseEntity<String> {
        val userId = jwtService.verify(token)
        val userVideos = userRepository.findById(userId).get().videos
        val video = userVideos.find { it.id == videoId }
        if (video != null) {
            videoRepository.delete(video)
            s3Service.delete(video.videoUrl)
            s3Service.delete(video.previewUrl)
            return ResponseEntity("Video deleted successfully", HttpStatus.OK)
        }
        return ResponseEntity("Video not found", HttpStatus.NOT_FOUND)
    }

    fun updateVideo(dto: UpdateVideoDto, token: String): ResponseEntity<String> {
        videoRepository.updateVideoById(dto.videoId, dto.newTitle, dto.newDescription)
        return ResponseEntity("Video updated successfully", HttpStatus.OK)
    }

    fun findVideos(dto: FindVideosDto): ResponseEntity<List<VideoModel>> {
        val videos = videoRepository.findVideosByPartOfTitle(dto.search)
        return ResponseEntity(videos, HttpStatus.OK)
    }

    fun createVideo(
        preview: MultipartFile,
        video: MultipartFile,
        title: String,
        description: String,
        token: String
    ): ResponseEntity<String> {
        val userId = jwtService.verify(token)
        val user = userRepository.findById(userId).get()

        val newVideo = VideoModel()
        newVideo.title = title
        newVideo.description = description
        newVideo.videoUrl = s3Service.upload(video)
        newVideo.previewUrl = s3Service.upload(preview)
        newVideo.user = user
        videoRepository.save(newVideo)

        return ResponseEntity("video created", HttpStatus.OK)
    }
}