package org.z1god.selap.OnBoarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import org.z1god.selap.R

class ScreenAdapter(
    val context : Context,
    val screens : ArrayList<ScreenModel>
) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_onboarding, container, false);

        val image : ImageView = view.findViewById(R.id.image_onboarding)
        val title : TextView = view.findViewById(R.id.title_onboarding)
        val desc : TextView = view.findViewById(R.id.content_onboarding)

        image.setImageResource(screens.get(position).image)
        title.text = screens.get(position).title
        desc.text = screens.get(position).desc

        container.addView(view)
        return view
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return screens.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View);
    }
}