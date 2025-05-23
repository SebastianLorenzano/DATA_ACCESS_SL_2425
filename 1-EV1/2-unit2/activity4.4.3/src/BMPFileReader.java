import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;

public class BMPFileReader
{
    final static int SIZEBYTES_OFFSET = 2;
    final static int SIZEBYTES_SIZE = 4;
    final static int WIDTHBYTES_OFFSET = 18;
    final static int WIDTHBYTES_SIZE = 4;
    final static int HEIGHTBYTES_OFFSET = 22;
    final static int HEIGHTBYTES_SIZE = 4;
    final static int BITSNUMBYTES_OFFSET = 28;
    final static int BITSNUMBYTES_SIZE = 2;
    final static int HEADERBYTES_SIZE = 55;

    public record Result(long size, long width, long height, long bitsNum)
    {
        public void printInfo()
        {
            System.out.println("Size: " + size);
            System.out.println("Width: " + width);
            System.out.println("Height: " + height);
            System.out.println("BitsNum: " + bitsNum);
        }
    }

    public static void main(String[] args)
    {
        run("2.bmp").printInfo();
    }

    public static Result run(String filePath)
    {
        FileInputStream fIn = null;
        if (ImageTypeReader.run(filePath) != Utils.FileType.BMP)
        {
            System.err.println(Utils.FORMAT_NOT_SUPPORTED);
            return null;
        }

        try
        {
            fIn = new FileInputStream(filePath);
            byte[] header = readBytesFromFile(0, HEADERBYTES_SIZE, fIn);
            long size = readLittleEndian(header, SIZEBYTES_OFFSET, SIZEBYTES_SIZE);
            long width = readLittleEndian(header, WIDTHBYTES_OFFSET, WIDTHBYTES_SIZE);
            long height = readLittleEndian(header, HEIGHTBYTES_OFFSET, HEIGHTBYTES_SIZE);
            long bitsNum = readLittleEndian(header, BITSNUMBYTES_OFFSET, BITSNUMBYTES_SIZE);
            return new Result(size, width, height, bitsNum);
        }
        catch (Exception e)
        {
            System.err.println(e.toString());
        }
        finally
        {
            closeStream(fIn);
        }
        return null;
    }

    private static byte[] readBytesFromFile(int offset, int size, FileInputStream file) throws IOException
    {
        file.skip(offset);
        byte[] result = new byte[size];
        file.read(result, 0, size);
        return result;
    }

    private static long readLittleEndian(byte[] b, int offset, int size)
    {
        if (b == null)
            return -1;
        if (size == 4)
        {
            return (Byte.toUnsignedInt(b[offset]) +
                    256L * (Byte.toUnsignedInt(b[offset + 1]) +
                            256L * (Byte.toUnsignedInt(b[offset + 2]) +
                                    256L * Byte.toUnsignedInt(b[offset + 3]))));

        }
        else
            return (Byte.toUnsignedInt(b[offset]) + 256 * (Byte.toUnsignedInt(b[offset + 1])));
    }

    public static void closeStream(Closeable file){
        try
        {
            if(file!=null)
                file.close();
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
        }
    }

}