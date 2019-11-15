package com.example.filmasync;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
RecyclerView recyclerView;
EditText editText;
ArrayList<String>Title,Year,Imdbid,Type,Posterlink;
String url="http://www.omdbapi.com/?";
AsyncHttpClient asyncHttpClient;
RequestParams requestParams;
ImageView imageView;


    //http://www.omdbapi.com/?i=tt3896198&apikey=138497c6
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recy);
        editText=findViewById(R.id.edt);
        Title=new ArrayList<>();
        Year=new ArrayList<>();
        Imdbid=new ArrayList<>();
        Type=new ArrayList<>();
        Posterlink=new ArrayList<>();
        imageView=findViewById(R.id.img);
        asyncHttpClient=new AsyncHttpClient();
        requestParams=new RequestParams();

    }

    public void imgg(View view) {

        String edit=editText.getText().toString();

        cleardata();


        requestParams.put("s",edit);
        requestParams.put("apikey","138497c6");
        asyncHttpClient.get(url,requestParams,new JsonHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        JSONObject jsonObject=response;
                        try {
                            JSONArray array=jsonObject.getJSONArray("Search");
                            for(int i=0;i<array.length();i++){
                                JSONObject obj=array.getJSONObject(i);
                                String title=obj.getString("Title");
                                String year=obj.getString("Year");
                                String imdb=obj.getString("imdbID");
                                String type=obj.getString("Type");
                                String poster=obj.getString("Poster");

                                Title.add(title);
                                Year.add(year);
                                Imdbid.add(imdb);
                                Type.add(type);
                                Posterlink.add(poster);


                            }
                        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),2);
                            recyclerView.setLayoutManager(gridLayoutManager);
                            Movieadapter adapt=new Movieadapter(Title,Year,Type,Imdbid,Posterlink);
                            recyclerView.setAdapter(adapt);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );


    }

    private void cleardata() {


        Type.clear();
        Title.clear();
        Imdbid.clear();
        Posterlink.clear();
        Year.clear();


    }



    public  class Movieadapter  extends RecyclerView.Adapter<Movieadapter.myviewholder> {
      private ArrayList<String>Title,Year,Imdb,Type,Poster;

        public Movieadapter(ArrayList<String>Title,ArrayList<String>Year,ArrayList<String>Type,ArrayList<String>Imdb,ArrayList<String>Poster){
        this.Title=Title;
        this.Year=Year;
        this.Imdb=  Imdb;
        this.Type=Type;
        this.Poster=Poster;



//        Year.clear();
//        Imdb.clear();
//        Type.clear();
//        Poster.clear();

        }
        @NonNull
        @Override
        public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.spare,parent,false);

            return new myviewholder(itemview);
        }

        @Override
        public void onBindViewHolder(@NonNull myviewholder holder, final int position) {
            Glide
                    .with(MainActivity.this)/////context
                    .load(Poster.get(position))///image
                    .placeholder(R.drawable.ima)
                    .into(holder.movieposter);

            holder.movietitle.setText(Title.get(position));
            holder.movieyear.setText(Year.get(position));
           // holder.movietype.setText(Type.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(MainActivity.this,Film2.class);
                    startActivity(i);
                    SharedPreferences sp=getApplicationContext().getSharedPreferences("P",MODE_PRIVATE);
                    SharedPreferences.Editor movie=sp.edit();
                    movie.putString("imdb",Imdb.get(position));
                    movie.apply();
                    Toast.makeText(MainActivity.this, Imdb.get(position), Toast.LENGTH_SHORT).show();

                }
            });

        }

        @Override
        public int getItemCount() {
            return Title.size();
        }

        public class myviewholder extends RecyclerView.ViewHolder{
            TextView movietitle,movieyear,movietype;
            ImageView movieposter;

            public myviewholder(@NonNull View itemView) {
                super(itemView);

                movietitle=itemView.findViewById(R.id.txt2);
                movieyear=itemView.findViewById(R.id.txt3);
              //  movietype=itemView.findViewById(R.id.txt4);
                movieposter=itemView.findViewById(R.id.img1);

            }
        }
    }

}
