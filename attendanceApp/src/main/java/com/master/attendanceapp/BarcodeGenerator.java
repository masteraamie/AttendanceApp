package com.master.attendanceapp;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.View;

import com.onbarcode.barcode.android.AndroidColor;
import com.onbarcode.barcode.android.IBarcode;
import com.onbarcode.barcode.android.QRCode;

public class BarcodeGenerator extends View
{

	static long id ;
	static String name;
	static String parent; 
	static String reg_no; 
	static String mob_no;
	static DisplayMetrics met;
	public BarcodeGenerator(Context context , long i , String n , String p, String r , String mob , DisplayMetrics dm) {
	    super(context);	    	   
	    
	    id = i;
	    name = n;
	    parent = p;
	    reg_no = r;
	    mob_no = mob;
	    met = dm;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		try {	
	        testQRCode(canvas);
	        
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


    private static void testQRCode(Canvas canvas) throws Exception
    {
        QRCode barcode = new QRCode();

        /*
           QRCode Valid data char set:
                numeric data (digits 0 - 9);
                alphanumeric data (digits 0 - 9; upper case letters A -Z; nine other characters: space, $ % * + - . / : );
                byte data (default: ISO/IEC 8859-1);
                Kanji characters
        */
                       //BIZCARD:N:Kelly;X:Goto;T:Design Ethnographer;C:gotomedia LLC;A:2169 Folsom Street M302;B:4158647007;F:4158647004;M:4159907005;E:kelly@gotomedia.com;;
        
        String data = id + "\n" + 
        				"Name : " + name + "\n" + 
        				"Parentage : " + parent + "\n" + 
        				"Registration Number : " + reg_no + "\n" + 
        				"Mobile Number : " + mob_no;
        
        barcode.setData(data);
        barcode.setDataMode(QRCode.M_AUTO);
        barcode.setVersion(1);
        barcode.setEcl(QRCode.ECL_L);
        
        //  if you want to encode GS1 compatible QR Code, you need set FNC1 mode to IBarcode.FNC1_ENABLE
        barcode.setFnc1Mode(IBarcode.FNC1_NONE);

        //  Set the processTilde property to true, if you want use the tilde character "~" to specify special characters in the input data. Default is false.
        //  1-byte character: ~ddd (character value from 0 ~ 255)
        //  ASCII (with EXT): from ~000 to ~255
        //  2-byte character: ~6ddddd (character value from 0 ~ 65535)
        //  Unicode: from ~600000 to ~665535
        //  ECI: from ~7000000 to ~7999999
        //  SJIS: from ~9ddddd (Shift JIS 0x8140 ~ 0x9FFC and 0xE040 ~ 0xEBBF)
        barcode.setProcessTilde(false);

        // unit of measure for X, Y, LeftMargin, RightMargin, TopMargin, BottomMargin
        barcode.setUom(IBarcode.UOM_PIXEL);
        // barcode module width in pixel
               
        float width = ( met.widthPixels / 50 );
        barcode.setX(width);
       
        /*barcode.setLeftMargin(50f);
        barcode.setRightMargin(50f);
        barcode.setTopMargin(50f);
        barcode.setBottomMargin(50f);*/
        // barcode image resolution in dpi
        barcode.setResolution(100);
        
      
        
        // barcode bar color and background color in Android device
        barcode.setForeColor(AndroidColor.black);
        barcode.setBackColor(AndroidColor.white);

        /*
        specify your barcode drawing area
	    */
	    RectF bounds = new RectF(100, 100, 0, 0);
        barcode.drawBarcode(canvas, bounds);
    }

}
