package com.gadium.damdioniso.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gadium.damdioniso.R;

/**
 * Clase que gestiona el splash screen de la aplicación. Este SplashScreen se mostrará sólo la primera vez que se ejecute la aplicación
 */
public class WelcomeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Boolean firstTime;

    private ViewPager welcomeViewPager;
    private LinearLayout welcomeDotsLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] dots;
    private Button prvButton;
    private Button nxtButton;
    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //Usamos SharedPreferences para almacenar una bandera con la que controlar que el splash screen sólo se muestra la primera vez que ejecutamos la app
        sharedPreferences = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        firstTime = sharedPreferences.getBoolean("firstTime",true);

        //Si es la primera ejecución se muestra el splash screen
        if (firstTime){
        welcomeViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        welcomeDotsLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        prvButton = (Button) findViewById(R.id.buttonPrv);
        nxtButton = (Button) findViewById(R.id.buttonNxt);


        sliderAdapter = new SliderAdapter(this);
        welcomeViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        welcomeViewPager.addOnPageChangeListener(viewListener);

        nxtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentPage == dots.length-1){

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    firstTime = false;
                    editor.putBoolean("firstTime",firstTime);
                    editor.apply();

                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    welcomeViewPager.setCurrentItem(currentPage+1);}
            }
        });

        prvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                welcomeViewPager.setCurrentItem(currentPage-1);
            }
        });}

        //Si no es la primera ejecución entonces pasamos directamente a mostrar la lista de vinos
        else{Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();}

    }

    /**
     * Función para gestionar los indicadores de la página del Splash Screen
     * @param position Recibe por parámetro la posición de la página
     */
    public void addDotsIndicator(int position){
        dots = new TextView[3];
        welcomeDotsLayout.removeAllViews();

        for (int i=0; i<dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorWhiteTransparent));
            welcomeDotsLayout.addView(dots[i]);
        }

        if(dots.length>0){
            dots[position].setTextColor(getResources().getColor(R.color.colorAccent));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * Función que gestiona el estado de los botones de Atrás y Siguiente del splash screen
         * @param position Recibe por parámetro la posición de la página
         */
        @Override
        public void onPageSelected(int position) {

            addDotsIndicator(position);
            currentPage = position;

            if(position == 0) {
                nxtButton.setEnabled(true);
                prvButton.setEnabled(false);
                prvButton.setVisibility(View.INVISIBLE);

                nxtButton.setText("Siguiente");
                prvButton.setText("");
            } else if (position == dots.length-1) {
                nxtButton.setEnabled(true);
                prvButton.setEnabled(true);
                prvButton.setVisibility(View.VISIBLE);

                nxtButton.setText("Finalizar");
                prvButton.setText("Atrás");
            } else {
                nxtButton.setEnabled(true);
                prvButton.setEnabled(true);
                prvButton.setVisibility(View.VISIBLE);

                nxtButton.setText("Siguiente");
                prvButton.setText("Atrás");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
