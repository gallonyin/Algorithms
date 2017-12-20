package util;

/**
 * Created by gallon on 2017/11/30.
 */

//zxing ��ά��
//compile files('libs/core-3.3.1-20160916.143830-1.jar')

/**
//��ά�� - ΢��ҳ����
                ImageView v_top1 = (ImageView) window.findViewById(R.id.v_top1);
                try {
                    InputStream is = mContext.getResources().openRawResource(R.raw.xiaofa);
                    Bitmap mBitmap = BitmapFactory.decodeStream(is);

                    String content = "%e4%bd%a0%e5%a5%bd"; //���
                    try {
                        content = URLEncoder.encode(VoiceApplication.S2S, "utf-8");
                        if (TextUtils.isEmpty(content)) content = "%e4%bd%a0%e5%a5%bd"; //���
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    String url = "https://h5.law.push.aegis-info.com/html/qavoice.html?id="
//                            + SharePreHelper.getEventId()
                            + "5822"
                            + "&lawyerid="
                            + bean.lawyerId + "&robotcontent=" + content;
                    GLog.e(TAG, "url:" + url);

                    Bitmap code = CodeUtils.createCode(mContext, url, mBitmap);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    code.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                    byte[] bytes = baos.toByteArray();

                    Glide.with(mContext)
                            .load(bytes)
                            .crossFade()
                            .into(v_top1);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

/**
 * ��ά���������
 * Created by gallon on 2017/11/30.
 */
public class CodeUtils {

    // ���ֵ��Ӱ���м�ͼƬ��С
    private static final int IMAGE_HALFWIDTH = 40;

    /**
     * �����м��ͼƬ�Ķ�ά��
     *
     * @param context ������
     * @param content ��ά������
     * @param logo    ��ά���м��ͼƬ
     * @return ���ɵĶ�ά��ͼƬ
     * @throws WriterException ���ɶ�ά���쳣
     */
    public static Bitmap createCode(Context context, String content, Bitmap logo) throws WriterException {
        Matrix m = new Matrix();
        float sx = (float) 2 * IMAGE_HALFWIDTH / logo.getWidth();
        float sy = (float) 2 * IMAGE_HALFWIDTH / logo.getHeight();
        // ����������Ϣ
        m.setScale(sx, sy);
        // ��logoͼƬ��martix���õ���Ϣ����
        logo = Bitmap.createBitmap(logo, 0, 0, logo.getWidth(),
                logo.getHeight(), m, false);
        MultiFormatWriter writer = new MultiFormatWriter();
        Hashtable<EncodeHintType, Object> hst = new Hashtable();
        // �����ַ�����
        hst.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // ���ö�ά���ݴ���
        hst.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // ���ɶ�ά�������Ϣ
        BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE,
                SizeUtils.dp2px(300),
                SizeUtils.dp2px(300), hst);
        // ����߶�
        int width = matrix.getWidth();
        // ������
        int height = matrix.getHeight();
        int halfW = width / 2;
        int halfH = height / 2;
        // �������鳤��Ϊ����߶�*�����ȣ����ڼ�¼������������Ϣ
        int[] pixels = new int[width * height];
        // ���п�ʼ��������
        for (int y = 0; y < height; y++) {
            // ������
            for (int x = 0; x < width; x++) {
                // ��λ�����ڴ��ͼƬ��Ϣ
                if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
                        && y > halfH - IMAGE_HALFWIDTH
                        && y < halfH + IMAGE_HALFWIDTH) {
                    // ��¼ͼƬÿ��������Ϣ
                    pixels[y * width + x] = logo.getPixel(x - halfW
                            + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
                } else {
                    // ����кڿ�㣬��¼��Ϣ
                    if (matrix.get(x, y)) {
                        // ��¼�ڿ���Ϣ
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = Color.WHITE;
                    }
                }

            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // ͨ��������������bitmap
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * �����û��Ķ�ά��
     *
     * @param context ������
     * @param content ��ά������
     * @return ���ɵĶ�ά��ͼƬ
     * @throws WriterException ���ɶ�ά���쳣
     */
    public static Bitmap createCode(Context context, String content) throws WriterException {
        //���ɶ�ά����,����ʱָ����С,��Ҫ������ͼƬ�Ժ��ٽ�������,������ģ������ʶ��ʧ��
        BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE,
                SizeUtils.dp2px(250),
                SizeUtils.dp2px(250));
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        //��ά����תΪһά��������,Ҳ����һֱ��������
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                } else {
                    pixels[y * width + x] = Color.WHITE;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //ͨ��������������bitmap
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}