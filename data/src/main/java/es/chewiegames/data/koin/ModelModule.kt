package es.chewiegames.data.koin

import es.chewiegames.data.model.PostData
import es.chewiegames.data.model.UserData

fun provideUser(): UserData {
    return UserData()
}

fun providePost(): PostData {
    return PostData()
}

fun provideFeedPosts(): ArrayList<PostData> {
    return arrayListOf()
}

fun provideLikedPostsByUser(): ArrayList<PostData> {
    return arrayListOf()
}
