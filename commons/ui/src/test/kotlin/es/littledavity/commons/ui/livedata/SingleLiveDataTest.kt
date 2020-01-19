/*
 * Copyright 2020 littledavity
 */
package es.littledavity.commons.ui.livedata

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import es.littledavity.commons.ui.extensions.observe
import es.littledavity.libraries.testutils.lifecycle.TestLifecycleOwner
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.anyString
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

class SingleLiveDataTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var lifecycleOwner: LifecycleOwner

    @Before
    fun setUp() {
        lifecycleOwner = TestLifecycleOwner()
    }

    @Test
    fun observingSingleLiveData_WhenPostStringValue_ShouldTriggerOneEvent() {
        val singleLiveData = SingleLiveData<String>()
        val observerPostValue = "Event Value"
        val observer = mock<(String) -> Unit>()
        val observerCaptor = argumentCaptor<String>()

        lifecycleOwner.observe(singleLiveData, observer)
        singleLiveData.postValue(observerPostValue)

        verify(observer).invoke(observerCaptor.capture())
        assertEquals(observerPostValue, observerCaptor.lastValue)
    }

    @Test
    fun observingSingleLiveData_WhenPostMultipleIntValue_ShouldTriggerMultipleTimes() {
        val singleLiveData = SingleLiveData<Int>()
        val observerPostValue = 1
        val observer = mock<(Int) -> Unit>()
        val observerCaptor = argumentCaptor<Int>()

        lifecycleOwner.observe(singleLiveData, observer)
        singleLiveData.postValue(observerPostValue)
        singleLiveData.postValue(observerPostValue)

        verify(observer, times(2)).invoke(observerCaptor.capture())
        assertEquals(observerPostValue, observerCaptor.lastValue)

        singleLiveData.postValue(observerPostValue)

        Mockito.verify(observer, times(3)).invoke(observerCaptor.capture())
        assertEquals(observerPostValue, observerCaptor.lastValue)
    }

    @Test
    fun multipleObservingSingleLiveData_WhenPostIntValue_ShouldTriggerOnlyFirstObserver() {
        val singleLiveData = SingleLiveData<String>()
        val observerPostValue = "Event Value"
        val observer1 = mock<(String) -> Unit>()
        val observer2 = mock<(String) -> Unit>()
        val observer3 = mock<(String) -> Unit>()
        val observer1Captor = argumentCaptor<String>()

        lifecycleOwner.observe(singleLiveData, observer1)
        lifecycleOwner.observe(singleLiveData, observer2)
        lifecycleOwner.observe(singleLiveData, observer3)
        singleLiveData.postValue(observerPostValue)

        verify(observer1).invoke(observer1Captor.capture())
        verify(observer2, never()).invoke(anyString())
        verify(observer3, never()).invoke(anyString())

        assertEquals(observerPostValue, observer1Captor.lastValue)
    }

    @Test
    fun observingSingleLiveData_WithoutPostValue_ShouldNotTrigger() {
        val singleLiveData = SingleLiveData<Int>()
        val observer = mock<(Int) -> Unit>()

        lifecycleOwner.observe(singleLiveData, observer)

        verify(observer, never()).invoke(Mockito.anyInt())
    }
}
