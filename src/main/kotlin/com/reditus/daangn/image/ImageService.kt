package com.reditus.daangn.image

import org.springframework.web.multipart.MultipartFile
import java.io.File

interface ImageService {
    fun uploadTempImage(memberId: Long, imageFiles: List<MultipartFile>): List<String>
    fun getTempImage(memberId: Long, imageIds: List<String>): List<File>
    fun deleteTempImage(memberId: Long, imageId: Long)

    fun uploadImage(imageIds: List<String>): List<String>

    fun getImage(imageIds: List<String>): List<String>

    fun deleteImage(imageId: Long)

}