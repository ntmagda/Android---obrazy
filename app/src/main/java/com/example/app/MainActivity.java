package com.example.app;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.graphics.*;
import android.graphics.drawable.*;
import android.content.Intent;

public class MainActivity extends ActionBarActivity {
    Bitmap oryginal;
    ImageView image;

    Bitmap obraz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //<- tu mam odwołanie do activity main a moje ImageView jest we fragment_main.xml nie wime jak to naprawic.


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        setContentView(R.layout.fragment_main);
        image = (ImageView) findViewById(R.id.imageView);
        setContentView(R.layout.activity_main);
        //////////////////////////Probowalam zrobic wczytywanie obrazow z galerii ale nie umiem sobie poradzic z fragment_main i activity_main/////
/*
        image = (ImageView) findViewById(R.id.imageView);
        image.setOnClickListener(new View.OnClickListener() { // po kliknieciu w obrazek wybor z galerii
            @Override
            public void onClick(View view) {

                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery,"Select Image"),1);

            }
        });
*/

    }


    public void Galeria(View view) {

        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery,"Select Image"),1);

    }

    public void onActivityResult(int requestCode , int resultCode, Intent data)
    {

        if(resultCode  == RESULT_OK)
        {
            if(requestCode ==1)
            {
                image.setImageURI(data.getData());
                obraz = ((BitmapDrawable) image.getDrawable()).getBitmap();
                oryginal= ((BitmapDrawable) image.getDrawable()).getBitmap();
                ImageView iv = (ImageView) findViewById(R.id.imageView);
                Bitmap sourceBitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
                iv.setImageBitmap(obraz);
            }
        }
    }

    public void Oryginal(View v) {

        ImageView iv = (ImageView) findViewById(R.id.imageView);
        Bitmap sourceBitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
        iv.setImageBitmap(oryginal);
    }







    public Bitmap WykrywanieSkory1(Bitmap src) {
        // create new bitmap with the same settings as source bitmap
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        int A, R, G, B;
        int pixelColor;
        // image size
        int height = src.getHeight();
        int width = src.getWidth();

        // scan through every pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // get one pixel
                pixelColor = src.getPixel(x, y);
                // saving alpha channel
                A = Color.alpha(pixelColor);
                // inverting byte for each R/G/B channel
                R = Color.red(pixelColor)-Color.green(pixelColor);
                G = Color.red(pixelColor)-Color.green(pixelColor);
                B = Color.red(pixelColor)-Color.green(pixelColor);
                // set newly-inverted pixel to output image
                if(R<0)
                {
                    R=B=G=0;
                }
                else if(R>255)
                {
                    R=B=G=255;
                }


                if(R>30 && G>30 && B>30)
                {
                    R=G=B=255;
                }
                if(R<30 && G<30 && B<30)
                {
                    R=G=B=0;
                }
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        return bmOut;
    }


    public Bitmap Maska(Bitmap src) {
        // create new bitmap with the same settings as source bitmap
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        int A, R, G, B;
        int pixelColor;
        // image size
        int height = src.getHeight();
        int width = src.getWidth();

        // scan through every pixel
        for (int y = 3; y < height-3; y++) {
            for (int x = 3; x < width-3; x++) {
                // get one pixel
                pixelColor = src.getPixel(x, y);
                int p7=Color.red(src.getPixel(x - 1, y));
                // saving alpha channel
                A = Color.alpha(pixelColor);
                // inverting byte for each R/G/B channel
                R = Color.red(pixelColor);
                // set newly-inverted pixel to output image
                if(p7>pixelColor)
                {
                    bmOut.setPixel(x, y, Color.argb(A, 0,0,0));
                }
                bmOut.setPixel(x, y, Color.argb(A, R,R,R));
            }
        }
        return bmOut;
    }



    public Bitmap WykrywanieSkory2(Bitmap src) {
        // create new bitmap with the same settings as source bitmap
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        int A, R, G, B;
        int pixelColor;
        // image size
        int height = src.getHeight();
        int width = src.getWidth();

        // scan through every pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // get one pixel
                pixelColor = src.getPixel(x, y);
                // saving alpha channel
                A = Color.alpha(pixelColor);
                // inverting byte for each R/G/B channel
                R = Color.red(pixelColor);
                G = Color.green(pixelColor);
                B = Color.blue(pixelColor);
                // set newly-inverted pixel to output image
                if(R>95&& G>40 && B>20 && (Math.max(Math.max(R,G),B)-Math.min(Math.min(R,G),B))>15 && (Math.abs(R-G))>15 && R>G && R>B)
                {
                    R=B=G=255;
                }
                else
                {
                    R=B=G=0;
                }
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        return bmOut;
    }

    public Bitmap Negatyw(Bitmap src) {
        // create new bitmap with the same settings as source bitmap
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        int A, R, G, B;
        int pixelColor;
        // image size
        int height = src.getHeight();
        int width = src.getWidth();

        // scan through every pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // get one pixel
                pixelColor = src.getPixel(x, y);
                // saving alpha channel
                A = Color.alpha(pixelColor);
                // inverting byte for each R/G/B channel
                R = 255 - Color.red(pixelColor);
                G = 255 - Color.green(pixelColor);
                B = 255 - Color.blue(pixelColor);
                // set newly-inverted pixel to output image
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        return bmOut;
    }



    public Bitmap Gray(Bitmap src) {
        // create new bitmap with the same settings as source bitmap
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        int Z;
        int pixelColor;
        // image size
        int height = src.getHeight();
        int width = src.getWidth();

        // scan through every pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // get one pixel
                pixelColor = src.getPixel(x, y);
                // saving alpha channel
                Z = (Color.red(pixelColor) +Color.green(pixelColor)+Color.blue(pixelColor))/3;
                bmOut.setPixel(x, y, Color.rgb(Z, Z, Z));
            }
        }

        // return final bitmap
        return bmOut;
    }

    public Bitmap Sepia(Bitmap src, int wsp) {
        // create new bitmap with the same settings as source bitmap
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        // color info
        int R, G, B,A;

        int pixelColor;
        // image size
        int height = src.getHeight();
        int width = src.getWidth();

        // scan through every pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // get one pixel
                pixelColor = src.getPixel(x, y);
                A = Color.alpha(pixelColor);
                R = Color.red(pixelColor)+2*wsp;
                G = Color.green(pixelColor)+wsp;
                B = Color.blue(pixelColor);
                if(R>255)
                {
                    R=255;
                }
                if(B>255)
                {
                    B=255;
                }
                if(G>255)
                {
                    G=255;
                }
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        return bmOut;
    }

    public Bitmap Sobel(Bitmap src) {
        // create new bitmap with the same settings as source bitmap
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        // image size
        int height = src.getHeight();
        int width = src.getWidth();

        // scan through every pixel
        for (int y = 3; y < height-3; y++) {
            for (int x = 3; x < width-3; x++) {
                // get one pixel
                        int p0=Color.red(src.getPixel(x - 1, y - 1));
                        int p1=Color.red(src.getPixel(x, y - 1));
                        int p2=Color.red(src.getPixel(x + 1, y - 1));
                        int p3=Color.red(src.getPixel(x + 1, y));
                        int p4=Color.red(src.getPixel(x + 1, y + 1));
                        int p5=Color.red(src.getPixel(x, y + 1));
                        int p6=Color.red(src.getPixel(x - 1, y + 1));
                        int p7=Color.red(src.getPixel(x - 1, y));
                        int xxg = ((p2+2*p3+p4)-(p0+2*p7+p6));
                        int yyg = ((p6+2*p5+p4)-(p0+2*p1+p2));
                        int g = (int) Math.hypot(xxg,yyg);
                        if(g > 255) g = 255;
                        bmOut.setPixel(x, y, Color.rgb(g, g, g));
            }
        }
         // return final bitmap
     return bmOut;
    }

    public void Help(View v)
    {
        Intent intent = new Intent(this, Help.class);
        startActivity(intent);
    }



    public Bitmap Roberts(Bitmap src) {
        // create new bitmap with the same settings as source bitmap
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        // image size
        int height = src.getHeight();
        int width = src.getWidth();

        // scan through every pixel
        for (int y = 3; y < height-3; y++) {
            for (int x = 3; x < width-3; x++) {
                // get one pixel
                int p3=Color.red(src.getPixel(x + 1, y));
                int p4=Color.red(src.getPixel(x + 1, y + 1));
                int p5=Color.red(src.getPixel(x, y + 1));

                int gg = Math.abs(Color.red(src.getPixel(x,y))- p4)+Math.abs(p3-p5);
                if(gg > 255) gg = 255;
                bmOut.setPixel(x, y, Color.rgb(gg, gg, gg));
            }
        }
        // return final bitmap
        return bmOut;
    }

    public Bitmap HistoKol(Bitmap src) {
        // create new bitmap with the same settings as source bitmap
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        // color info

        int height = src.getHeight();
        int width = src.getWidth();
        int l_arrRed[] = new int[256];
        int l_arrGreen[] = new int[256];
        int l_arrBlue[] = new int[256];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                l_arrRed[Color.red(src.getPixel(x, y))]++; // zlicza piksele w jednym kolorze
                l_arrGreen[Color.green(src.getPixel(x, y))]++;
                l_arrBlue[Color.blue(src.getPixel(x, y))]++;
            }
        }
        double w=width*height;

        double[] DRed= new double[256];
        for( int i =0; i < 256; i++)
        {
            int zmienna=l_arrRed[0];
            for( int k=0; k <i+1; k++)
            {
                zmienna+=l_arrRed[k];
            }
            System.out.println(zmienna);

            DRed[i] = zmienna/w;
        }

        double[] DGreen= new double[256];
        for( int i =0; i < 256; i++)
        {
            int zmienna=l_arrRed[0];
            for( int k=0; k <i+1; k++)
            {
                zmienna+=l_arrRed[k];
            }
            DGreen[i] = zmienna/w;
        }

        double[] DBlue= new double [256];
        for( int i =0; i < 256; i++)
        {
            int zmienna=l_arrRed[0];
            for( int k=0; k <i+1; k++)
            {
                zmienna+=l_arrRed[k];
            }
            DBlue[i] = zmienna/w;
        }

        int n = 0;
        while(DRed[n]<=0)
        {
            n=n+1;
        }
        double minDRed = DRed[n];

        int k = 0;
        while(DBlue[k]<=0)
        {
            k=k+1;
        }
        double minDBlue = DBlue[k];

        int j = 0;
        while(DGreen[j]<=0)
        {
            j=j+1;
        }
        double minDGreen = DGreen[j];


        int[] lutR = new int[256];
        int[] lutG = new int[256];
        int[] lutB = new int[256];
        for(int i = 0 ;i<256;++i){
            lutR[i]=(int) Math.floor(((DRed[i]-minDRed)/(1-minDRed))*255);
            lutG[i]=(int) Math.floor(((DGreen[i]-minDGreen)/(1-minDGreen))*255);
            lutB[i]=(int) Math.floor(((DBlue[i]-minDBlue)/(1-minDBlue))*255);

        }
        int r, g, b;
        for (int x = 0; x <width; x++) {
            for (int y = 0; y < height; y++) {
                r = lutR[Color.red(src.getPixel(x, y))];
                g = lutG[Color.green(src.getPixel(x, y))];
                b = lutB[Color.blue(src.getPixel(x, y))];
                bmOut.setPixel(x, y, Color.rgb(r, g, b));
            }
        }
        // return final bitmap
        return bmOut;
    }




    public Bitmap HistoGray(Bitmap src) {
        // create new bitmap with the same settings as source bitmap
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        // color info

        int height = src.getHeight();
        int width = src.getWidth();
        int l_arr[] = new int[256];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                l_arr[Color.red(src.getPixel(x, y))]++; // zlicza piksele w jednym kolorze
            }
        }
        double w=width*height;

        double[] D= new double[256];
        for( int i =0; i < 256; i++)
        {
            int zmienna=l_arr[0];
            for( int k=0; k <i+1; k++)
            {
                zmienna+=l_arr[k];
            }
            System.out.println(zmienna);

            D[i] = zmienna/w;
        }
        int n = 0;
        while(D[n]<=0)
        {
            n=n+1;
        }
        double minD = D[n];

        int[] lut = new int[256];
        for(int i = 0 ;i<256;++i){
            lut[i]=(int) Math.floor(((D[i]-minD)/(1-minD))*255);
        }
        int z;
        for (int x = 0; x <width; x++) {
            for (int y = 0; y < height; y++) {
                z = lut[Color.red(src.getPixel(x,y))];
                bmOut.setPixel(x, y, Color.rgb(z, z, z));
            }
        }
        // return final bitmap
        return bmOut;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        oryginal = ((BitmapDrawable) iv.getDrawable()).getBitmap();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        Bitmap sourceBitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
        switch(item.getItemId())
        {

            case R.id.negatyw:
                iv.setImageBitmap(Negatyw(sourceBitmap));
                break;
            case R.id.grey:
                iv.setImageBitmap(Gray(sourceBitmap));
                break;
            case R.id.sepia20:
                iv.setImageBitmap(Sepia(sourceBitmap,20));
                break;
            case R.id.sepia40:
                iv.setImageBitmap(Sepia(sourceBitmap,40));
                break;
            case R.id.roberts:
                iv.setImageBitmap(Roberts(sourceBitmap));
                break;
            case R.id.sobel:
                iv.setImageBitmap(Sobel(sourceBitmap));
                break;
            case R.id.histokol:
                iv.setImageBitmap(HistoKol(sourceBitmap));
                break;
            case R.id.histogrey:
                iv.setImageBitmap(HistoGray(sourceBitmap));
                break;
            case R.id.wykrywanie1:
                iv.setImageBitmap(WykrywanieSkory1(sourceBitmap));
                break;
            case R.id.wykrywanie2:
                iv.setImageBitmap(WykrywanieSkory2(sourceBitmap));
                break;
            case R.id.lenna1:
                ImageView iv4 = (ImageView) findViewById(R.id.lennaim);
                oryginal = ((BitmapDrawable) iv4.getDrawable()).getBitmap();
                Bitmap lenna = ((BitmapDrawable) iv4.getDrawable()).getBitmap();
                iv.setImageBitmap(lenna);
                break;
            case R.id.stwor:
                ImageView iv3 = (ImageView) findViewById(R.id.stworim);
                oryginal = ((BitmapDrawable) iv3.getDrawable()).getBitmap();
                Bitmap stwor = ((BitmapDrawable) iv3.getDrawable()).getBitmap();
                iv.setImageBitmap(stwor);
                break;
            case R.id.kot:
                ImageView iv2 = (ImageView) findViewById(R.id.kotim);
                oryginal = ((BitmapDrawable) iv2.getDrawable()).getBitmap();
                Bitmap kot = ((BitmapDrawable) iv2.getDrawable()).getBitmap();
                iv.setImageBitmap(kot);
                break;
            case R.id.tucan:
                ImageView iv5 = (ImageView) findViewById(R.id.tucanim);
                oryginal = ((BitmapDrawable) iv5.getDrawable()).getBitmap();
                Bitmap tucano = ((BitmapDrawable) iv5.getDrawable()).getBitmap();
                iv.setImageBitmap(tucano);
                break;

            case R.id.kobieta1:
                ImageView iv6 = (ImageView) findViewById(R.id.kobietaim);
                oryginal = ((BitmapDrawable) iv6.getDrawable()).getBitmap();
                Bitmap kobieta = ((BitmapDrawable) iv6.getDrawable()).getBitmap();
                iv.setImageBitmap(kobieta);
                break;
        }


        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
