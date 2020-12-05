package tpsql.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;

public class ByteUtil {
	
	public static byte[] longToByte(long number) {
		long temp = number;
		byte[] b = new byte[8];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Long(temp & 0xff).byteValue();// 将最低位保存在最低位
			temp = temp >> 8; // 向右移8位        
		}
		return b;
	}         
	
	//byte数组转成long     
	public static long byteToLong(byte[] b) {
		return byteToLong(b,0);
	}

	//byte数组转成long
	public static long byteToLong(byte[] b,int pos) {
		long s = 0;
		long s0 = b[pos] & 0xff;// 最低位
		long s1 = b[pos+1] & 0xff;
		long s2 = b[pos+2] & 0xff;
		long s3 = b[pos+3] & 0xff;
		long s4 = b[pos+4] & 0xff;// 最低位
		long s5 = b[pos+5] & 0xff;
		long s6 = b[pos+6] & 0xff;
		long s7 = b[pos+7] & 0xff;          // s0不变
		s1 <<= 8;
		s2 <<= 16;
		s3 <<= 24;
		s4 <<= 8 * 4;
		s5 <<= 8 * 5;
		s6 <<= 8 * 6;
		s7 <<= 8 * 7;
		s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
		return s;
	}
	
	public static byte[] intToByte(int number) {
		int temp = number;
		byte[] b = new byte[4];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Integer(temp & 0xff).byteValue();// 将最低位保存在最低位
			temp = temp >> 8; // 向右移8位 
		}
		return b;
	}
	
	public static int byteToInt(byte[] b) {
		return byteToInt(b,0);
	}

	public static int byteToInt(byte[] b,int pos) {
		int s = 0;
		int s0 = b[pos] & 0xff;// 最低位
		int s1 = b[pos+1] & 0xff;
		int s2 = b[pos+2] & 0xff;
		int s3 = b[pos+3] & 0xff;
		s3 <<= 24;
		s2 <<= 16;
		s1 <<= 8;
		s = s0 | s1 | s2 | s3;
		return s;
	}
	
	public static byte[] shortToByte(short number) {
		int temp = number;
		byte[] b = new byte[2];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Integer(temp & 0xff).byteValue();// 将最低位保存在最低位
			temp = temp >> 8; // 向右移8位  
		}         
		return b;
	}
	
	public static short byteToShort(byte[] b) {
		return byteToShort(b,0);
	}

	public static short byteToShort(byte[] b,int pos) {
		short s = 0;
		short s0 = (short) (b[pos] & 0xff);// 最低位
		short s1 = (short) (b[pos+1] & 0xff);
		s1 <<= 8;
		s = (short) (s0 | s1);
		return s;
	}

	public static byte[] doubleToByte(double number){
		long value = Double.doubleToRawLongBits(number);
		return longToByte(value);
	}

	public static double byteToDouble(byte[] b){
		long value = byteToLong(b);
		return Double.longBitsToDouble(value);
	}

	public static double byteToDouble(byte[] b,int pos){
		long value = byteToLong(b,pos);
		return Double.longBitsToDouble(value);
	}

	public static byte[] decimalToByte(BigDecimal number){
		int scale = number.scale();
		byte[] b = new byte[9];
		byte[] decimalBytes = longToByte(number.unscaledValue().longValue());
		for(int i=0;i<8;i++)
			b[i] = decimalBytes[i];
		b[8] = (byte)scale;
		return b;
	}

	public static BigDecimal byteToDecimal(byte[] b){
		int scale = b[8];
		Long value = byteToLong(b);
		return new BigDecimal(BigInteger.valueOf(value),scale);
	}

	public static BigDecimal byteToDecimal(byte[] b,int pos){
		int scale = b[8];
		Long value = byteToLong(b,pos);
		return new BigDecimal(BigInteger.valueOf(value),scale);
	}

	public static byte[] dateToByte(Date date){
		return longToByte(date.getTime());
	}

	public static Date byteToDate(byte[] b){
		long value = byteToLong(b);
		return new Date(value);
	}

	public static Date byteToDate(byte[] b,int pos){
		long value = byteToLong(b,pos);
		return new Date(value);
	}

	public static byte[] floatToByte(float number){
		int value = Float.floatToIntBits(number);
		return intToByte(value);
	}

	public static float byteToFloat(byte[] b){
		int value = byteToInt(b);
		return Float.intBitsToFloat(value);
	}

	public static float byteToFloat(byte[] b,int pos){
		int value = byteToInt(b,pos);
		return Float.intBitsToFloat(value);
	}

	public static byte[] boolToByte(boolean b){
		return b?new byte[]{1}:new byte[]{0};
	}

	public static boolean byteToBool(byte[] b){
		return b[0]==(byte)1;
	}

	public static boolean byteToBool(byte[] b,int pos){
		return b[pos]==(byte)1;
	}
	
	public static byte[] removeZeroBytes(byte[] data){
		int i=0;
		byte[] bytes = new byte[data.length];
		for(byte b : data){
			if(b!=0){
				bytes[i]=b;
				i++;
			}
		}
		return Arrays.copyOf(bytes,i);
	}
}
