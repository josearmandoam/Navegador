package com.example.jose.navegador;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    WebView browser;

    AutoCompleteTextView aut;
    Button bBorrado;
    Base bd;
    String tex="";
    LinearLayout ly;
    int posFav=0;
    int posHist=0;
    Historial historial;
    TextView h,h2,h3,h4,h5,h6,h7,h8,h9,h10,h11,h12,h13,h14,h15,h16,h17,h18,h19,fav,fav2,fav3,fav4,fav5,fav6;
    ImageView logo;
    Vector<String> sugerencias;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bd = new Base(this);

        bBorrado=(Button)findViewById(R.id.button9);
        h=(TextView)findViewById(R.id.h);
        h2=(TextView)findViewById(R.id.h2);
        h3=(TextView)findViewById(R.id.h3);
        h4=(TextView)findViewById(R.id.h4);
        h5=(TextView)findViewById(R.id.h5);
        h6=(TextView)findViewById(R.id.h6);
        h7=(TextView)findViewById(R.id.h7);
        h8=(TextView)findViewById(R.id.h8);
        h9=(TextView)findViewById(R.id.h9);
        h10=(TextView)findViewById(R.id.h10);
        h11=(TextView)findViewById(R.id.h11);
        h12=(TextView)findViewById(R.id.h12);
        h13=(TextView)findViewById(R.id.h13);
        h14=(TextView)findViewById(R.id.h14);
        h15=(TextView)findViewById(R.id.h15);
        h16=(TextView)findViewById(R.id.h16);
        h17=(TextView)findViewById(R.id.h17);
        h18=(TextView)findViewById(R.id.h18);
        h19=(TextView)findViewById(R.id.h19);

        fav=(TextView)findViewById(R.id.fav);
        fav2=(TextView)findViewById(R.id.fav2);
        fav3=(TextView)findViewById(R.id.fav3);
        fav4=(TextView)findViewById(R.id.fav4);
        fav5=(TextView)findViewById(R.id.fav5);
        fav6=(TextView)findViewById(R.id.fav6);

        logo = (ImageView) findViewById(R.id.imageView4);
        aut = (AutoCompleteTextView) findViewById(R.id.editText);

        aut.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mostrarTeclado();
                }
            }

        });

        browser = (WebView) findViewById(R.id.browser);
        browser.setWebViewClient(new WebViewClient() {
            public void onPageStarted(WebView v, String url, Bitmap e) {
                ly.setVisibility(View.GONE);
                logo.setVisibility(View.GONE);
                browser.setVisibility(View.VISIBLE);
                aut.setText(url);
                String hora="212";//no he podido averiguar como obtener la hora del sistema, he encontrado algunos pero me piden un API superior
                historial=new Historial(tex,url,hora);
                tex=null;
                    int n = bd.añadirPagina(historial);
                    //Toast.makeText(getBaseContext(), "Se ha añadido " + n + " paginas", Toast.LENGTH_SHORT).show();
                obtenerSugerencias();

            }
        });
        WebSettings webSettings = browser.getSettings();
        webSettings.setJavaScriptEnabled(true);

        aut.setFocusableInTouchMode(true);
        aut.setFocusable(true);
        aut.requestFocus();
        pulsadoEnter();

        ly = (LinearLayout) findViewById(R.id.ventana);
        ly.setVisibility(View.GONE);

    }

    @Override
    protected void onSaveInstanceState(Bundle guardarStado) {
        super.onSaveInstanceState(guardarStado);
        String t = aut.getText().toString();
        guardarStado.putString("text", t);
    }

    @Override
    public void onRestoreInstanceState(Bundle recuperarEstado) {
        super.onRestoreInstanceState(recuperarEstado);
        String t = recuperarEstado.getString("text");
        aut.setText(t);
        aut.setSelection(aut.length());
    }

    private void reiniciar() {
        Intent i=new Intent(this,MainActivity.class);
        this.startActivity(i);
    }

    private void ocultarTeclado() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        aut.setVisibility(View.GONE);
    }

    public void mostrarTeclado() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        aut.requestFocus();
        inputMethodManager.showSoftInput(aut, 0);
    }

    private void pulsadoEnter() {
        aut.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                browser.setVisibility(View.VISIBLE);
                ly.setVisibility(View.GONE);
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    tex=aut.getText().toString();
                    compruebaURL();
                    return true;
                }
                return false;
            }
        });
    }

    private void compruebaURL() {
        String url = aut.getText().toString();
        int n = url.indexOf(':');
        int n2 = url.indexOf('.');
        if (n2 < 0 && n < 0) {
            browser.loadUrl("https://www.google.es/search?q=" + url);
            ocultarTeclado();
        } else {
            if (n > 0 && n > 0) {
                browser.loadUrl(url);
                ocultarTeclado();
            } else {
                if (n < 0 && n2 > 0) {
                    browser.loadUrl("http://"+url);
                    ocultarTeclado();
                }
            }
        }
    }

    public void home(View view) {
        ly.setVisibility(View.GONE);
        bBorrado.setEnabled(false);
        reiniciar();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {//para retrocer atras con el boton del telefono
        if ((keyCode == KeyEvent.KEYCODE_BACK) && browser.canGoBack()) {
            browser.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void next(View v) {
        if (browser.canGoForward()) {
            browser.setVisibility(View.VISIBLE);
            ly.setVisibility(View.GONE);
            browser.goForward();
        }
    }

    public void previus(View v) {
        if (browser.canGoBack()) {
            if(ly.getVisibility()==View.VISIBLE){
                ly.setVisibility(View.GONE);
                bBorrado.setEnabled(false);
                browser.setVisibility(View.VISIBLE);
            }else {
                browser.setVisibility(View.VISIBLE);
                ly.setVisibility(View.GONE);
                browser.goBack();
            }
        }else{
            if (!browser.canGoBack() && ly.getVisibility()==View.VISIBLE){
                bBorrado.setEnabled(false);
                browser.setVisibility(View.VISIBLE);
                ly.setVisibility(View.GONE);
            }
        }
    }

    public void refresh(View v) {
        browser.reload();
        browser.setVisibility(View.VISIBLE);
        ly.setVisibility(View.GONE);
        aut.setText(browser.getUrl());
    }

    public void search(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(aut.getWindowToken(), 0);
        browser.loadUrl(aut.getText().toString());
    }

    public void buscar(View v) {
        browser.setVisibility(View.VISIBLE);
        ly.setVisibility(View.GONE);
        aut.setFocusableInTouchMode(true);
        aut.setVisibility(View.VISIBLE);
        aut.setText("");
        aut.requestFocus();

    }

    public void exit(View v) {
        System.exit(0);
    }

    public void historial(View v) {
            aut.setVisibility(View.GONE);
            browser.setVisibility(View.GONE);
            ly.setVisibility(View.VISIBLE);
            actualizarHistorial();
            bBorrado.setEnabled(true);
    }
    private  void actualizarHistorial(){
        try {
            List<Historial> lista = bd.obtenerHistorial();
                    h.setText( lista.get(0+posHist).getUrl());
                    h2.setText(lista.get(1+posHist).getUrl());
                    h3.setText(lista.get(2+posHist).getUrl() );
                    h4.setText(lista.get(3+posHist).getUrl() );
                    h5.setText( lista.get(4+posHist).getUrl() );
                    h6.setText(lista.get(5+posHist).getUrl() );
                    h7.setText( lista.get(6+posHist).getUrl() );
                    h8.setText(lista.get(7+posHist).getUrl() );
                    h9.setText(lista.get(8+posHist).getUrl() );
                    h10.setText( lista.get(9+posHist).getUrl() );
                    h11.setText( lista.get(10+posHist).getUrl() );
                    h12.setText( lista.get(11+posHist).getUrl() );
                    h13.setText(lista.get(12+posHist).getUrl());
                    h14.setText( lista.get(13+posHist).getUrl() );
                    h15.setText( lista.get(14+posHist).getUrl() );
                    h16.setText( lista.get(15+posHist).getUrl() );
                    h17.setText( lista.get(16+posHist).getUrl() );
                    h18.setText( lista.get(17+posHist).getUrl());
                    h19.setText( lista.get(18+posHist).getUrl());
            if(!h19.getText().toString().isEmpty()) {
                posHist++;
            }
        }catch(Exception e){}
    }
    public void borrarHistorial(View v){

        int n=bd.borrarHistorial();
        posHist=0;
        Toast.makeText(this,R.string.borrarHistorial,Toast.LENGTH_SHORT).show();
        actualizarHistorial();
        home(v);
    }
    public void favoritos(View v){
        switch (posFav){
            case 0:
                fav.setText(browser.getUrl());
                posFav++;
                Toast.makeText(this,R.string.favorito, Toast.LENGTH_SHORT).show();
                break;
            case 1:
                fav2.setText(browser.getUrl());
                posFav++;
                Toast.makeText(this,R.string.favorito, Toast.LENGTH_SHORT).show();
                break;
            case 2:
                fav3.setText(browser.getUrl());
                posFav++;
                Toast.makeText(this,R.string.favorito, Toast.LENGTH_SHORT).show();
                break;
            case 3:
                fav4.setText(browser.getUrl());
                posFav++;
                Toast.makeText(this,R.string.favorito, Toast.LENGTH_SHORT).show();
                break;
            case 4:
                fav5.setText(browser.getUrl());
                posFav++;
                Toast.makeText(this,R.string.favorito, Toast.LENGTH_SHORT).show();
                break;
            case 5:
                fav6.setText(browser.getUrl());
                posFav=0;
                Toast.makeText(this,R.string.favorito, Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public void cargarLink(View v){
        switch (v.getId()){
            case R.id.h:
                browser.loadUrl(h.getText().toString());
                break;
            case R.id.h2:
                browser.loadUrl(h2.getText().toString());
                break;
            case R.id.h3:
                browser.loadUrl(h3.getText().toString());
                break;
            case R.id.h4:
                browser.loadUrl(h4.getText().toString());
                break;
            case R.id.h5:
                browser.loadUrl(h5.getText().toString());
                break;
            case R.id.h6:
                browser.loadUrl(h6.getText().toString());
                break;
            case R.id.h7:
                browser.loadUrl(h7.getText().toString());
                break;
            case R.id.h8:
                browser.loadUrl(h8.getText().toString());
                break;
            case R.id.h9:
                browser.loadUrl(h9.getText().toString());
                break;
            case R.id.h10:
                browser.loadUrl(h10.getText().toString());
                break;
            case R.id.h11:
                browser.loadUrl(h11.getText().toString());
                break;
            case R.id.h12:
                browser.loadUrl(h12.getText().toString());
                break;
            case R.id.h13:
                browser.loadUrl(h13.getText().toString());
                break;
            case R.id.h14:
                browser.loadUrl(h15.getText().toString());
                break;
            case R.id.h16:
                browser.loadUrl(h.getText().toString());
                break;
            case R.id.h17:
                browser.loadUrl(h17.getText().toString());
                break;
            case R.id.h18:
                browser.loadUrl(h18.getText().toString());
                break;
            case R.id.h19:
                browser.loadUrl(h19.getText().toString());
                break;
            case R.id.fav:
                browser.loadUrl(fav.getText().toString());
                break;
            case R.id.fav2:
                browser.loadUrl(fav2.getText().toString());
                break;
            case R.id.fav3:
                browser.loadUrl(fav3.getText().toString());
                break;
            case R.id.fav4:
                browser.loadUrl(fav4.getText().toString());
                break;
            case R.id.fav5:
                browser.loadUrl(fav5.getText().toString());
                break;
            case R.id.fav6:
                browser.loadUrl(fav6.getText().toString());
                break;

        }
    }
    public void obtenerSugerencias(){
        List<Historial> lista=null;
        sugerencias=new Vector<String>();
         lista= bd.obtenerHistorial();
        for(int i=0;i<lista.size();i++){
            String texto=lista.get(i).getTexto();
            if(texto!=null){
                sugerencias.add(texto);
               // Toast.makeText(this,texto,Toast.LENGTH_SHORT).show();
            }
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, sugerencias);
        aut.setAdapter(adaptador);

    }

}
