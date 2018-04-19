package news.design.graduation.com.news.fragment;


import android.net.Uri;
import android.support.v4.app.Fragment;

import java.util.HashMap;

/**
 * Created by carter on 2018/3/20
 */

public class FragmentFactory implements TopNewsFragmet.OnFragmentInteractionListener,SocietyNewsFragment
        .OnFragmentInteractionListener,InlandNewsFragment.OnFragmentInteractionListener,
        InternationalNewsFragment.OnFragmentInteractionListener{

    public static HashMap<Integer, android.support.v4.app.Fragment> sHashMap = new HashMap<>();

    public static Fragment createFragment(int position) {
        Fragment fragment = sHashMap.get(position);
        if (fragment == null) {
            switch (position) {
                case 0:
                    fragment = new TopNewsFragmet();
                    break;
                case 1:
                    fragment = new SocietyNewsFragment();
                    break;
                case 2:
                    fragment = new InlandNewsFragment();
                    break;
                case 3:
                    fragment = new InternationalNewsFragment();
                    break;
                default:
                    break;
            }
        }
        return fragment;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
