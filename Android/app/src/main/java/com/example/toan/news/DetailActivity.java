package com.example.toan.news;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toan.api.NewsApi;
import com.example.toan.entity.PostEntity;
import com.example.toan.interfaces.HttpCallback;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;

public class DetailActivity extends AppCompatActivity {

    private ImageView imgBack;
    private WebView webView;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //hứng dữ liệu từ MainActivity truyền sang
        Bundle bundle = getIntent().getExtras();
        PostEntity postEntity = (PostEntity)bundle.getSerializable("post"); //hàm này trả về 1 object nên phải ép kiểu

        imgBack = (ImageView)findViewById(R.id.img_back_detail_menu);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();   //xong task ở màn hình này và thoát ra màn hình trước đó
            }
        });

        tvTitle = (TextView)findViewById(R.id.tv_title_detail_menu);
        tvTitle.setText(postEntity.getTitle());

        webView = (WebView)findViewById(R.id.wb_content);
        NewsApi.getPostDetail(this, postEntity.getId(), new HttpCallback() {
            @Override
            public void onSuccess(final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(s);  //parse chuỗi String có cấu trúc json thành 1 đối tượng json
                            PostEntity postEntity1 = new PostEntity(jsonObject);

                            //dữ liệu truyền vào WebView phải là dữ liệu có cấu trúc html
                            String data = "<html><head></head><title></title><style>*{max-width:100%;}</style><body>" + postEntity1.getContent() + "</body></html>";
                            webView.loadData(data, "text/html; charset=utf-8", "utf-8");    //load dữ liệu vào WebView
                        } catch (Exception e) { //catch lỗi nếu như s ko phải là chuỗi hoặc là chuỗi ko có cấu trúc json...
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(Request request, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DetailActivity.this, "Kết nối mạng có vấn đề, yêu cầu bạn kiểm tra lại !!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
