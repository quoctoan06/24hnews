package com.example.toan.news;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toan.adapter.MenuAdapter;
import com.example.toan.adapter.PostAdapter;
import com.example.toan.api.NewsApi;
import com.example.toan.entity.MenuEntity;
import com.example.toan.entity.PostEntity;
import com.example.toan.interfaces.AdapterListener;
import com.example.toan.interfaces.HttpCallback;
import com.example.toan.util.LogUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    private ImageView imgMenu;

    private RecyclerView rvMenu;

    private RecyclerView rvPost;

    private MenuAdapter menuAdapter;

    private PostAdapter postAdapter;

    private List<MenuEntity> menuEntities = new ArrayList<>();

    private Context context = this;     //ngữ cảnh là từ trang main này?

    private List<PostEntity> postEntities = new ArrayList<>();

    private int categoryId = 1;     //giá trị mặc định bằng 1 chủ đề Thời sự

    private TextView tvTitle;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MenuEntity menuThoiSu = new MenuEntity();
        menuThoiSu.setId(1);
        menuThoiSu.setTitle("Thời sự");
        menuThoiSu.setSelected(true);

        MenuEntity menuTheThao = new MenuEntity();
        menuTheThao.setId(2);
        menuTheThao.setTitle("Thể thao");

        MenuEntity menuKinhTe = new MenuEntity();
        menuKinhTe.setId(3);
        menuKinhTe.setTitle("Kinh tế");

        MenuEntity menuChinhTri = new MenuEntity();
        menuChinhTri.setId(4);
        menuChinhTri.setTitle("Chính trị");

        menuEntities.add(menuThoiSu);
        menuEntities.add(menuTheThao);
        menuEntities.add(menuKinhTe);
        menuEntities.add(menuChinhTri);


        imgMenu = (ImageView)findViewById(R.id.img_main_menu);   //findViewById return type View

        tvTitle = (TextView)findViewById(R.id.tv_title_main_menu);
        tvTitle.setText(menuThoiSu.getTitle());     //mặc định đầu tiên vào title là Thời sự

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        //khi kéo xuống thì máy tự động refresh để update những tin mới nhất
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postEntities.clear();
                postAdapter.notifyDataSetChanged();
                getListPost();
            }
        });

        // configure the SlidingMenu
        final SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen._12sdp);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen._60sdp);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.layout_menu);

        imgMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                menu.toggle();
            }
        });

        menuAdapter = new MenuAdapter(menuEntities, new AdapterListener() {
            @Override
            public void onItemClickListerner(Object o, int position, RecyclerView.ViewHolder holder) {
                //khi click vào 1 item thì đổi màu nền của tất cả các item thành trắng
                for(int i = 0; i < menuEntities.size(); ++i) {
                    MenuEntity menuEntity = menuEntities.get(i);
                    menuEntity.setSelected(false);
                }

                MenuEntity menuEntity = (MenuEntity) o;
                categoryId = menuEntity.getId();    //lấy id chủ đề đã được click
                tvTitle.setText(menuEntity.getTitle());     //đổi title thành tên chủ đề đã được click
                menuEntity.setSelected(true);       //đổi màu nền của item đã click
                menuAdapter.notifyDataSetChanged();     //thông báo dữ liệu đã thay đổi để thay đổi màu nền

                menu.toggle();  //đóng menu

                postEntities.clear();   //xoá các bài post của chủ đề cũ trong mảng
                postAdapter.notifyDataSetChanged();     //update lại giao diện sau khi xoá mảng
                getListPost();  //load các bài viết của chủ đề mới được chọn, get lại giao diện của category đó
            }
        });

        //RecycleView Menu
        rvMenu = (RecyclerView)menu.findViewById(R.id.rv_layout_menu);
        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        rvMenu.setItemAnimator(new DefaultItemAnimator());
        rvMenu.setAdapter(menuAdapter);

        //RecycleView Post
        rvPost = (RecyclerView) findViewById(R.id.rv_post);
        rvPost.setLayoutManager(new LinearLayoutManager(this));
        rvPost.setItemAnimator(new DefaultItemAnimator());

        postAdapter = new PostAdapter(postEntities, new AdapterListener() {
            @Override
            public void onItemClickListerner(Object o, int position, RecyclerView.ViewHolder holder) {
                if(position < postEntities.size()) {    //giả sử đầu tiên có size = 3, khi đó nếu click vào 3 bài post thì position=0,1,2
                                                        //còn nếu position >= size tức là click vào nút "Xem thêm" rồi
                    //click lên bài post
                    PostEntity postEntity = (PostEntity)o;  //lấy đối tượng được bắn ra khi click vào
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("post", postEntity);    //bỏ vào intent để truyền dữ liệu sang DetailActivity
                    startActivity(intent);  //chuyển đến màn hình trang DetailActivity
                }
                else {
                    //click lên nút load_more
                    getListPost();
                }
            }
        });

        rvPost.setAdapter(postAdapter);

        getListPost();  //đầu tiên các bài viết chủ đề Thời sự được load
    }

    private void getListPost() {
        NewsApi.getListPost(context, categoryId, 3, postEntities.size(), new HttpCallback() {   //postEntities.size() = {0,3,6,9,...}
            @Override
            public void onSuccess(final String s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);    //lúc kéo xuống nó là true(có biểu tượng vòng tròn đang quay)
                                                                //khi success biểu tượng đó sẽ mất(false) và các bài viết mới được hiển thị
                        try {
                            JSONArray jsonArray = new JSONArray(s);     //parse chuỗi string truyền vào thành 1 mảng các đối tượng json
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);     //bắt đầu lấy từng đối tượng json ra
                                PostEntity postEntity = new PostEntity(jsonObject);     //lưu vào 1 đối tượng PostEntity
                                postEntities.add(postEntity);
                                LogUtil.d("postEntity", postEntity.toString());     //log ra console để xem chạy được chưa
                            }

                            postAdapter.notifyDataSetChanged();     //update giao diện sau khi thêm các bài post vào mảng
                        } catch (JSONException e) {
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
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(context, "Kết nối mạng có vấn đề, yêu cầu bạn kiểm tra lại !!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
