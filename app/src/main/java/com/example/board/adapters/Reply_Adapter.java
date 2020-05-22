package com.example.board.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.board.R;
import com.example.board.util.Reply_News;

import java.util.List;

public class Reply_Adapter extends RecyclerView.Adapter<Reply_Adapter.ReplyViewHolder> {

    private Context mCtx;
    private List<Reply_News> productList;

    public Reply_Adapter(Context mCtx, List<Reply_News> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ReplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.reply__comment, null);
        return new ReplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReplyViewHolder holder, int position) {
        Reply_News product = productList.get(position);
        Glide.with(mCtx)
                .load(product.getPostImage())
                .into(holder.reply_image_view);
        holder.title_post.setText(product.getPost_title());
        holder.textViewShortDesc.setText(product.getPost_content());
        holder.news_category.setText(product.getCategory());
        holder.time_published.setText(product.getPostTime());
        holder.textViewShortDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ReplyViewHolder extends RecyclerView.ViewHolder {

        TextView title_post, textViewShortDesc, news_category,time_published,category,write_a_comment;
        ImageView reply_image_view;

        ReplyViewHolder(View itemView) {
            super(itemView);

            title_post = itemView.findViewById(R.id.news_title);
            textViewShortDesc = itemView.findViewById(R.id.news_cast);
           news_category = itemView.findViewById(R.id.catergory_news);
            time_published = itemView.findViewById(R.id.date_reply);
            reply_image_view = itemView.findViewById(R.id.reply_image);
            write_a_comment = itemView.findViewById(R.id.write_a_comment);



        }
    }
}