package com.example.glide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;

public class MainActivity extends AppCompatActivity {

    // 图片的网络地址
    private final static String URL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607680750379&di=dea930e76bd9d9056c5ad2437ac03dbc&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F30%2F29%2F01300000201438121627296084016.jpg";
    private final static String URL2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1607680979294&di=aded2f163292585ebb225efe0409b9e5&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F52%2F62%2F31300542679117141195629117826.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.image); // 获取ImageView控件而已

        // TODO　常规方式
        Glide.with(this).load(URL).into(imageView);


        // TODO 拆分分析源码

        /**
         * 源码分析一：
         */
        RequestManager requestManager = Glide.with(this.getApplicationContext()); // 没法及时释放，就奔溃了

        new Thread() {
            @Override
            public void run() {
                super.run();
                // 在子线程中Glide生命周期为Application
                // Glide.with(getApplicationContext());
            }
        };

        /**
         * 源码分析二：
         * .append(GlideUrl.class, InputStream.class, new HttpGlideUrlLoader.Factory()) ==  String URL
         * .append(Drawable.class, Drawable.class, UnitModelLoader.Factory.<Drawable>getInstance()) == Drawable
         *
         * 参数一:我们的类型      参数二：参数三的对象构建出 InputStream
         * .append(byte[].class, InputStream.class, new ByteArrayLoader.StreamFactory()) ==  new byte[]
         */
        RequestBuilder requestBuilder = requestManager.load(URL); // URL === StringBitmapDecoder

        /**
         * 源码分析三：
         */
        requestBuilder.into(imageView);
    }
}