package com.example.backend.dtos

import com.example.backend.enums.ReviewTypes
import com.example.backend.models.VideoModel

class VideoDto : VideoModel() {
    var likes: Int = 0
    var dislikes: Int = 0

    fun fromModel(model: VideoModel): VideoDto {
        val videoDto = VideoDto()
        videoDto.id = model.id
        videoDto.title = model.title
        videoDto.description = model.description
        videoDto.videoUrl = model.videoUrl
        videoDto.previewUrl = model.previewUrl
        videoDto.comments = model.comments

        for (i in model.reviews) {
            if (i.reviewType == ReviewTypes.LIKE) videoDto.likes++
            if (i.reviewType == ReviewTypes.DISLIKE) videoDto.dislikes++
        }
        return videoDto
    }
}