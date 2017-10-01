package me.barta.stayintouch.common.ui

import android.app.Fragment
import android.os.Bundle
import android.view.View

/**
 * Base Fragment implementing MVP pattern
 */
abstract class MVPFragment<V : MVPContract.View, out P : MVPContract.Presenter<V>,
        out C : MVPContract.Component<V, P>> : Fragment(), MVPContract.View {

    protected val presenter: P by lazy { component.presenter() }
    protected val component: C by lazy { createComponent() }

    protected abstract fun createComponent(): C

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this as V)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }
}