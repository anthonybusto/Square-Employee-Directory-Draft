package com.abusto.square.employees.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abusto.square.base_arch.BaseView
import com.abusto.square.employees.R
import com.abusto.square.employees.RecyclerViewActivity
import com.abusto.square.employees.extensions.hide
import com.abusto.square.employees.extensions.show
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_employee_directory.*
import kotlinx.android.synthetic.main.layout_recycler_view.*
import org.koin.android.ext.android.getKoin


class EmployeeDirectoryFragment: Fragment(R.layout.fragment_employee_directory), BaseView<EmployeeDirectoryIntent, EmployeeDirectoryViewState> {

    private val viewModel: EmployeeDirectoryViewModel by viewModels { EmployeeDirectoryViewModelactory(getKoin()) }

    private val mainActivity: RecyclerViewActivity? get() = activity as? RecyclerViewActivity

    private val adapter
       get() = recycler_view.adapter as? GroupAdapter ?: GroupAdapter<GroupieViewHolder>()

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        /**
         * After our first launch, according to the spec, we do not want to
         * refresh the data. There should be a date/time stamp in our UI that
         * proves that on rotation we are not re-fetching any data. Using the
         * debugger also shows that we never hit the network to fetch data
         * to display after the first launch. We are handling the caching
         * and persistence of state on the RxJava level. When rotating or
         * config changing, the previous state is cached in the stream inside
         * the [BaseViewModel]. We are also then using a local database
         * to persist data across multiple launches as well.
         */

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        /**
         * We are using ViewModels and RxJava operators and streams
         * to handle saving the state to persist rotation. This logic should
         * persist our immutable state across rotation changes and ONLY hit the
         * network on the very FIRST launch. The ViewState is being cached both
         * in our RxStream and in our local caches that Rx is using. ALl the
         * specifications should be covered and addressed. State is being persisted
         *
         * Without using the approach, we can also use an approach where we
         * can pass data to the bundle inside this [onSaveInstanceState] function
         * and then read in that data inside either [onViewStateRestored] and
         * even [onViewCreated].
         *
         * The savedInstanceState inside [onViewStateRestored] and even [onViewCreated]
         * is always NULL on the very first launch. After the first launch, if
         * the configuration changes by the user rotating to landscape mode or
         * changing his default device text size in settings, the fragment will
         * get destroyed and recreated. When it is recreated, the savedInstanceState
         * bundle will NO longer be NULL and if you want to persist some data or
         * flags across rotations, you can override this function to do so.
         * Anything passed to [outState] here will be persisted and can be
         * read inside the [onViewStateRestored] and even [onViewCreated]
         * functions.
         */
    }


    /**
     * After our first launch, according to the spec, we do not want to
     * refresh the data. There should be a date/time stamp in our UI that
     * proves that on rotation we are not re-fetching any data. Using the
     * debugger also shows that we never hit the network to fetch data
     * to display after the first launch. We are handling the caching
     * and persistence of state on the RxJava level. When rotating or
     * config changing, the previous state is cached in the stream inside
     * the [BaseViewModel]. We are also then using a local database
     * to persist data across multiple launches as well.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.adapter = this.adapter
        recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mainActivity?.let { viewModel.states().observe(it, Observer(::render)) }
        viewModel.listenForIntents(intents())
    }

    /**
     * This is the only EXIT point out of the [BaseView] and into the [BaseViewModel].
     * The [BaseViewModel] is subscribe to these events and will be listening for
     * all interactions between the user and the [BaseView]
     */
    override fun intents(): Observable<EmployeeDirectoryIntent> = employeeClicks()

    private fun employeeClicks(): Observable<EmployeeDirectoryIntent> =
        Observable.just(EmployeeDirectoryIntent.EmployeeClicked(""))

    /**
     * This is the only entry point into this [BaseView]. The @parameter for
     * [state] is immutable. This view only knows to do 1 main thing,
     * "Render what I'm told to render." It does not 'decide' to do anything,
     * it just renders things to the user and then communicates what the user
     * interacts with back to the [BaseViewModel]
     */
    override fun render(state: EmployeeDirectoryViewState) {
        //TODO: We should add an error state as well and render 1 of those 3 states: Loading, Content, or Error.
        if (state.isLoading) {
            layout_content.hide()
            layout_loading.show()
        } else {
            layout_content.show()
            layout_loading.hide()
            renderContentState(state)
        }
    }

    private fun renderContentState(state: EmployeeDirectoryViewState) {
        adapter.update(state.toGroups(), true)
    }

    companion object {
        fun newInstance() = EmployeeDirectoryFragment()
    }
}
