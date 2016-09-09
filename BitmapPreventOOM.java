/*
* part1 通过inSampleSize缩放Bitmap
* @params options BitmapFactory处理Bitmap格式
* @params reqWidth, reqHeight 请求宽高
* @return inSampleSize BitmapFactory.Options.inSampleSize值
* 通过BitmapFactory.Options的inSampleSize压缩Bitmap，如果inSampleSize = 2，长宽压缩到1/2
* 通过实际的容器大小确定压缩大小
*/
public static int caculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
        int width=options.outWidth;
        int height=options.outHeight;
        int inSampleSize=1;
        if(width>reqWidth||height>reqHeight){
        int widthRadio=Math.round(width*1.0f/reqWidth);
        int heightRadio=Math.round(height*1.0f/reqHeight);
        inSampleSize=Math.max(widthRadio,heightRadio);
        }
        return inSampleSize;
        }


        /*
        * part2 通过inJustDecodeBounds + inSampleSize处理Bitmap缩放
        *
        * */
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true; // 设置了此属性一定要记得将值设置为false
        Bitmap bitmap=null;
        bitmap=BitmapFactory.decodeFile(url,options);
        options.inSampleSize=computeSampleSize(options,128,128);
        //inPreferredConfig设置Bitmap内存占内存大小，每一个数字对应ARPG一位占几位
        options.inPreferredConfig=Bitmap.Config.ARGB_4444;
        /* 下面两个字段需要组合使用 */
        //inPurgeable，inInputShareable已经deprecated
        options.inPurgeable=true;
        options.inInputShareable=true;
        options.inJustDecodeBounds=false;
        try{
        bitmap=BitmapFactory.decodeFile(url,options);
        }catch(OutOfMemoryError e){
        Log.e(TAG,"OutOfMemoryError");
        }