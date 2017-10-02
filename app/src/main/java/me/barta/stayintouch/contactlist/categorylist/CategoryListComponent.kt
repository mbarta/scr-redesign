package me.barta.stayintouch.contactlist.categorylist

import dagger.Component
import me.barta.stayintouch.common.di.ActivityScope
import me.barta.stayintouch.common.di.ApplicationComponent

/**
 * Category list Component
 */
@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class))
interface CategoryListComponent : CategoryListContract.Component<CategoryListContract.View, CategoryListPresenter> {
    override fun presenter(): CategoryListPresenter
}