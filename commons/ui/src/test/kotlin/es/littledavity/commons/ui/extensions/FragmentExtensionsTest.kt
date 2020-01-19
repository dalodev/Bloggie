/*
 * Copyright 2020 littledavity
 */
package es.littledavity.commons.ui.extensions

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import es.littledavity.libraries.testutils.TestFragment
import es.littledavity.libraries.testutils.robolectric.TestRobolectric
import org.hamcrest.Matchers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test

class FragmentExtensionsTest : TestRobolectric() {
    private open class TestViewModel(val state: Lifecycle.State? = null) : ViewModel()

    @Test
    fun providedViewModel_ShouldObtainWithStateSaved() {
        val fragmentScenario = launchFragmentInContainer<TestFragment>()
        val expectedState = Lifecycle.State.INITIALIZED
        fragmentScenario.onFragment {
            val createdViewModel = it.viewModel {
                TestViewModel(
                    expectedState
                )
            }
            assertThat(createdViewModel, Matchers.instanceOf(TestViewModel::class.java))
            assertEquals(expectedState, createdViewModel.state)
            val providedViewModel = ViewModelProviders.of(it).get(TestViewModel::class.java)
            assertThat(providedViewModel, Matchers.instanceOf(TestViewModel::class.java))
            assertEquals(expectedState, providedViewModel.state)
        }
    }
    @Test
    fun providedViewModelByIdentifier_ShouldObtainWithStateSaved() {
        val fragmentScenario = launchFragmentInContainer<TestFragment>()
        val expectedState = Lifecycle.State.INITIALIZED
        val identifierViewModel = "TestViewModel"
        fragmentScenario.onFragment {
            val createdViewModel =
                it.viewModel(identifierViewModel) {
                    TestViewModel(
                        expectedState
                    )
                }
            assertThat(createdViewModel, Matchers.instanceOf(TestViewModel::class.java))
            assertEquals(expectedState, createdViewModel.state)
            val providedViewModel =
                ViewModelProviders.of(it).get(identifierViewModel, TestViewModel::class.java)
            assertThat(providedViewModel, Matchers.instanceOf(TestViewModel::class.java))
            assertEquals(expectedState, providedViewModel.state)
        }
    }
    @Test(expected = RuntimeException::class)
    fun providedViewModelWithWrongIdentifier_ShouldNotObtain() {
        val fragmentScenario = launchFragmentInContainer<TestFragment>()
        val identifierViewModel = "TestViewModel"
        fragmentScenario.onFragment {
            it.viewModel(identifierViewModel) { TestViewModel() }
            ViewModelProviders.of(it).get("Wrong Identifier", TestViewModel::class.java)
        }
    }
    @Test(expected = RuntimeException::class)
    fun notProvidedViewModel_ShouldNotObtain() {
        val fragmentScenario = launchFragmentInContainer<TestFragment>()
        fragmentScenario.onFragment {
            ViewModelProviders.of(it).get(TestViewModel::class.java)
        }
    }
}
