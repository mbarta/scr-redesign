package me.barta.stayintouch.common.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Base Activity implementing MVP pattern
 */
abstract class MVPActivity<V : MVPContract.View, out P : MVPContract.Presenter<V>,
        out C : MVPContract.Component<V, P>> : AppCompatActivity(), MVPContract.View {

    protected val presenter: P by lazy { component.presenter() }
    protected val component: C by lazy { createComponent() }

    protected abstract fun createComponent(): C

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}