package custom.path;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by linchenpeng on 2017/7/4.
 */

class ShareItem {
    public String title;
    public int titleColor= Color.BLACK;
    public int bgColor=Color.WHITE;
    public Bitmap icon;

    public ShareItem(String title){
        this.title=title;
    }
    public ShareItem(String title,Bitmap icon){
        this.title=title;
        this.icon=icon;
    }

    public ShareItem(String title,int bgColor){
        this.title=title;
        this.titleColor=titleColor;
        this.bgColor=bgColor;
    }
    public ShareItem(String title,int titleColor,int bgColor){
        this.title=title;
        this.titleColor=titleColor;
        this.bgColor=bgColor;
    }

    public ShareItem(String title,int bgColor,Bitmap icon){
        this.title=title;
        this.bgColor=bgColor;
        this.icon=icon;
    }

    public ShareItem(String title,int titleColor,int bgColor,Bitmap icon){
        this.title=title;
        this.titleColor=titleColor;
        this.bgColor=bgColor;
        this.icon=icon;
    }

    @Override
    public String toString() {
        return "ShareItem{"
                + "title='" + title + '\''
                + ", titleColor=" + titleColor
                + ", bgColor=" + bgColor
                + ", icon=" + icon
                + '}';
    }
}
