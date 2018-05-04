package news.design.graduation.com.news.util;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class ShareUtil {
    private static final ShareUtil ourInstance = new ShareUtil();

    public static ShareUtil getInstance() {
        return ourInstance;
    }

    private ShareUtil() {
    }

    public void showShare(String title,String url) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("我正在看");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(title);
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网使用
        // 启动分享GUI
        oks.show(UiUtil.getContext());
    }
}
