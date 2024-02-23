package com.reditus.daangn.saleposts.entity

import com.reditus.daangn.core.entity.BaseTimeEntity
import jakarta.persistence.*

@Entity
class SalePostImage(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "sale_post_id")
    val salePost: SalePost,
    val imageUrl: String,
    val imageName: String,
) : BaseTimeEntity() {
    companion object {
        fun fixture(
            id: Long? = null,
            salePost: SalePost = SalePost.fixture(),
            imageUrl: String = "imageUrl",
            imageName: String = "imageName",
        ) = SalePostImage(
            id = id,
            salePost = salePost,
            imageUrl = imageUrl,
            imageName = imageName,
        )
    }
}