package com.kodeco.memeverse.models

fun likePost(post: Post, userId: String): Boolean {
    return if (!post.likedBy.contains(userId)) {
        post.likedBy.add(userId)
        true
    } else {
        false
    }
}

fun unlikePost(post: Post, userId: String): Boolean {
    return if (post.likedBy.contains(userId)) {
        post.likedBy.remove(userId)
        true
    } else {
        false
    }
}