package com.example.filmasync;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Film2 extends AppCompatActivity {
ImageView imageView;
TextView Title,Year,Rated,Runtime,Genere,Director,Writer,Actor,Plot,Language,Country,Award,Metasource,Imdbrating,Type,Dvd,Boxoffice,Production;
AsyncHttpClient asyncHttpClient;
RequestParams requestParams;
String url="http://www.omdbapi.com/?";
    //http://www.omdbapi.com/?i=tt3896198&apikey=138497c6
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film2);
        imageView=findViewById(R.id.poster);
        Year=findViewById(R.id.year);
        Rated=findViewById(R.id.rated);
        Runtime=findViewById(R.id.runtime);
        Genere=findViewById(R.id.genere);
        Director=findViewById(R.id.director);
        Writer=findViewById(R.id.writer);
        Actor=findViewById(R.id.actor);
        asyncHttpClient=new AsyncHttpClient();
        requestParams=new RequestParams();
        Title=findViewById(R.id.title1);
        Metasource=findViewById(R.id.metascore);
        Imdbrating=findViewById(R.id.imbdrating);
        Type=findViewById(R.id.type);
       Dvd=findViewById(R.id.dvd);
        Boxoffice=findViewById(R.id.boxoffice);
       Production=findViewById(R.id.production);
        Plot=findViewById(R.id.plot);
        Country=findViewById(R.id.country);
        Award=findViewById(R.id.award);
        Language=findViewById(R.id.language);
        SharedPreferences sp=getApplicationContext().getSharedPreferences("P",MODE_PRIVATE);
         String text=sp.getString("imdb",null);
         requestParams.put("i",text);
         requestParams.put("apikey","138497c6");
//        {
//            "Title": "Kushi",
//                "Year": "2000",
//                "Rated": "N/A",
//                "Released": "19 May 2000",
//                "Runtime": "178 min",
//                "Genre": "Romance",
//                "Director": "S.J. Surya",
//                "Writer": "S.J. Surya (dialogue), S.J. Surya (screenplay), S.J. Surya",
//                "Actors": "Jyothika, Joseph Vijay, Mumtaj, Shilpa Shetty Kundra",
//                "Plot": "College friends Jenny and Shiva part ways due to a misunderstanding. Although they are in love and realize they cannot live without each other, their egos prevent them from uniting.",
//                "Language": "Tamil",
//                "Country": "India",
//                "Awards": "2 wins.",
//                "Poster": "https://m.media-amazon.com/images/M/MV5BNGUxNTkxMDAtOGM4Mi00MmJmLWEwYWItZTg2NDllZjM3YTE1XkEyXkFqcGdeQXVyODE0NjUxNzY@._V1_SX300.jpg",
//                "Ratings": [
//            {
//                "Source": "Internet Movie Database",
//                    "Value": "7.4/10"
//            }
//],
//            "Metascore": "N/A",
//                "imdbRating": "7.4",
//                "imdbVotes": "1,908",
//                "imdbID": "tt0361818",
//                "Type": "movie",
//                "DVD": "N/A",
//                "BoxOffice": "N/A",
//                "Production": "N/A",
//                "Website": "N/A",
//                "Response": "True"
//        }
        asyncHttpClient.get(url,requestParams,new JsonHttpResponseHandler()
                {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        JSONObject jsonObject=response;
                        try {
                            String filmtitle=jsonObject.getString("Title");
                            String filmyear=jsonObject.getString("Year");
                            String filmrated=jsonObject.getString("Rated");
                            String filmruntime=jsonObject.getString("Runtime");
                            String filmgenere=jsonObject.getString("Genre");
                            String filmdirector=jsonObject.getString("Director");
                            String filmwriter=jsonObject.getString("Writer");
                            String filmactor=jsonObject.getString("Actors");
                            String filmplot=jsonObject.getString("Plot");
                            String filmlanguage=jsonObject.getString("Language");
                            String filmcountry=jsonObject.getString("Country");
                            String filmawards=jsonObject.getString("Awards");
                            String filmposter=jsonObject.getString("Poster");
                            String filmmetascore=jsonObject.getString("Metascore");
                            String filmimbdrating=jsonObject.getString("imdbRating");
                            String filmtype=jsonObject.getString("Type");
                            String filmdvd=jsonObject.getString("DVD");
                            String filmboxoffice=jsonObject.getString("BoxOffice");
                            String filmproduction=jsonObject.getString("Production");
                          Year.setText(filmyear);
                            Title.setText(filmtitle);
                            Rated.setText("Rating: "+filmrated);
                            Runtime.setText(filmruntime);
                            Genere.setText(filmgenere);
                            Director.setText(filmdirector);
                            Writer.setText(filmwriter);
                            Actor.setText(filmactor);
                            Language.setText(filmlanguage);
                            Country.setText(filmcountry);
                            Award.setText(filmawards);
                            Plot.setText(filmplot);
                            Metasource.setText("Metasource :"+filmmetascore);
                            Imdbrating.setText(filmimbdrating+"/10");
                            Type.setText("Type: "+filmtype);
                            Dvd.setText("DVD: "+filmdvd);
                            Boxoffice.setText("Boxoffice: "+filmboxoffice);
                            Production.setText("Production: "+filmproduction);

                            Glide
                                    .with(getApplicationContext())/////context
                                    .load(filmposter)///image
                                    .placeholder(R.drawable.ima)
                                    .into(imageView);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

    }
}
