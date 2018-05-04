package news.design.graduation.com.news.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import news.design.graduation.com.news.R;
import news.design.graduation.com.news.activity.NewsDetailActivity;
import news.design.graduation.com.news.adapter.NewsListAdapter;
import news.design.graduation.com.news.http.NewsGetter;
import news.design.graduation.com.news.listener.OnGetTheNewsFinishListener;
import news.design.graduation.com.news.newsInfo.NewsInfo;
import news.design.graduation.com.news.util.DbOpenHelper;
import news.design.graduation.com.news.util.ShareUtil;
import news.design.graduation.com.news.util.UiUtil;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TopNewsFragmet.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopNewsFragmet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopNewsFragmet extends Fragment {
    private static final String TAG = "TopNewsFragmet";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private NewsListAdapter mNewsListAdapter;

    public TopNewsFragmet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopNewsFragmet.
     */
    // TODO: Rename and change types and number of parameters
    public static TopNewsFragmet newInstance(String param1, String param2) {
        TopNewsFragmet fragment = new TopNewsFragmet();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        DbOpenHelper dbOpenHelper = new DbOpenHelper(getActivity(),"news");
        SQLiteDatabase readableDatabase = dbOpenHelper.getWritableDatabase();
        Cursor news = readableDatabase.query("news", new String[]{"title"}, null, null, null, null, null);
        while (news.moveToNext()){
            Log.d(TAG, "onCreate: "+news.getString(0));
        }
        news.close();
        readableDatabase.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        //初始化frament
        RecyclerView rvNews = view.findViewById(R.id.rv_list_news);
        rvNews.setLayoutManager(new LinearLayoutManager(UiUtil.getContext()));
        NewsGetter newsGetter = new NewsGetter();
        String type = "top";
        newsGetter.updateContent(new OnGetTheNewsFinishListener() {
            @Override
            public void finish(List<NewsInfo.ResultBean.DataBean> data) {
//                Log.d(TAG, "finish: "+data.get(0).getTitle());
                mNewsListAdapter = new NewsListAdapter();
                mNewsListAdapter.updateNews(data);
                mNewsListAdapter.notifyDataSetChanged();
                rvNews.setAdapter(mNewsListAdapter);
                mNewsListAdapter.setOnClickNewsListener(new NewsListAdapter.OnClickNewsListener() {
                    @Override
                    public void onClickNews(NewsInfo.ResultBean.DataBean dataBean) {
                        Intent intent = new Intent(UiUtil.getContext(),NewsDetailActivity.class);
                        String url = dataBean.getUrl();
                        intent.putExtra("url",url);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongClickListener(NewsInfo.ResultBean.DataBean dataBean) {

                        ShareUtil.getInstance().showShare(dataBean.getTitle(),dataBean.getUrl());
                    }
                });
            }
        },type,getActivity());


        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
