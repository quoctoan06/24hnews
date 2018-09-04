package com.example.toan.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toan.entity.MenuEntity;
import com.example.toan.interfaces.AdapterListener;
import com.example.toan.news.R;

import java.util.List;

/**
 * Created by Toan on 09-Feb-18.
 */

public class MenuAdapter extends RecyclerView.Adapter{

    private AdapterListener listener;   //biến bắt sự kiện
    private List<MenuEntity> menuEntities;

    //constructor
    public MenuAdapter(List<MenuEntity> menuEntityList, AdapterListener listener) {
        this.listener = listener;
        this.menuEntities = menuEntityList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //this function returns the layout(style) of data
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());   //LayoutInflater takes as input an XML file and builds the View objects from it
        View v = layoutInflater.inflate(R.layout.item_menu, null);
        return new MenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {     //hàm này làm việc với dữ liệu
        final MenuViewHolder menuViewHolder = (MenuViewHolder) holder;  //lấy View
        final MenuEntity menuEntity = menuEntities.get(position);     //lấy dữ liệu, hàm get(position) sẽ trả về các phần tử ở vị trí 0,1,2... trong mảng

        String title = menuEntity.getTitle();
        menuViewHolder.tvItemMenu.setText(title);   //đưa dữ liệu vào View để hiển thị ra

        //thay đổi màu nền
        if(menuEntity.isSelected()) {
            menuViewHolder.rlItemMenu.setBackgroundResource(R.color.colorPrimary);
        }
        else {
            menuViewHolder.rlItemMenu.setBackgroundResource(android.R.color.white);
        }

        menuViewHolder.rlItemMenu.setOnClickListener(new View.OnClickListener() {   //hàm bắt sự kiện khi click vào 1 item
            @Override
            public void onClick(View view) {
                if(listener != null) {
                    listener.onItemClickListerner(menuEntity, position, menuViewHolder);
                }
            }
        });

    }

    private class MenuViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlItemMenu;
        TextView tvItemMenu;

        public MenuViewHolder(View itemView) {
            super(itemView);
            rlItemMenu = (RelativeLayout) itemView.findViewById(R.id.rl_item_menu);
            tvItemMenu = (TextView) itemView.findViewById(R.id.tv_item_menu);
        }
    }

    @Override
    public int getItemCount() {
        return menuEntities.size();
    }
}
