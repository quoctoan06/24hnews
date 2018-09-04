package com.example.toan.interfaces;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Toan on 09-Feb-18.
 */

public interface AdapterListener {
    public void onItemClickListerner(Object o, int position, RecyclerView.ViewHolder holder);
}
