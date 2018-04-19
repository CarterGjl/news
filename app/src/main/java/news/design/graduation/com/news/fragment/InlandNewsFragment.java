package news.design.graduation.com.news.fragment;

import android.content.Context;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;

import news.design.graduation.com.news.R;
import news.design.graduation.com.news.adapter.NewsListAdapter;
import news.design.graduation.com.news.http.NewsGetter;
import news.design.graduation.com.news.listener.OnGetTheNewsFinishListener;
import news.design.graduation.com.news.newsInfo.NewsInfo;
import news.design.graduation.com.news.util.UiUtil;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InlandNewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InlandNewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InlandNewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private WebView mWebView;

    public InlandNewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InlandNewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InlandNewsFragment newInstance(String param1, String param2) {
        InlandNewsFragment fragment = new InlandNewsFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inland_news, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_list_inland);
        recyclerView.setLayoutManager(new LinearLayoutManager(UiUtil.getContext()));
        NewsGetter newsGetter = new NewsGetter();
        String type = "guonei";
        newsGetter.updateContent(new OnGetTheNewsFinishListener() {
            @Override
            public void finish(List<NewsInfo.ResultBean.DataBean> data) {
                NewsListAdapter newsListAdapter = new NewsListAdapter();
                newsListAdapter.updateNews(data);
                recyclerView.setAdapter(newsListAdapter);
                newsListAdapter.setOnClickNewsListener(new NewsListAdapter.OnClickNewsListener() {
                    @Override
                    public void onClickNews(NewsInfo.ResultBean.DataBean dataBean) {

                    }
                });
            }
        },type);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
}
