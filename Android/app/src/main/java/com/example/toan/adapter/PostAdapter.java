package com.example.toan.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.toan.entity.PostEntity;
import com.example.toan.interfaces.AdapterListener;
import com.example.toan.news.R;

import java.util.List;

/**
 * Created by Toan on 10-Feb-18.
 */

public class PostAdapter extends RecyclerView.Adapter {

    static final int TYPE_ITEM_NORMAL = 0;
    static final int TYPE_ITEM_LOAD_MORE = 1;

    private List<PostEntity> postEntities;

    private AdapterListener listener;

    public PostAdapter(List<PostEntity> postEntities, AdapterListener listener) {
        this.postEntities = postEntities;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {     //khi ở main gọi postAdapter, hàm tạo ViewHolder
                                                                            //sẽ luôn được gọi trước để gọi các View và hiển thị ra màn hình
        if(viewType == TYPE_ITEM_NORMAL) {   //các bài post chỉ được tạo và hiển thị với số lượng limit(= 3)
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, null);
            PostViewHolder postViewHolder = new PostViewHolder(v);
            return postViewHolder;
        }
        //nút load_more luôn được tạo và hiển thị ngay sau các bài post
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, null);
        LoadMoreViewHolder loadMoreViewHolder = new LoadMoreViewHolder(v);
        return loadMoreViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {        //hàm làm việc với dữ liệu
        //ta xử lí thao tác với dữ liệu (click vào bài post hay nút load_more)
        if(holder instanceof PostViewHolder) {  //nếu ta chỉ click vào các bài post (position=0,1,2... < postEntities.size())
            final PostEntity postEntity = postEntities.get(position);
            final PostViewHolder postViewHolder = (PostViewHolder) holder;

            postViewHolder.tvTitle.setText(postEntity.getTitle());  //đặt tiêu đề
            postViewHolder.tvDesc.setText(postEntity.getDesc());    //đặt mô tả

            Glide.with(postViewHolder.imgThumb.getContext()).load(postEntity.getThumb()).into(postViewHolder.imgThumb); //đặt hình ảnh với thư viện Glide

            postViewHolder.rlPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null) {
                        listener.onItemClickListerner(postEntity, position, postViewHolder);    //khi click vào postViewHolder tại vị trí position
                    }                                                                           //thì trả về 1 đối tượng postEntity
                }
            });
        }
        else {  //còn nếu không, tức ta đã click vào nút load_more
            final LoadMoreViewHolder loadMoreViewHolder = (LoadMoreViewHolder)holder;
            loadMoreViewHolder.btnLoadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        listener.onItemClickListerner(null, position, loadMoreViewHolder);
                    }
                }
            });
        }

    }

    private class PostViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlPost;
        ImageView imgThumb;
        TextView tvTitle;
        TextView tvDesc;

        public PostViewHolder(View itemView) {
            super(itemView);
            imgThumb = (ImageView)itemView.findViewById(R.id.img_thumb);
            tvTitle = (TextView)itemView.findViewById(R.id.tv_title);
            tvDesc = (TextView)itemView.findViewById(R.id.tv_desc);
            rlPost = (RelativeLayout) itemView.findViewById(R.id.rl_post);
        }
    }

    private class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        Button btnLoadMore;

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
            btnLoadMore = (Button)itemView.findViewById(R.id.btn_load_more);
        }
    }

    @Override
    public int getItemViewType(int position) {  //Trong onCreateViewHolder: viewType = getItemViewType();
        if(position < postEntities.size()) {    //0,1...n-1 < n
            return TYPE_ITEM_NORMAL;
        }
        return TYPE_ITEM_LOAD_MORE;
    }

    @Override
    public int getItemCount() {
        return postEntities.size() + 1;     //cộng thêm 1 dành cho TYPE_ITEM_LOAD_MORE
    }

}
