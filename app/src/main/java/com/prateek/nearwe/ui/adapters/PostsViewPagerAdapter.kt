
package com.prateek.nearwe.ui.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.prateek.nearwe.R

import com.prateek.nearwe.ui.favourites.FavouritesFragment
import com.prateek.nearwe.ui.posts.PostsFragment
private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)
class PostsViewPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }
    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            PostsFragment.newInstance(0)
        } else {
            FavouritesFragment.newInstance(1)
        }
    }

}