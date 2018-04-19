package news.design.graduation.com.news.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import news.design.graduation.com.news.R;
import news.design.graduation.com.news.adapter.NewsListAdapter;
import news.design.graduation.com.news.adapter.SearchAdapter;
import news.design.graduation.com.news.newsInfo.NewsInfo;
import news.design.graduation.com.news.util.SearchNewsUtil;
import news.design.graduation.com.news.util.UiUtil;

public class SearchNewsActivity extends AppCompatActivity {

    private SearchAdapter mSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_news);
        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("查找音乐");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView re = findViewById(R.id.rv_search);
        re.setLayoutManager(new LinearLayoutManager(UiUtil.getContext()));
        mSearchAdapter = new SearchAdapter();
        mSearchAdapter.setOnClickNewsListener(new NewsListAdapter.OnClickNewsListener() {
            @Override
            public void onClickNews(NewsInfo.ResultBean.DataBean dataBean) {
                Intent intent = new Intent(UiUtil.getContext(),NewsDetailActivity.class);
                String url = dataBean.getUrl();
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
        re.setAdapter(mSearchAdapter);
    }

    private static final String TAG = "SearchNewsActivity";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = ((SearchView) searchItem.getActionView());
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("输入要查找的新闻");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: ");
                onQueryTextChange(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange: ");

                List<NewsInfo.ResultBean.DataBean> dataBeans = SearchNewsUtil.SearchNews(newText);

                for (int i = 0; i < dataBeans.size(); i++) {
                    Log.d(TAG, "onQueryTextChange: "+dataBeans.get(i).getTitle());
                }
                mSearchAdapter.updateSearchResults(dataBeans);
                mSearchAdapter.notifyDataSetChanged();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStop() {
        finish();
        super.onStop();
    }
}
