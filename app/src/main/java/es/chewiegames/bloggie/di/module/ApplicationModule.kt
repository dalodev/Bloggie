package es.chewiegames.bloggie.di.module

import dagger.Module
import es.chewiegames.bloggie.BloggieApplication

@Module
class ApplicationModule constructor(mApplication: BloggieApplication?) {

    private var mApplication: BloggieApplication? = mApplication
}
