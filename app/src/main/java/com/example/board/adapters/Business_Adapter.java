package com.example.board.adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Observable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.board.R;
import com.example.board.util.Business_News;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Business_Adapter extends RecyclerView.Adapter<Business_Adapter.BusinessViewHolder> {

    private Context mCtx;
    private List<Business_News> productList;


    public Business_Adapter(Context mCtx, List<Business_News> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public BusinessViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.business_list, null);
        return new BusinessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BusinessViewHolder holder, int position) {
        Business_News product = productList.get(position);
        Glide.with(mCtx)
                .load(product.getPostImage())
                .centerCrop()
                .placeholder(R.drawable.bsu)
                .into(holder.business_image);
        holder.textViewTitle.setText(product.getPost_title());
       holder.textViewShortDesc.setText(product.getPost_content());
       holder.textViewId.setText(product.getbusiness_id());
        holder.time_published.setText(product.getPostTime());
        holder.comment_coun.setText(product.getComment_counted());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });


        @SuppressLint("StaticFieldLeak")
        class insert extends AsyncTask<String, Void, Boolean> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected Boolean doInBackground(String... urls) {
                @SuppressLint("WrongThread") final String textViewId= holder.textViewId.toString();



                try {

                    List<NameValuePair> insert = new ArrayList<NameValuePair>();
                    insert.add(new BasicNameValuePair("clicked_id", textViewId));



                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(
                            "http://softcodes.tech/newsportal/save_clicked_id.php"); // link to connect to database
                    httpPost.setEntity(new UrlEncodedFormEntity(insert));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();

                    return true;


                } catch (IOException e) {
                    e.printStackTrace();

                }


                return false;
            }

            protected void onPostExecute(Boolean result) {


            }

        }
    }





    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class BusinessViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewId,time_published,comment_coun;
        ImageView business_image;

        BusinessViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.post_title);
              textViewShortDesc = itemView.findViewById(R.id.post_content);
             textViewId = itemView.findViewById(R.id.clicked_post);
            time_published = itemView.findViewById(R.id.time_ago);
            business_image = itemView.findViewById(R.id.business_image);
            comment_coun = itemView.findViewById(R.id.comment_count);

        }
    }
}
