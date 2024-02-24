package com.reditus.daangn.image

import com.reditus.daangn.core.aop.TemporaryApi
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@Service
class ImageServiceV0 : ImageService {
    private val rootPath = System.getProperty("user.dir")
    private val imageFolder = "temp-images"
    override fun uploadTempImage(memberId: Long, imageFiles: List<MultipartFile>): List<String> {
        val folder = getFileFolder(memberId)
        if(!folder.exists()){
            folder.mkdirs()
        }
        val ids = imageFiles.map { multipartFile ->
            val uniqueId = UUID.randomUUID().toString() // UUID 생성
            val pos = multipartFile.originalFilename!!.lastIndexOf(".")
            val ext = multipartFile.originalFilename!!.substring(pos+1)

            val filePath = getFilePath(memberId,uniqueId,ext)
            val file = File(filePath)

            multipartFile.transferTo(file)

            uniqueId
        }
        return ids
    }

    override fun getTempImage(memberId: Long, imageIds: List<String>): List<File> {
        val folder = getFileFolder(memberId)
        val userFile = folder.listFiles()

        return userFile?.filter {
            val name = it.nameWithoutExtension
            val fileId = name.substringAfter("-")
            imageIds.contains(fileId)
        }?.toList() ?: emptyList()
    }

    override fun deleteTempImage(memberId: Long, imageId: Long) {
        val folder = getFileFolder(memberId)
        val userFile = folder.listFiles()
        userFile?.forEach {
            val name = it.nameWithoutExtension
            val fileId = name.substringAfter("-")
            if(fileId == imageId.toString()){
                it.delete()
            }
        }
    }

    @TemporaryApi
    override fun uploadImage(imageIds: List<String>): List<String> {
        return imageIds
    }

    @TemporaryApi
    override fun getImage(imageIds: List<String>): List<String> {
        return imageIds
    }

    @TemporaryApi
    override fun deleteImage(imageId: Long) {

    }

    private fun getFileFolder(memberId: Long): File {
        val folder = File("$rootPath/$imageFolder/$memberId")
        if(!folder.exists()){
            folder.mkdirs()
        }
        return folder
    }

    private fun getFilePath(memberId: Long, imageId: String, ext:String): String {
        return "$rootPath/$imageFolder/$memberId/${imageId}.${ext}"
    }
}