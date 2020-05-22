package com.example.board.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.board.R;
import com.example.board.util.Comment_News;

import java.util.List;

import static android.view.View.*;

public class Comment_Adapter  extends RecyclerView.Adapter<Comment_Adapter.CommentViewHolder> {

    private Context mCtx;
    private List<Comment_News> productList;
    int count =0;

    public Comment_Adapter(Context mCtx, List<Comment_News> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public Comment_Adapter.CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.comment_list, null);
        return new Comment_Adapter.CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final Comment_Adapter.CommentViewHolder holder, int position) {
        Comment_News product = productList.get(position);
//        Glide.with(mCtx)
//                .load(product.getPerson_image())
//                .centerCrop()
//                .placeholder(R.drawable.person)
//                .into(holder.business_image);

        holder.textViewTitle.setText(product.getComment_name());
        holder.textViewShortDesc.setText(product.getComment_desc());
        holder.time_published.setText(product.getComment_posted_date());


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc,time_published;
        ImageView business_image,liked_seed;

        CommentViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.person_name);
            textViewShortDesc = itemView.findViewById(R.id.comment_desc);
            time_published = itemView.findViewById(R.id.comment_time);
            business_image = itemView.findViewById(R.id.person_image);
           // liked_seed = itemView.findViewById(R.id.most_likes);
        }
    }
}
