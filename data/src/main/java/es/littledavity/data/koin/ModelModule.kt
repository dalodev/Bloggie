package es.littledavity.data.koin

import es.littledavity.data.model.PostData
import es.littledavity.data.model.UserData

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
