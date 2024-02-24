package com.reditus.daangn.image

import org.springframework.web.multipart.MultipartFile
import java.io.File

interface ImageService {
    /**
     * 이미지를 임시 저장소에 업로드합니다.
     * @return 업로드된 이미지의 ID 리스트
     */
    fun uploadTempImage(memberId: Long, imageFiles: List<MultipartFile>): List<String>

    /**
     * 이미지를 임시 저장소에서 가져옵니다.
     * @return 이미지 파일 리스트
     */
    fun getTempImage(memberId: Long, imageIds: List<String>): List<File>

    /**
     * 이미지를 임시 저장소에서 삭제합니다.
     */
    fun deleteTempImage(memberId: Long, imageId: Long)

    /**
     * 이미지를 영구 저장소에 업로드합니다.
     * @return 업로드된 이미지의 URL 리스트
     */
    fun uploadImage(memberId:Long, imageIds: List<String>): List<String>

    /**
     * 이미지를 영구 저장소에서 삭제합니다.
     */
    fun deleteImage(imageUrl: String)

}