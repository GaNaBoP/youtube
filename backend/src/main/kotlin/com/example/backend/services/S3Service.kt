package com.example.backend.services

import com.amazonaws.services.s3.AmazonS3
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

@Service
class S3Service (
    @Autowired private val s3Client: AmazonS3
) {
    @Value("\${bucket-name}")
    lateinit var bucketName: String

    @Value("\${endpoint-url}")
    lateinit var endpoint: String

    fun upload(file: MultipartFile): String {
        val convertedFile = convertMultiPartFileToFile(file)
        val newFileName = UUID.randomUUID().toString() + file.originalFilename
        s3Client.putObject(bucketName, newFileName, convertedFile)
        return "${endpoint}/${bucketName}/${newFileName}"
    }

    fun delete(fileName: String) {
        s3Client.deleteObject(bucketName, fileName)
    }

    private fun convertMultiPartFileToFile(file: MultipartFile): File {
        val convertedFile = File(file.originalFilename!!)
        try {
            FileOutputStream(convertedFile).use { fos ->
                fos.write(file.bytes)
            }
        } catch (e: IOException) {
            println(e)
        }
        return convertedFile
    }
}